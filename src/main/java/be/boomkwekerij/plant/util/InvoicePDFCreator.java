package be.boomkwekerij.plant.util;

import be.boomkwekerij.plant.model.report.CompanyReportObject;
import be.boomkwekerij.plant.model.report.CustomerReportObject;
import be.boomkwekerij.plant.model.report.InvoiceReportObject;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class InvoicePDFCreator {

    public byte[] createInvoicePdf(CompanyReportObject company, CustomerReportObject customer, InvoiceReportObject invoice) throws IOException {
        JasperReport invoiceTemplate = getTemplate();
        JRDataSource dataSource = getDataSource();
        Map<String, Object> parameters = getParameters(company, customer, invoice);

        return createPDF(invoice, invoiceTemplate, dataSource, parameters);
    }

    private JasperReport getTemplate() {
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

    private JasperReport compileStreamToReport(InputStream inputStream) {
        JasperReport jasperReport = null;
        try {
            jasperReport = JasperCompileManager.compileReport(inputStream);
        } catch (JRException e) {
            e.printStackTrace();
        }
        return jasperReport;
    }

    private byte[] createPDF(InvoiceReportObject invoice, JasperReport invoiceTemplate, JRDataSource dataSource, Map<String, Object> parameters) throws IOException {
        byte[] pdfReport = createPdfReport(invoiceTemplate, dataSource, parameters);
        FileUtils.writeByteArrayToFile(new File(Initializer.getDataUri() + "/files/" + invoice.getInvoiceNumber() + ".pdf"), pdfReport);
        return pdfReport;
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
