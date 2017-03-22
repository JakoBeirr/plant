package be.boomkwekerij.plant.controller;

import be.boomkwekerij.plant.model.dto.FustDTO;
import be.boomkwekerij.plant.service.FustService;
import be.boomkwekerij.plant.service.FustServiceImpl;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;

import java.util.Collections;

public class FustController {

    private FustService fustService = new FustServiceImpl();

    public CrudsResult createFust(FustDTO fustDTO) {
        try {
            return fustService.createFust(fustDTO);
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    public FustDTO makeNewFust(String customerId) {
        return fustService.getNewFustForCustomer(customerId);
    }

    public SearchResult<FustDTO> getFust(String id) {
        try {
            return fustService.getFust(id);
        } catch (Exception e) {
            return new SearchResult<FustDTO>().error(Collections.singletonList(e.getMessage()));
        }
    }

    public SearchResult<FustDTO> getAllFusts() {
        try {
            return fustService.getAllFusts();
        } catch (Exception e) {
            return new SearchResult<FustDTO>().error(Collections.singletonList(e.getMessage()));
        }
    }

    public SearchResult<FustDTO> getFustFromCustomer(String customerId) {
        try {
            return fustService.getFustFromCustomer(customerId);
        } catch (Exception e) {
            return new SearchResult<FustDTO>().error(Collections.singletonList(e.getMessage()));
        }
    }

    public SearchResult<FustDTO> getFustFromCustomerWithName(String customerName) {
        try {
            return fustService.getFustFromCustomerWithName(customerName);
        } catch (Exception e) {
            return new SearchResult<FustDTO>().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult deleteFust(String id) {
        try {
            return fustService.deleteFust(id);
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }
}
