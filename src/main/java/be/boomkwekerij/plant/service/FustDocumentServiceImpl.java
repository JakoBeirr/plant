package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.exception.ReportException;
import be.boomkwekerij.plant.model.dto.BestandDTO;
import be.boomkwekerij.plant.model.dto.FustDTO;
import be.boomkwekerij.plant.model.dto.FustOverviewDTO;
import be.boomkwekerij.plant.model.report.FustReportObject;
import be.boomkwekerij.plant.model.report.FustsOverviewReportObject;
import be.boomkwekerij.plant.model.report.FustsReportObject;
import be.boomkwekerij.plant.rapportage.FustReportObjectCreator;
import be.boomkwekerij.plant.util.FustPDFCreator;
import org.joda.time.DateTime;

import java.util.List;

public class FustDocumentServiceImpl implements FustDocumentService {

    private FustReportObjectCreator fustReportObjectCreator = new FustReportObjectCreator();

    private FustPDFCreator fustPDFCreator = new FustPDFCreator();

    @Override
    public BestandDTO createFustFromCustomerReport(List<FustDTO> fusts) throws ReportException {
        DateTime reportDate = new DateTime();

        FustsReportObject fustsReport = fustReportObjectCreator.createFustsReport(fusts, reportDate);
        return fustPDFCreator.createFustsReport(fustsReport);
    }

    @Override
    public BestandDTO createFustFromAllCustomersReport(List<FustOverviewDTO> fusts) throws ReportException {
        DateTime reportDate = new DateTime();

        FustsOverviewReportObject fustsOverviewReport = fustReportObjectCreator.createFustsOverviewReport(fusts, reportDate);
        return fustPDFCreator.createFustsOverviewReport(fustsOverviewReport);
    }
}
