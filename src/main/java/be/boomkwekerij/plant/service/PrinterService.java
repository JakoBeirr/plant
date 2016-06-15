package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.model.dto.InvoiceDTO;

import java.awt.print.PrinterException;
import java.io.IOException;

public interface PrinterService {

    void printDocument(byte[] invoiceDocument) throws IOException, PrinterException;
}
