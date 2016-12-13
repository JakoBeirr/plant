package be.boomkwekerij.plant.util;

import be.boomkwekerij.plant.exception.ReportException;
import be.boomkwekerij.plant.model.report.CustomerFileReportObject;
import net.sf.jasperreports.engine.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportPDFCreator {

    private PDFHelper pdfHelper = new PDFHelper();

    public byte[] createCustomerFileReport(CustomerFileReportObject customerFileReportObject) throws ReportException {
        try {
            JasperReport template = getCustomerFileTemplate();
            Map<String, Object> parameters = getCustomerFileParameters(customerFileReportObject);
            JRDataSource dataSource = getDataSource();
            JasperPrint page = pdfHelper.fillPDF(template, parameters, dataSource);

            return createPDF("klantenbestand", Arrays.asList(page));
        } catch (JRException | IOException e) {
            throw new ReportException(e.getMessage());
        }
    }

    private JasperReport getCustomerFileTemplate() throws JRException {
        InputStream templateStream = ClassLoader.getSystemResourceAsStream("report/customer_file.jrxml");
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

    private byte[] createPDF(String fileName, List<JasperPrint> pages) throws IOException, JRException {
        byte[] pdfReport = pdfHelper.createPdfReport(pages);
        writeFileToFileSystem(fileName, pdfReport);
        return pdfReport;
    }

    private void writeFileToFileSystem(String fileName, byte[] pdfReport) throws IOException {
        File file = new File(Initializer.getDataUri() + "/files/" + fileName + ".pdf");
        if (file.exists()) {
            file.delete();
        }
        FileUtils.writeByteArrayToFile(file, pdfReport);
    }
}
