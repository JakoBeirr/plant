package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.exception.ReportException;
import be.boomkwekerij.plant.model.dto.BestandDTO;
import be.boomkwekerij.plant.model.dto.FustDTO;
import be.boomkwekerij.plant.model.dto.FustOverviewDTO;
import org.joda.time.DateTime;

import java.util.List;

public interface FustDocumentService {

    BestandDTO createFustFromCustomerReport(List<FustDTO> fusts, DateTime date) throws ReportException;

    BestandDTO createFustFromAllCustomersReport(List<FustOverviewDTO> fusts, DateTime date) throws ReportException;
}
