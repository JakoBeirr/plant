package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.exception.ReportException;
import be.boomkwekerij.plant.model.dto.InvoiceDTO;

public interface InvoiceDocumentService {

    byte[] createInvoiceDocument(InvoiceDTO invoiceDTO) throws ReportException;
}
