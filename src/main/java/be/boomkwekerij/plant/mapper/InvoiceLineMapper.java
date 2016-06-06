package be.boomkwekerij.plant.mapper;

import be.boomkwekerij.plant.model.dto.InvoiceLineDTO;
import be.boomkwekerij.plant.model.dto.PlantDTO;
import be.boomkwekerij.plant.model.repository.InvoiceLine;

public class InvoiceLineMapper {

    public InvoiceLine mapDTOToDAO(InvoiceLineDTO invoiceLineDTO) {
        InvoiceLine invoiceLine = new InvoiceLine();
        invoiceLine.setDate(invoiceLineDTO.getDate());
        invoiceLine.setAmount(invoiceLineDTO.getAmount());
        invoiceLine.setPlantId(invoiceLineDTO.getPlant().getId());
        invoiceLine.setPrice(invoiceLineDTO.getPrice());
        return invoiceLine;
    }

    public InvoiceLineDTO mapDAOToDTO(InvoiceLine invoiceLine) {
        InvoiceLineDTO invoiceLineDTO = new InvoiceLineDTO();
        invoiceLineDTO.setDate(invoiceLine.getDate());
        invoiceLineDTO.setAmount(invoiceLine.getAmount());
        invoiceLineDTO.setPlant(getPlant(invoiceLine.getPlantId()));
        invoiceLineDTO.setPrice(invoiceLine.getPrice());
        invoiceLineDTO.setTotalPrice(countTotalPrice(invoiceLine));
        return invoiceLineDTO;
    }

    private PlantDTO getPlant(String plantId) {
        PlantDTO plant = new PlantDTO();
        plant.setId(plantId);
        return plant;
    }

    private double countTotalPrice(InvoiceLine invoiceLine) {
        return invoiceLine.getAmount() * invoiceLine.getPrice();
    }
}
