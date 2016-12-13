package be.boomkwekerij.plant.controller;

import be.boomkwekerij.plant.service.PrinterService;
import be.boomkwekerij.plant.service.PrinterServiceImpl;
import be.boomkwekerij.plant.service.ReportingService;
import be.boomkwekerij.plant.service.ReportingServiceImpl;
import be.boomkwekerij.plant.util.CrudsResult;

public class ReportingController {

    private ReportingService reportingService = new ReportingServiceImpl();
    private PrinterService printerService = new PrinterServiceImpl();

    public CrudsResult printCustomerFileReport() {
        CrudsResult printResult = new CrudsResult();

        try {
            byte[] invoiceDocument = reportingService.createCustomerFileReport();
            printerService.printDocument("klantenbestand", invoiceDocument);
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
