package be.boomkwekerij.plant.mapper;

import be.boomkwekerij.plant.model.dto.InvoiceLineDTO;
import be.boomkwekerij.plant.model.repository.InvoiceLine;
import org.joda.time.DateTime;

public class InvoiceLineMapper {

    public InvoiceLine mapDTOToDAO(InvoiceLineDTO invoiceLineDTO) {
        InvoiceLine invoiceLine = new InvoiceLine();
        DateTime date = invoiceLineDTO.getDate();
        if (date == null) {
            date = new DateTime();
        }
        invoiceLine.setDate(date.toDate());
        invoiceLine.setOrderNumber(invoiceLineDTO.getOrderNumber());
        invoiceLine.setAmount(invoiceLineDTO.getAmount());
        invoiceLine.setPlantId(invoiceLineDTO.getPlantId());
        invoiceLine.setPlantName(invoiceLineDTO.getPlantName());
        invoiceLine.setRemark(invoiceLineDTO.getRemark() != null ? invoiceLineDTO.getRemark() : "");
        invoiceLine.setPlantAge(invoiceLineDTO.getPlantAge());
        invoiceLine.setPlantMeasure(invoiceLineDTO.getPlantMeasure());
        invoiceLine.setPlantPrice(invoiceLineDTO.getPlantPrice());
        invoiceLine.setPlantBtw(invoiceLineDTO.getBtw());
        return invoiceLine;
    }

    public InvoiceLineDTO mapDAOToDTO(InvoiceLine invoiceLine) {
        InvoiceLineDTO invoiceLineDTO = new InvoiceLineDTO();
        invoiceLineDTO.setDate(new DateTime(invoiceLine.getDate()));
        invoiceLineDTO.setOrderNumber(invoiceLine.getOrderNumber());
        invoiceLineDTO.setAmount(invoiceLine.getAmount());
        invoiceLineDTO.setPlantId(invoiceLine.getPlantId());
        invoiceLineDTO.setPlantName(invoiceLine.getPlantName());
        invoiceLineDTO.setRemark(invoiceLine.getRemark() != null ? invoiceLine.getRemark() : "");
        invoiceLineDTO.setPlantAge(invoiceLine.getPlantAge());
        invoiceLineDTO.setPlantMeasure(invoiceLine.getPlantMeasure());
        invoiceLineDTO.setPlantPrice(invoiceLine.getPlantPrice());
        double totalPrice = countTotalPrice(invoiceLine);
        invoiceLineDTO.setTotalPrice(totalPrice);
        invoiceLineDTO.setBtw(invoiceLine.getPlantBtw());
        invoiceLineDTO.setBtwAmount(countBtwAmount(totalPrice, invoiceLine.getPlantBtw()));
        return invoiceLineDTO;
    }

    private double countTotalPrice(InvoiceLine invoiceLine) {
        return invoiceLine.getAmount() * invoiceLine.getPlantPrice();
    }

    private double countBtwAmount(double totalPrice, double plantBtw) {
        return totalPrice * (plantBtw / 100);
    }
}
