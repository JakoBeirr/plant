package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.model.dto.FustDTO;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;

public interface FustService {

    CrudsResult createFust(FustDTO fust);

    SearchResult<FustDTO> getFust(String id);

    SearchResult<FustDTO> getAllFusts();

    SearchResult<FustDTO> getFustFromCustomer(String customerId);

    SearchResult<FustDTO> getFustFromCustomerWithName(String customerName);

    CrudsResult deleteFust(String id);

    FustDTO getNewFustForCustomer(String customerId);
}
