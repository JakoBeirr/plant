package be.boomkwekerij.plant.controller;

import be.boomkwekerij.plant.model.dto.BestandDTO;
import be.boomkwekerij.plant.model.dto.FustDTO;
import be.boomkwekerij.plant.service.*;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;

import java.util.Collections;
import java.util.List;

public class FustController {

    private FustService fustService = new FustServiceImpl();
    private FustDocumentService fustDocumentService = new FustDocumentServiceImpl();
    private PrinterService printerService = new PrinterServiceImpl();

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

    public CrudsResult printFustReport(String fustId) {
        try {
            SearchResult<FustDTO> fustSearchResult = fustService.getFust(fustId);
            if (fustSearchResult.isSuccess()) {
                FustDTO fust = fustSearchResult.getFirst();
                BestandDTO fustReport = fustDocumentService.createFustReport(fust);
                printerService.printDocument_Portrait(fustReport);
                return new CrudsResult().success(fustId);
            }
            return new CrudsResult().error(fustSearchResult.getMessages());
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult printFustsReport() {
        try {
            SearchResult<FustDTO> fustSearchResult = fustService.getAllFusts();
            if (fustSearchResult.isSuccess()) {
                List<FustDTO> fusts = fustSearchResult.getResults();
                BestandDTO fustReport = fustDocumentService.createFustsReport(fusts);
                printerService.printDocument_Portrait(fustReport);
                return new CrudsResult().success();
            }
            return new CrudsResult().error(fustSearchResult.getMessages());
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }
}
