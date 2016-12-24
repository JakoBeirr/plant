package be.boomkwekerij.plant.util;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.ExporterInputItem;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleExporterInputItem;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PDFHelper {

    public JasperReport compileStreamToReport(InputStream inputStream) throws JRException {
        return JasperCompileManager.compileReport(inputStream);
    }

    public JasperPrint fillPDF(JasperReport invoiceTemplate, Map<String, Object> parameters, JRDataSource dataSource) throws JRException {
        return JasperFillManager.fillReport(invoiceTemplate, parameters, dataSource);
    }

    public byte[] createPDF(List<JasperPrint> pages) throws JRException {
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
}
