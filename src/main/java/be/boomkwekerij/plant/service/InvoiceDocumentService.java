package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.exception.ReportException;

public interface InvoiceDocumentService {

    byte[] createInvoiceDocument(String id) throws ReportException;
}
