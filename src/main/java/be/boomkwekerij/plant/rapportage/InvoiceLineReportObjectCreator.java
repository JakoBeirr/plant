package be.boomkwekerij.plant.rapportage;

import be.boomkwekerij.plant.model.dto.InvoiceLineDTO;
import be.boomkwekerij.plant.model.report.InvoiceLineReportObject;
import be.boomkwekerij.plant.model.repository.InvoiceLine;
import be.boomkwekerij.plant.service.PlantService;
import be.boomkwekerij.plant.service.PlantServiceImpl;
import be.boomkwekerij.plant.util.DateFormatPattern;
import be.boomkwekerij.plant.util.DateUtils;
import be.boomkwekerij.plant.util.NumberUtils;
import org.joda.time.DateTime;

public class InvoiceLineReportObjectCreator {

    public InvoiceLineReportObject create(InvoiceLineDTO invoiceLineDTO) {
        InvoiceLineReportObject invoiceLineReportObject = new InvoiceLineReportObject();
        invoiceLineReportObject.setInvoiceLineDate(DateUtils.formatDate(invoiceLineDTO.getDate(), DateFormatPattern.DATE_NOYEAR_FORMAT));
        invoiceLineReportObject.setOrderNumber(invoiceLineDTO.getOrderNumber());
        invoiceLineReportObject.setPlantAmount(invoiceLineDTO.getAmount());
        invoiceLineReportObject.setPlantSpecies(invoiceLineDTO.getPlantName());
        invoiceLineReportObject.setPlantAge(invoiceLineDTO.getPlantAge());
        invoiceLineReportObject.setPlantMeasure(invoiceLineDTO.getPlantMeasure());
        invoiceLineReportObject.setPrice(NumberUtils.formatDouble(invoiceLineDTO.getPlantPrice(), 2));
        invoiceLineReportObject.setTotalPrice(NumberUtils.formatDouble(invoiceLineDTO.getTotalPrice(), 2));
        return invoiceLineReportObject;
    }
}
