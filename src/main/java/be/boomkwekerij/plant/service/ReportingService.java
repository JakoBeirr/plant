package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.exception.ReportException;
import be.boomkwekerij.plant.model.dto.BestandDTO;
import be.boomkwekerij.plant.util.Month;

public interface ReportingService {

    BestandDTO createCustomerFileReport() throws ReportException;

    BestandDTO createUnpayedInvoicesReport() throws ReportException;

    BestandDTO createInvoicesReportForMonth(Month month, int year) throws ReportException;
}
