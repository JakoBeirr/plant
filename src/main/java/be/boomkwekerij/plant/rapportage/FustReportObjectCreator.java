package be.boomkwekerij.plant.rapportage;

import be.boomkwekerij.plant.model.dto.FustDTO;
import be.boomkwekerij.plant.model.report.FustReportObject;
import be.boomkwekerij.plant.util.DateFormatPattern;
import be.boomkwekerij.plant.util.DateUtils;
import org.joda.time.DateTime;

public class FustReportObjectCreator {

    public FustReportObject createFustReport(FustDTO fustDTO, DateTime reportDate) {
        FustReportObject fustReportObject = new FustReportObject();
        fustReportObject.setCustomerName(fustDTO.getCustomer().getName1() + " " + fustDTO.getCustomer().getName2());
        fustReportObject.setReportDate(DateUtils.formatDate(reportDate, DateFormatPattern.DATE_FORMAT));
        fustReportObject.setLageKisten(Integer.toString(fustDTO.getLageKisten()));
        fustReportObject.setHogeKisten(Integer.toString(fustDTO.getHogeKisten()));
        fustReportObject.setPalletBodem(Integer.toString(fustDTO.getPalletBodem()));
        fustReportObject.setBoxPallet(Integer.toString(fustDTO.getBoxPallet()));
        fustReportObject.setHalveBox(Integer.toString(fustDTO.getHalveBox()));
        fustReportObject.setFerroPalletKlein(Integer.toString(fustDTO.getFerroPalletKlein()));
        fustReportObject.setFerroPalletGroot(Integer.toString(fustDTO.getFerroPalletGroot()));
        fustReportObject.setKarrenEnBorden(Integer.toString(fustDTO.getKarrenEnBorden()));
        fustReportObject.setDiverse(Integer.toString(fustDTO.getDiverse()));
        return fustReportObject;
    }
}
