package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.mapper.CompanyMapper;
import be.boomkwekerij.plant.mapper.CustomerMapper;
import be.boomkwekerij.plant.mapper.InvoiceMapper;
import be.boomkwekerij.plant.model.dto.CompanyDTO;
import be.boomkwekerij.plant.model.dto.InvoiceDTO;
import be.boomkwekerij.plant.model.report.CompanyReportObject;
import be.boomkwekerij.plant.model.report.CustomerReportObject;
import be.boomkwekerij.plant.model.report.InvoiceReportObject;
import be.boomkwekerij.plant.util.InvoicePDFCreator;
import be.boomkwekerij.plant.util.SearchResult;

import java.io.IOException;

public class InvoiceDocumentServiceImpl implements InvoiceDocumentService {

    private InvoiceService invoiceService = new InvoiceServiceImpl();
    private CompanyService companyService = new CompanyServiceImpl();

    private CompanyMapper companyMapper = new CompanyMapper();
    private CustomerMapper customerMapper = new CustomerMapper();
    private InvoiceMapper invoiceMapper = new InvoiceMapper();

    private InvoicePDFCreator invoicePDFCreator = new InvoicePDFCreator();

    public byte[] createInvoiceDocument(String id) {
        CompanyReportObject company = getCompany();
        InvoiceDTO invoiceDTO = getInvoice(id);
        CustomerReportObject customer = customerMapper.mapDTOToReportObject(invoiceDTO.getCustomer());
        InvoiceReportObject invoice = invoiceMapper.mapDTOToReportObject(invoiceDTO);

        try {
            return invoicePDFCreator.createInvoicePdf(company, customer, invoice);
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
}
