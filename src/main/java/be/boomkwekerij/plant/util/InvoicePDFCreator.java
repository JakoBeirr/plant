package be.boomkwekerij.plant.util;

import be.boomkwekerij.plant.exception.ReportException;
import be.boomkwekerij.plant.model.report.CompanyReportObject;
import be.boomkwekerij.plant.model.report.CustomerReportObject;
import be.boomkwekerij.plant.model.report.InvoiceReportObject;
import net.sf.jasperreports.engine.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class InvoicePDFCreator {

    public byte[] createInvoicePdf(CompanyReportObject company, CustomerReportObject customer, InvoiceReportObject invoice) throws ReportException {
        try {
            JasperReport invoiceTemplate = getTemplate();
            JRDataSource dataSource = getDataSource();
            Map<String, Object> parameters = getParameters(company, customer, invoice);

            return createPDF(invoice, invoiceTemplate, dataSource, parameters);
        } catch (JRException | IOException e) {
            throw new ReportException(e.getMessage());
        }
    }

    private JasperReport getTemplate() throws JRException {
        InputStream invoiceTemplateStream = ClassLoader.getSystemResourceAsStream("invoiceDocument/invoice.jrxml");
        return compileStreamToReport(invoiceTemplateStream);
    }

    private JRDataSource getDataSource() {
        return new JREmptyDataSource();
    }

    private Map<String, Object> getParameters(CompanyReportObject company, CustomerReportObject customer, InvoiceReportObject invoice) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("company", company);
        parameters.put("customer", customer);
        parameters.put("invoice", invoice);
        parameters.put("logo", ClassLoader.getSystemResourceAsStream("invoiceDocument/logo.jpg"));
        return parameters;
    }

    private JasperReport compileStreamToReport(InputStream inputStream) throws JRException {
        JasperReport jasperReport = null;
        jasperReport = JasperCompileManager.compileReport(inputStream);
        return jasperReport;
    }

    private byte[] createPDF(InvoiceReportObject invoice, JasperReport invoiceTemplate, JRDataSource dataSource, Map<String, Object> parameters) throws IOException, JRException {
        byte[] pdfReport = createPdfReport(invoiceTemplate, dataSource, parameters);
        File invoiceFile = new File(Initializer.getDataUri() + "/files/" + invoice.getInvoiceNumber() + ".pdf");
        if (invoiceFile.exists()) {
            invoiceFile.delete();
        }
        FileUtils.writeByteArrayToFile(invoiceFile, pdfReport);
        return pdfReport;
    }

    private byte[] createPdfReport(JasperReport template, JRDataSource dataSource, Map<String, Object> parameters) throws JRException {
        JasperPrint printFile = JasperFillManager.fillReport(template, parameters, dataSource);
        if (printFile != null) {
            return JasperExportManager.exportReportToPdf(printFile);
        }
        throw new IllegalArgumentException("Kon factuur niet aanmaken");
    }
}
