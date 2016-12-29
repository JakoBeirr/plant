package be.boomkwekerij.plant.util;

import be.boomkwekerij.plant.exception.ReportException;
import be.boomkwekerij.plant.model.dto.BestandDTO;
import be.boomkwekerij.plant.model.report.CustomerFileReportObject;
import be.boomkwekerij.plant.model.report.InvoicesReportObject;
import net.sf.jasperreports.engine.*;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportPDFCreator {

    private PDFHelper pdfHelper = new PDFHelper();

    public BestandDTO createCustomerFileReport(CustomerFileReportObject customerFileReportObject) throws ReportException {
        try {
            JasperReport template = getCustomerFileTemplate();
            Map<String, Object> parameters = getCustomerFileParameters(customerFileReportObject);
            JRDataSource dataSource = getDataSource();
            JasperPrint page = pdfHelper.fillPDF(template, parameters, dataSource);

            return createPDF("klantenbestand_" + DateUtils.formatDate(new DateTime(), DateFormatPattern.DATE_FORMAT), Arrays.asList(page));
        } catch (JRException | IOException e) {
            throw new ReportException(e.getMessage());
        }
    }

    public BestandDTO createInvoiceReport(InvoicesReportObject invoicesReportObject) throws ReportException {
        try {
            JasperReport template = getInvoiceReportTemplate();
            Map<String, Object> parameters = getInvoicesParameters(invoicesReportObject);
            JRDataSource dataSource = getDataSource();
            JasperPrint page = pdfHelper.fillPDF(template, parameters, dataSource);

            return createPDF(invoicesReportObject.getReportTitle().toLowerCase().replace(" ", "_") + "_" + invoicesReportObject.getPeriod().toLowerCase().replace(" ", "_") + "_" + invoicesReportObject.getReportDate(), Arrays.asList(page));
        } catch (JRException | IOException e) {
            throw new ReportException(e.getMessage());
        }
    }

    private JasperReport getCustomerFileTemplate() throws JRException {
        InputStream templateStream = ClassLoader.getSystemResourceAsStream("report/customer_file.jrxml");
        return pdfHelper.compileStreamToReport(templateStream);
    }

    private JasperReport getInvoiceReportTemplate() throws JRException {
        InputStream templateStream = ClassLoader.getSystemResourceAsStream("report/invoice_report.jrxml");
        return pdfHelper.compileStreamToReport(templateStream);
    }

    private JRDataSource getDataSource() {
        return new JREmptyDataSource();
    }

    private Map<String, Object> getCustomerFileParameters(CustomerFileReportObject customerFileReportObject) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("customerFile", customerFileReportObject);
        return parameters;
    }

    private Map<String, Object> getInvoicesParameters(InvoicesReportObject invoicesReportObject) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("invoiceReport", invoicesReportObject);
        return parameters;
    }

    private BestandDTO createPDF(String fileName, List<JasperPrint> pages) throws IOException, JRException {
        byte[] pdfReport = pdfHelper.createPDF(pages);

        BestandDTO bestandDTO = new BestandDTO();
        bestandDTO.setName(fileName + ".pdf");
        bestandDTO.setFile(pdfReport);
        writeFileToFileSystem(bestandDTO);

        return bestandDTO;
    }

    private void writeFileToFileSystem(BestandDTO report) throws IOException {
        File file = new File(Initializer.getDataUri() + "/files/" + report.getName());
        if (file.exists()) {
            file.delete();
        }
        FileUtils.writeByteArrayToFile(file, report.getFile());
    }
}
