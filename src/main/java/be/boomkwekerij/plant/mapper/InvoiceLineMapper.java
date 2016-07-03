package be.boomkwekerij.plant.mapper;

import be.boomkwekerij.plant.model.dto.InvoiceLineDTO;
import be.boomkwekerij.plant.model.dto.PlantDTO;
import be.boomkwekerij.plant.model.report.InvoiceLineReportObject;
import be.boomkwekerij.plant.model.repository.InvoiceLine;
import be.boomkwekerij.plant.service.PlantService;
import be.boomkwekerij.plant.service.PlantServiceImpl;
import be.boomkwekerij.plant.util.DateFormatPattern;
import be.boomkwekerij.plant.util.DateUtils;
import be.boomkwekerij.plant.util.NumberUtils;
import be.boomkwekerij.plant.util.SearchResult;
import org.joda.time.DateTime;

public class InvoiceLineMapper {

    private PlantService plantService = new PlantServiceImpl();

    public InvoiceLine mapDTOToDAO(InvoiceLineDTO invoiceLineDTO) {
        InvoiceLine invoiceLine = new InvoiceLine();
        DateTime date = invoiceLineDTO.getDate();
        if (date == null) {
            date = new DateTime();
        }
        invoiceLine.setDate(date.toDate());
        invoiceLine.setAmount(invoiceLineDTO.getAmount());
        invoiceLine.setPlantId(invoiceLineDTO.getPlant().getId());
        invoiceLine.setPrice(invoiceLineDTO.getPrice());
        return invoiceLine;
    }

    public InvoiceLineDTO mapDAOToDTO(InvoiceLine invoiceLine) {
        InvoiceLineDTO invoiceLineDTO = new InvoiceLineDTO();
        invoiceLineDTO.setDate(new DateTime(invoiceLine.getDate()));
        invoiceLineDTO.setAmount(invoiceLine.getAmount());
        invoiceLineDTO.setPlant(getPlant(invoiceLine.getPlantId()));
        invoiceLineDTO.setPrice(invoiceLine.getPrice());
        invoiceLineDTO.setTotalPrice(countTotalPrice(invoiceLine));
        return invoiceLineDTO;
    }

    private PlantDTO getPlant(String plantId) {
        SearchResult<PlantDTO> plantSearchResult = plantService.getPlant(plantId);

        if (plantSearchResult.isSuccess()) {
            return plantSearchResult.getFirst();
        }
        return null;
    }

    private double countTotalPrice(InvoiceLine invoiceLine) {
        return invoiceLine.getAmount() * invoiceLine.getPrice();
    }

    public InvoiceLineReportObject mapDTOToReportObject(InvoiceLineDTO invoiceLineDTO) {
        InvoiceLineReportObject invoiceLineReportObject = new InvoiceLineReportObject();
        invoiceLineReportObject.setInvoiceLineDate(DateUtils.formatDate(invoiceLineDTO.getDate(), DateFormatPattern.DATE_NOYEAR_FORMAT));
        invoiceLineReportObject.setPlantAmount(invoiceLineDTO.getAmount());
        invoiceLineReportObject.setPlantSpecies(invoiceLineDTO.getPlant().getName());
        invoiceLineReportObject.setPlantAgeAndMeasure(invoiceLineDTO.getPlant().getAge() + "/" + invoiceLineDTO.getPlant().getMeasure());
        invoiceLineReportObject.setPrice(NumberUtils.formatDouble(invoiceLineDTO.getPrice(), 3));
        invoiceLineReportObject.setTotalPrice(NumberUtils.formatDouble(invoiceLineDTO.getTotalPrice(), 2));
        return invoiceLineReportObject;
    }


}
