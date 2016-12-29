package be.boomkwekerij.plant.controller;

import be.boomkwekerij.plant.model.dto.BestandDTO;
import be.boomkwekerij.plant.service.PrinterService;
import be.boomkwekerij.plant.service.PrinterServiceImpl;
import be.boomkwekerij.plant.service.ReportingService;
import be.boomkwekerij.plant.service.ReportingServiceImpl;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.Month;

public class ReportingController {

    private ReportingService reportingService = new ReportingServiceImpl();
    private PrinterService printerService = new PrinterServiceImpl();

    public CrudsResult printCustomerFileReport() {
        CrudsResult printResult = new CrudsResult();

        try {
            BestandDTO report = reportingService.createCustomerFileReport();
            printerService.printDocument_LandScape(report);
            printResult.setSuccess(true);
        } catch (Exception e) {
            return createCrudsError(e);
        }

        return printResult;
    }

    public CrudsResult printUnpayedInvoicesReport() {
        CrudsResult printResult = new CrudsResult();

        try {
            BestandDTO report = reportingService.createUnpayedInvoicesReport();
            //printerService.printDocument_Portrait(report);
            printResult.setSuccess(true);
        } catch (Exception e) {
            return createCrudsError(e);
        }

        return printResult;
    }

    public CrudsResult printInvoicesReport(String month, int year) {
        CrudsResult printResult = new CrudsResult();

        try {
            BestandDTO report = reportingService.createInvoicesReportForMonth(Month.fromTranslation(month), year);
            //printerService.printDocument_Portrait(report);
            printResult.setSuccess(true);
        } catch (Exception e) {
            return createCrudsError(e);
        }

        return printResult;
    }

    private CrudsResult createCrudsError(Exception e) {
        CrudsResult failure = new CrudsResult();
        failure.setSuccess(false);
        failure.getMessages().add(e.getMessage());
        return failure;
    }
}
