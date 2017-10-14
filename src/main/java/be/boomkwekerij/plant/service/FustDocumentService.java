package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.exception.ReportException;
import be.boomkwekerij.plant.model.dto.BestandDTO;
import be.boomkwekerij.plant.model.dto.FustDTO;
import be.boomkwekerij.plant.model.dto.FustOverviewDTO;

import java.util.List;

public interface FustDocumentService {

    BestandDTO createFustFromCustomerReport(List<FustDTO> fusts) throws ReportException;

    BestandDTO createFustFromAllCustomersReport(List<FustOverviewDTO> fusts) throws ReportException;
}
