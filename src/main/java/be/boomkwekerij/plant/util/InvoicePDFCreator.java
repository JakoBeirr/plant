package be.boomkwekerij.plant.util;

import be.boomkwekerij.plant.exception.ReportException;
import be.boomkwekerij.plant.model.report.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class InvoicePDFCreator {

    public byte[] createOnePagedInvoiceDocument(CompanyReportObject company, CustomerReportObject customer, OnePagedInvoiceReportObject invoice) throws ReportException {
        try {
            JasperReport invoiceTemplate = getOnePagedTemplate();
            Map<String, Object> parameters = getOnePagedInvoiceParameters(company, customer, invoice);
            JRDataSource dataSource = getDataSource();
            JasperPrint page = JasperFillManager.fillReport(invoiceTemplate, parameters, dataSource);

            return createPDF(invoice.getInvoiceNumber(), Arrays.asList(page));
        } catch (JRException | IOException e) {
            throw new ReportException(e.getMessage());
        }
    }

    public byte[] createMultiplePagedInvoiceDocument(CompanyReportObject company, CustomerReportObject customer, List<MultiplePagedInvoiceReportObject> invoiceParts) throws ReportException {
        try {
            List<JasperPrint> pages = new ArrayList<>();
            for (int i = 1; i <= invoiceParts.size(); i++) {
                MultiplePagedInvoiceReportObject invoice = invoiceParts.get(i-1);

                JasperReport invoiceTemplate = getTemplate(invoiceParts, i);
                Map<String, Object> parameters = getMultiplePagedInvoiceParameters(company, customer, invoice);
                JRDataSource dataSource = getDataSource();
                JasperPrint page = JasperFillManager.fillReport(invoiceTemplate, parameters, dataSource);
                pages.add(page);
            }

            return createPDF(invoiceParts.get(0).getInvoiceNumber(), pages);
        } catch (Exception e) {
            throw new ReportException(e.getMessage());
        }
    }

    private JasperReport getOnePagedTemplate() throws JRException {
        InputStream invoiceTemplateStream = ClassLoader.getSystemResourceAsStream("invoiceDocument/one_paged_invoice.jrxml");
        return compileStreamToReport(invoiceTemplateStream);
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
        return compileStreamToReport(invoiceTemplateStream);
    }

    private JasperReport getMiddleMultiplePagedTemplate() throws JRException {
        InputStream invoiceTemplateStream = ClassLoader.getSystemResourceAsStream("invoiceDocument/middle_page_multiple_paged_invoice.jrxml");
        return compileStreamToReport(invoiceTemplateStream);
    }

    private JasperReport getLastMultiplePagedTemplate() throws JRException {
        InputStream invoiceTemplateStream = ClassLoader.getSystemResourceAsStream("invoiceDocument/last_page_multiple_paged_invoice.jrxml");
        return compileStreamToReport(invoiceTemplateStream);
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

    private JasperReport compileStreamToReport(InputStream inputStream) throws JRException {
        JasperReport jasperReport = null;
        jasperReport = JasperCompileManager.compileReport(inputStream);
        return jasperReport;
    }

    private byte[] createPDF(String invoiceNumber, List<JasperPrint> pages) throws IOException, JRException {
        byte[] pdfReport = createPdfReport(pages);
        writeFileToFileSystem(invoiceNumber, pdfReport);
        return pdfReport;
    }

    private byte[] createPdfReport(List<JasperPrint> pages) throws JRException {
        JRPdfExporter pdfExporter = new JRPdfExporter();
        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        List<ExporterInputItem> exporterInputItems = new ArrayList<>(pages.size());
        for (JasperPrint page : pages) {
            ExporterInputItem exporterInputItem = new SimpleExporterInputItem(page);
            exporterInputItems.add(exporterInputItem);
        }
        pdfExporter.setExporterInput(new SimpleExporterInput(exporterInputItems));
        pdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfOutputStream));
        pdfExporter.exportReport();
        return pdfOutputStream.toByteArray();
    }

    private void writeFileToFileSystem(String invoiceNumber, byte[] pdfReport) throws IOException {
        File invoiceFile = new File(Initializer.getDataUri() + "/files/" + invoiceNumber + ".pdf");
        if (invoiceFile.exists()) {
            invoiceFile.delete();
        }
        FileUtils.writeByteArrayToFile(invoiceFile, pdfReport);
    }
}
