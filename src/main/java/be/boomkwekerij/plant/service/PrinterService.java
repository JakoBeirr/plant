package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.exception.PrintException;

public interface PrinterService {

    void printDocument(byte[] invoiceDocument) throws PrintException;
}
