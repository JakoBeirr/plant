package be.boomkwekerij.plant.rapportage;

import be.boomkwekerij.plant.model.dto.InvoiceLineDTO;
import be.boomkwekerij.plant.model.report.InvoiceLineReportObject;
import be.boomkwekerij.plant.util.DateFormatPattern;
import be.boomkwekerij.plant.util.DateUtils;
import be.boomkwekerij.plant.util.NumberUtils;

public class InvoiceLineReportObjectCreator {

    public InvoiceLineReportObject create(InvoiceLineDTO invoiceLineDTO) {
        InvoiceLineReportObject invoiceLineReportObject = new InvoiceLineReportObject();
        invoiceLineReportObject.setInvoiceLineDate(DateUtils.formatDate(invoiceLineDTO.getDate(), DateFormatPattern.DATE_NOYEAR_FORMAT));
        invoiceLineReportObject.setOrderNumber(invoiceLineDTO.getOrderNumber());
        invoiceLineReportObject.setPlantAmount(Integer.toString(invoiceLineDTO.getAmount()));
        invoiceLineReportObject.setPlantSpecies(invoiceLineDTO.getPlantName());
        invoiceLineReportObject.setPlantAge(invoiceLineDTO.getPlantAge());
        invoiceLineReportObject.setPlantMeasure(invoiceLineDTO.getPlantMeasure());
        invoiceLineReportObject.setPrice(NumberUtils.roundDouble(invoiceLineDTO.getPlantPrice(), 2) + " EUR");
        invoiceLineReportObject.setTotalPrice(NumberUtils.roundDouble(invoiceLineDTO.getTotalPrice(), 2) + " EUR");
        return invoiceLineReportObject;
    }

    public InvoiceLineReportObject createRemarkRow(InvoiceLineDTO invoiceLineDTO) {
        InvoiceLineReportObject invoiceLineReportObject = new InvoiceLineReportObject();
        invoiceLineReportObject.setInvoiceLineDate("");
        invoiceLineReportObject.setOrderNumber("");
        invoiceLineReportObject.setPlantAmount("");
        invoiceLineReportObject.setPlantSpecies(invoiceLineDTO.getRemark());
        invoiceLineReportObject.setPlantAge("");
        invoiceLineReportObject.setPlantMeasure("");
        invoiceLineReportObject.setPrice("");
        invoiceLineReportObject.setTotalPrice("");
        return invoiceLineReportObject;
    }
}
