package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.exception.ReportException;
import be.boomkwekerij.plant.util.Month;

public interface ReportingService {

    byte[] createCustomerFileReport() throws ReportException;

    byte[] createUnpayedInvoicesReport();

    byte[] createInvoicesReportForMonth(Month month);
}
