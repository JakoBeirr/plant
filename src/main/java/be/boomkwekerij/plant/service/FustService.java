package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.model.dto.FustDTO;
import be.boomkwekerij.plant.model.dto.FustOverviewDTO;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;
import org.joda.time.DateTime;

public interface FustService {

    CrudsResult createFust(FustDTO fust);

    SearchResult<FustDTO> getFustFromCustomer(String customerId);

    SearchResult<FustDTO> getFustFromCustomer(String customerId, DateTime date);

    SearchResult<FustOverviewDTO> getAllFustOverviews();

    SearchResult<FustOverviewDTO> getAllFustOverviews(DateTime date);

    SearchResult<FustOverviewDTO> getFustOverviewFromCustomer(String customerId);

    SearchResult<FustOverviewDTO> getFustOverviewFromCustomer(String customerId, DateTime date);

    SearchResult<FustOverviewDTO> getFustOverviewFromCustomerWithName(String customerName);
}
