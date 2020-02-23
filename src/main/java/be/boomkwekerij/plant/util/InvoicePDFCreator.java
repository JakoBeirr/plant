package be.boomkwekerij.plant.util;

import be.boomkwekerij.plant.exception.ReportException;
import be.boomkwekerij.plant.model.dto.BestandDTO;
import be.boomkwekerij.plant.model.report.*;
import net.sf.jasperreports.engine.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class InvoicePDFCreator {

    private PDFHelper pdfHelper = new PDFHelper();

    public BestandDTO createOnePagedInvoiceDocument(CompanyReportObject company, CustomerReportObject customer, OnePagedInvoiceReportObject invoice) throws ReportException {
        try {
            JasperReport invoiceTemplate = getOnePagedTemplate();
            Map<String, Object> parameters = getOnePagedInvoiceParameters(company, customer, invoice);
            JRDataSource dataSource = getDataSource();
            JasperPrint page = pdfHelper.fillPDF(invoiceTemplate, parameters, dataSource);

            return createPDF(invoice.getInvoiceNumber(), Collections.singletonList(page));
        } catch (Exception e) {
            throw new ReportException(e.getMessage());
        }
    }

    public BestandDTO createMultiplePagedInvoiceDocument(CompanyReportObject company, CustomerReportObject customer, List<MultiplePagedInvoiceReportObject> invoiceParts) throws ReportException {
        try {
            List<JasperPrint> pages = new ArrayList<>();
            for (int i = 1; i <= invoiceParts.size(); i++) {
                MultiplePagedInvoiceReportObject invoice = invoiceParts.get(i-1);

                JasperReport invoiceTemplate = getTemplate(invoiceParts, i);
                Map<String, Object> parameters = getMultiplePagedInvoiceParameters(company, customer, invoice);
                JRDataSource dataSource = getDataSource();
                JasperPrint page = pdfHelper.fillPDF(invoiceTemplate, parameters, dataSource);
                pages.add(page);
            }

            return createPDF(invoiceParts.get(0).getInvoiceNumber(), pages);
        } catch (Exception e) {
            throw new ReportException(e.getMessage());
        }
    }

    private JasperReport getOnePagedTemplate() throws JRException {
        InputStream invoiceTemplateStream = ClassLoader.getSystemResourceAsStream("invoiceDocument/one_paged_invoice.jrxml");
        return pdfHelper.compileStreamToReport(invoiceTemplateStream);
    }

    private JasperReport getTemplate(List<MultiplePagedInvoiceReportObject> invoiceParts, int counter) throws JRException {
        JasperReport invoiceTemplate = null;
        if (counter == 1) {
            invoiceTemplate = getFirstMultiplePagedTemplate();
        } else if (counter == invoiceParts.size()) {
            invoiceTemplate = getLastMultiplePagedTemplate();
        } else {
            invoiceTemplate = getMiddleMultiplePagedTemplate();
        }
        return invoiceTemplate;
    }

    private JasperReport getFirstMultiplePagedTemplate() throws JRException {
        InputStream invoiceTemplateStream = ClassLoader.getSystemResourceAsStream("invoiceDocument/first_page_multiple_paged_invoice.jrxml");
        return pdfHelper.compileStreamToReport(invoiceTemplateStream);
    }

    private JasperReport getMiddleMultiplePagedTemplate() throws JRException {
        InputStream invoiceTemplateStream = ClassLoader.getSystemResourceAsStream("invoiceDocument/middle_page_multiple_paged_invoice.jrxml");
        return pdfHelper.compileStreamToReport(invoiceTemplateStream);
    }

    private JasperReport getLastMultiplePagedTemplate() throws JRException {
        InputStream invoiceTemplateStream = ClassLoader.getSystemResourceAsStream("invoiceDocument/last_page_multiple_paged_invoice.jrxml");
        return pdfHelper.compileStreamToReport(invoiceTemplateStream);
    }

    private JRDataSource getDataSource() {
        return new JREmptyDataSource();
    }

    private Map<String, Object> getOnePagedInvoiceParameters(CompanyReportObject company, CustomerReportObject customer, OnePagedInvoiceReportObject invoice) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("company", company);
        parameters.put("customer", customer);
        parameters.put("invoice", invoice);
        parameters.put("logo", ClassLoader.getSystemResourceAsStream("invoiceDocument/logo.png"));
        return parameters;
    }

    private Map<String, Object> getMultiplePagedInvoiceParameters(CompanyReportObject company, CustomerReportObject customer, MultiplePagedInvoiceReportObject invoice) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("company", company);
        parameters.put("customer", customer);
        parameters.put("invoice", invoice);
        parameters.put("logo", ClassLoader.getSystemResourceAsStream("invoiceDocument/logo.png"));
        return parameters;
    }

    private BestandDTO createPDF(String invoiceNumber, List<JasperPrint> pages) throws IOException, JRException {
        byte[] pdfReport = pdfHelper.createPDF(pages);

        BestandDTO bestandDTO = new BestandDTO();
        bestandDTO.setName(invoiceNumber + ".pdf");
        bestandDTO.setFile(pdfReport);

        return bestandDTO;
    }
}
