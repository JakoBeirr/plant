package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.model.dto.FustDTO;
import be.boomkwekerij.plant.model.dto.FustOverviewDTO;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;

public interface FustService {

    CrudsResult createFust(FustDTO fust);

    SearchResult<FustDTO> getFustFromCustomer(String customerId);

    SearchResult<FustOverviewDTO> getAllFustOverviews();

    SearchResult<FustOverviewDTO> getFustOverviewFromCustomer(String customerId);

    SearchResult<FustOverviewDTO> getFustOverviewFromCustomerWithName(String customerName);
}
