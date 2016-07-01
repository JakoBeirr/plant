package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.model.dto.CompanyDTO;
import be.boomkwekerij.plant.model.dto.SystemDTO;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;

public interface SystemService {

    CrudsResult createSystem(SystemDTO system);

    SearchResult<SystemDTO> getSystem();

    CrudsResult updateSystem(SystemDTO system);

    CrudsResult deleteSystem();

    String getNextInvoiceNumber();

    void setNextInvoiceNumber(String invoiceNumber);
}
