package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.exception.PrintException;
import be.boomkwekerij.plant.model.dto.BestandDTO;

public interface PrinterService {

    void printDocumentInPortraitMode(BestandDTO bestand) throws PrintException;

    void printDocumentInLandScapeMode(BestandDTO bestand) throws PrintException;
}
