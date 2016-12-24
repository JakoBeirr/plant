package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.exception.ReportException;
import be.boomkwekerij.plant.model.dto.BestandDTO;
import be.boomkwekerij.plant.model.dto.InvoiceDTO;

public interface InvoiceDocumentService {

    BestandDTO createInvoiceDocument(InvoiceDTO invoiceDTO) throws ReportException;

    BestandDTO createSellingConditionsDocument() throws ReportException;
}
