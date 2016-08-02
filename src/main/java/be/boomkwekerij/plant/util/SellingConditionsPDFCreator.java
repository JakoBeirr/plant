package be.boomkwekerij.plant.util;

import be.boomkwekerij.plant.exception.ReportException;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellingConditionsPDFCreator {

    private PDFHelper pdfHelper = new PDFHelper();

    public byte[] createSellingConditionsDocument() throws ReportException {
        try {
            List<JasperPrint> pages = new ArrayList<>();

            JasperReport sellingConditionsTemplate = getTemplate();
            Map<String, Object> parameters = getParameters();
            JRDataSource dataSource = getDataSource();
            JasperPrint page = pdfHelper.fillPDF(sellingConditionsTemplate, parameters, dataSource);
            pages.add(page);

            return pdfHelper.createPdfReport(pages);
        } catch (JRException e) {
            throw new ReportException(e.getMessage());
        }
    }

    private JasperReport getTemplate() throws JRException {
        InputStream sellingConditionsTemplateStream = ClassLoader.getSystemResourceAsStream("invoiceDocument/selling_conditions.jrxml");
        return pdfHelper.compileStreamToReport(sellingConditionsTemplateStream);
    }

    private JRDataSource getDataSource() {
        return new JREmptyDataSource();
    }

    private Map<String, Object> getParameters() {
        return new HashMap<String, Object>();
    }
}
