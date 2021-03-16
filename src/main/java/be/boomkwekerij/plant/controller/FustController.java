package be.boomkwekerij.plant.controller;

import be.boomkwekerij.plant.model.dto.BestandDTO;
import be.boomkwekerij.plant.model.dto.DateDTO;
import be.boomkwekerij.plant.model.dto.FustDTO;
import be.boomkwekerij.plant.model.dto.FustOverviewDTO;
import be.boomkwekerij.plant.service.FustDocumentService;
import be.boomkwekerij.plant.service.FustDocumentServiceImpl;
import be.boomkwekerij.plant.service.FustService;
import be.boomkwekerij.plant.service.FustServiceImpl;
import be.boomkwekerij.plant.service.PrinterService;
import be.boomkwekerij.plant.service.PrinterServiceImpl;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;

import java.util.Collections;
import java.util.List;

public class FustController {

    private final FustService fustService = new FustServiceImpl();
    private final FustDocumentService fustDocumentService = new FustDocumentServiceImpl();
    private final PrinterService printerService = new PrinterServiceImpl();

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

    public CrudsResult printFustFromCustomerReport(String customerId, DateDTO date) {
        try {
            SearchResult<FustDTO> fustSearchResult = fustService.getFustFromCustomer(customerId, date.getDate());
            if (fustSearchResult.isSuccess()) {
                List<FustDTO> fustFromCustomer = fustSearchResult.getResults();
                if (fustFromCustomer.isEmpty()) {
                    return new CrudsResult().error(Collections.singletonList("Geen fust gevonden voor klant met id: " + customerId));
                }
                BestandDTO fustReport = fustDocumentService.createFustFromCustomerReport(fustFromCustomer, date.getDate());
                printerService.printDocumentInLandScapeMode(fustReport);
                return new CrudsResult().success();
            }
            return new CrudsResult().error(fustSearchResult.getMessages());
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult printFustFromAllCustomersReport(DateDTO date) {
        try {
            SearchResult<FustOverviewDTO> fustSearchResult = fustService.getAllFustOverviews(date.getDate());
            if (fustSearchResult.isSuccess()) {
                List<FustOverviewDTO> fusts = fustSearchResult.getResults();
                BestandDTO fustReport = fustDocumentService.createFustFromAllCustomersReport(fusts, date.getDate());
                printerService.printDocumentInLandScapeMode(fustReport);
                return new CrudsResult().success();
            }
            return new CrudsResult().error(fustSearchResult.getMessages());
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }
}
