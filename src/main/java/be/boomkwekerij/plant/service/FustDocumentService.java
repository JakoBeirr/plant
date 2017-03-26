package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.exception.ReportException;
import be.boomkwekerij.plant.model.dto.BestandDTO;
import be.boomkwekerij.plant.model.dto.FustDTO;

import java.util.List;

public interface FustDocumentService {

    BestandDTO createFustReport(FustDTO fustDTO) throws ReportException;

    BestandDTO createFustsReport(List<FustDTO> fusts) throws ReportException;
}
