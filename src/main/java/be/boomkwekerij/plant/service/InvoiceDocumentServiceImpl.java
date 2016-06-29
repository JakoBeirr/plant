package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.mapper.CompanyMapper;
import be.boomkwekerij.plant.mapper.CustomerMapper;
import be.boomkwekerij.plant.mapper.InvoiceMapper;
import be.boomkwekerij.plant.model.dto.CompanyDTO;
import be.boomkwekerij.plant.model.dto.InvoiceDTO;
import be.boomkwekerij.plant.model.report.CompanyReportObject;
import be.boomkwekerij.plant.model.report.CustomerReportObject;
import be.boomkwekerij.plant.model.report.InvoiceReportObject;
import be.boomkwekerij.plant.util.Initializer;
import be.boomkwekerij.plant.util.SearchResult;
import net.sf.jasperreports.engine.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class InvoiceDocumentServiceImpl implements InvoiceDocumentService {

    private InvoiceService invoiceService = new InvoiceServiceImpl();
    private CompanyService companyService = new CompanyServiceImpl();

    private CompanyMapper companyMapper = new CompanyMapper();
    private CustomerMapper customerMapper = new CustomerMapper();
    private InvoiceMapper invoiceMapper = new InvoiceMapper();

    public byte[] createInvoiceDocument(String id) {
        CompanyReportObject company = getCompany();
        InvoiceDTO invoiceDTO = getInvoice(id);
        CustomerReportObject customer = customerMapper.mapDTOToReportObject(invoiceDTO.getCustomer());
        InvoiceReportObject invoice = invoiceMapper.mapDTOToReportObject(invoiceDTO);

        try {
            return createDocument(company, customer, invoice);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private CompanyReportObject getCompany() {
        SearchResult<CompanyDTO> companySearchResult = companyService.getCompany();

        if (companySearchResult.isError()) {
            throw new IllegalArgumentException("Company not found!");
        }

        CompanyDTO companyDTO = companySearchResult.getFirst();
        return companyMapper.mapDTOToReportObject(companyDTO);
    }

    private InvoiceDTO getInvoice(String id) {
        SearchResult<InvoiceDTO> invoiceSearchResult = invoiceService.getInvoice(id);

        if (invoiceSearchResult.isError()) {
            throw new IllegalArgumentException("Invoice not found!");
        }

        return invoiceSearchResult.getFirst();
    }

    private byte[] createDocument(CompanyReportObject company, CustomerReportObject customer, InvoiceReportObject invoice) throws IOException {
        InputStream invoiceTemplateStream = ClassLoader.getSystemResourceAsStream("invoiceDocument/invoice.jrxml");
        JasperReport invoiceTemplate = compileStreamToReport(invoiceTemplateStream);

        JRDataSource dataSource = new JREmptyDataSource();

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("company", company);
        parameters.put("customer", customer);
        parameters.put("invoice", invoice);
        parameters.put("logo", ClassLoader.getSystemResourceAsStream("invoiceDocument/logo.jpg"));

        byte[] pdfReport = createPdfReport(invoiceTemplate, dataSource, parameters);
        FileUtils.writeByteArrayToFile(new File(Initializer.getDataUri() + "/files/" + invoice.getInvoiceNumber() + ".pdf"), pdfReport);
        return pdfReport;
    }

    private JasperReport compileStreamToReport(InputStream inputStream) {
        JasperReport jasperReport = null;
        try {
            jasperReport = JasperCompileManager.compileReport(inputStream);
        } catch (JRException e) {
            e.printStackTrace();
        }
        return jasperReport;
    }

    private byte[] createPdfReport(JasperReport template, JRDataSource dataSource, Map<String, Object> parameters) {
        try {
            JasperPrint printFile = JasperFillManager.fillReport(template, parameters, dataSource);
            if (printFile != null) {
                return JasperExportManager.exportReportToPdf(printFile);
            }
            return null;
        } catch (JRException e) {
            return null;
        }
    }
}
