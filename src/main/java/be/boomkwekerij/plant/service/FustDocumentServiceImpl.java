package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.exception.ReportException;
import be.boomkwekerij.plant.model.dto.BestandDTO;
import be.boomkwekerij.plant.model.dto.FustDTO;
import be.boomkwekerij.plant.model.report.FustReportObject;
import be.boomkwekerij.plant.rapportage.FustReportObjectCreator;
import be.boomkwekerij.plant.util.FustPDFCreator;
import org.joda.time.DateTime;

import java.util.List;

public class FustDocumentServiceImpl implements FustDocumentService {

    private FustReportObjectCreator fustReportObjectCreator = new FustReportObjectCreator();

    private FustPDFCreator fustPDFCreator = new FustPDFCreator();

    @Override
    public BestandDTO createFustReport(FustDTO fustDTO) throws ReportException {
        DateTime reportDate = new DateTime();

        FustReportObject fustReport = fustReportObjectCreator.createFustReport(fustDTO, reportDate);
        return fustPDFCreator.createFustReport(fustReport);
    }

    @Override
    public BestandDTO createFustsReport(List<FustDTO> fusts) throws ReportException {
        DateTime reportDate = new DateTime();

        FustReportObject fustReport = fustReportObjectCreator.createFustsReport(fusts, reportDate);
        return fustPDFCreator.createFustReport(fustReport);
    }
}
