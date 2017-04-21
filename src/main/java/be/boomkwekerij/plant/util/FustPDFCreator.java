package be.boomkwekerij.plant.util;

import be.boomkwekerij.plant.exception.ReportException;
import be.boomkwekerij.plant.model.dto.BestandDTO;
import be.boomkwekerij.plant.model.report.FustReportObject;
import be.boomkwekerij.plant.model.report.FustsReportObject;
import net.sf.jasperreports.engine.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FustPDFCreator {

    private PDFHelper pdfHelper = new PDFHelper();

    public BestandDTO createFustReport(FustReportObject fustReportObject) throws ReportException {
        try {
            JasperReport template = getFustReportTemplate();
            Map<String, Object> parameters = getFustReportParameters(fustReportObject);
            JRDataSource dataSource = getDataSource();
            JasperPrint page = pdfHelper.fillPDF(template, parameters, dataSource);

            return createPDF("fust_" + fustReportObject.getCustomerName().replaceAll(" ", "_") + "_" + fustReportObject.getReportDate(), Arrays.asList(page));
        } catch (Exception e) {
            throw new ReportException(e.getMessage());
        }
    }

    public BestandDTO createFustsReport(FustsReportObject fustsReportObject) throws ReportException {
        try {
            JasperReport template = getFustsReportTemplate();
            Map<String, Object> parameters = getFustsReportParameters(fustsReportObject);
            JRDataSource dataSource = getDataSource();
            JasperPrint page = pdfHelper.fillPDF(template, parameters, dataSource);

            return createPDF("fusts_" + fustsReportObject.getReportDate(), Arrays.asList(page));
        } catch (Exception e) {
            throw new ReportException(e.getMessage());
        }
    }

    private JasperReport getFustReportTemplate() throws JRException {
        InputStream templateStream = ClassLoader.getSystemResourceAsStream("report/fust_report.jrxml");
        return pdfHelper.compileStreamToReport(templateStream);
    }

    private JasperReport getFustsReportTemplate() throws JRException {
        InputStream templateStream = ClassLoader.getSystemResourceAsStream("report/fusts_report.jrxml");
        return pdfHelper.compileStreamToReport(templateStream);
    }

    private JRDataSource getDataSource() {
        return new JREmptyDataSource();
    }

    private Map<String, Object> getFustReportParameters(FustReportObject fustReportObject) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("fustReport", fustReportObject);
        return parameters;
    }

    private Map<String, Object> getFustsReportParameters(FustsReportObject fustsReportObject) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("fustsReport", fustsReportObject);
        return parameters;
    }

    private BestandDTO createPDF(String fileName, List<JasperPrint> pages) throws IOException, JRException {
        byte[] pdfReport = pdfHelper.createPDF(pages);

        BestandDTO bestandDTO = new BestandDTO();
        bestandDTO.setName(fileName + ".pdf");
        bestandDTO.setFile(pdfReport);

        return bestandDTO;
    }
}
