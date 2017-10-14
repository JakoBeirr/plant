package be.boomkwekerij.plant.controller;

import be.boomkwekerij.plant.model.dto.BestandDTO;
import be.boomkwekerij.plant.model.dto.FustDTO;
import be.boomkwekerij.plant.model.dto.FustOverviewDTO;
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

    public SearchResult<FustOverviewDTO> getAllFustOverviews() {
        try {
            return fustService.getAllFustOverviews();
        } catch (Exception e) {
            return new SearchResult<FustOverviewDTO>().error(Collections.singletonList(e.getMessage()));
        }
    }

    public SearchResult<FustOverviewDTO> getFustOverviewFromCustomerWithName(String customerName) {
        try {
            return fustService.getFustOverviewFromCustomerWithName(customerName);
        } catch (Exception e) {
            return new SearchResult<FustOverviewDTO>().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult printFustFromCustomerReport(String customerId) {
        try {
            SearchResult<FustDTO> fustSearchResult = fustService.getFustFromCustomer(customerId);
            if (fustSearchResult.isSuccess()) {
                List<FustDTO> fustFromCustomer = fustSearchResult.getResults();
                BestandDTO fustReport = fustDocumentService.createFustFromCustomerReport(fustFromCustomer);
                printerService.printDocument_LandScape(fustReport);
                return new CrudsResult().success();
            }
            return new CrudsResult().error(fustSearchResult.getMessages());
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult printFustFromAllCustomersReport() {
        try {
            SearchResult<FustOverviewDTO> fustSearchResult = fustService.getAllFustOverviews();
            if (fustSearchResult.isSuccess()) {
                List<FustOverviewDTO> fusts = fustSearchResult.getResults();
                BestandDTO fustReport = fustDocumentService.createFustFromAllCustomersReport(fusts);
                printerService.printDocument_LandScape(fustReport);
                return new CrudsResult().success();
            }
            return new CrudsResult().error(fustSearchResult.getMessages());
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }
}
