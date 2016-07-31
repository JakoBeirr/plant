package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.exception.PrintException;

public interface PrinterService {

    void printDocument(String name, byte[] invoiceDocument) throws PrintException;
}
