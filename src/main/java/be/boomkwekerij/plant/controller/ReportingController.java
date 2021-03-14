package be.boomkwekerij.plant.controller;

import be.boomkwekerij.plant.model.dto.BestandDTO;
import be.boomkwekerij.plant.service.PrinterService;
import be.boomkwekerij.plant.service.PrinterServiceImpl;
import be.boomkwekerij.plant.service.ReportingService;
import be.boomkwekerij.plant.service.ReportingServiceImpl;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.Month;

import java.util.Collections;

public class ReportingController {

    private ReportingService reportingService = new ReportingServiceImpl();
    private PrinterService printerService = new PrinterServiceImpl();

    public CrudsResult printCustomerFileReport() {
        try {
            BestandDTO report = reportingService.createCustomerFileReport();
            printerService.printDocumentInLandScapeMode(report);
            return new CrudsResult().success();
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult printUnpayedInvoicesReport() {
        try {
            BestandDTO report = reportingService.createUnpayedInvoicesReport();
            printerService.printDocumentInPortraitMode(report);
            return new CrudsResult().success();
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult printInvoicesReport(String month, int year) {
        try {
            BestandDTO report = reportingService.createInvoicesReportForMonth(Month.fromTranslation(month), year);
            printerService.printDocumentInPortraitMode(report);
            return new CrudsResult().success();
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }
}
