package be.boomkwekerij.plant.rapportage;

import be.boomkwekerij.plant.model.dto.FustDTO;
import be.boomkwekerij.plant.model.report.FustReportObject;
import be.boomkwekerij.plant.util.DateFormatPattern;
import be.boomkwekerij.plant.util.DateUtils;
import org.joda.time.DateTime;

import java.util.List;

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

    public FustReportObject createFustsReport(List<FustDTO> fusts, DateTime reportDate) {
        FustReportObject fustReportObject = new FustReportObject();
        fustReportObject.setCustomerName("/");
        fustReportObject.setReportDate(DateUtils.formatDate(reportDate, DateFormatPattern.DATE_FORMAT));
        fustReportObject.setLageKisten(Integer.toString(fusts.stream().mapToInt(FustDTO::getLageKisten).sum()));
        fustReportObject.setHogeKisten(Integer.toString(fusts.stream().mapToInt(FustDTO::getHogeKisten).sum()));
        fustReportObject.setPalletBodem(Integer.toString(fusts.stream().mapToInt(FustDTO::getPalletBodem).sum()));
        fustReportObject.setBoxPallet(Integer.toString(fusts.stream().mapToInt(FustDTO::getBoxPallet).sum()));
        fustReportObject.setHalveBox(Integer.toString(fusts.stream().mapToInt(FustDTO::getHalveBox).sum()));
        fustReportObject.setFerroPalletKlein(Integer.toString(fusts.stream().mapToInt(FustDTO::getFerroPalletKlein).sum()));
        fustReportObject.setFerroPalletGroot(Integer.toString(fusts.stream().mapToInt(FustDTO::getFerroPalletGroot).sum()));
        fustReportObject.setKarrenEnBorden(Integer.toString(fusts.stream().mapToInt(FustDTO::getKarrenEnBorden).sum()));
        fustReportObject.setDiverse(Integer.toString(fusts.stream().mapToInt(FustDTO::getDiverse).sum()));
        return fustReportObject;
    }
}
