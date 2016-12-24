package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.exception.PrintException;
import be.boomkwekerij.plant.model.dto.BestandDTO;

public interface PrinterService {

    void printDocument_Portrait(BestandDTO bestand) throws PrintException;

    void printDocument_LandScape(BestandDTO bestand) throws PrintException;
}
