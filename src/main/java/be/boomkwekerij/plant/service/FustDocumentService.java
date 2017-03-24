package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.exception.ReportException;
import be.boomkwekerij.plant.model.dto.BestandDTO;
import be.boomkwekerij.plant.model.dto.FustDTO;

public interface FustDocumentService {

    BestandDTO createFustReport(FustDTO fustDTO) throws ReportException;
}
