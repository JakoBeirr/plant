package be.boomkwekerij.plant.rapportage;

import be.boomkwekerij.plant.model.dto.FustDTO;
import be.boomkwekerij.plant.model.report.FustReportObject;
import be.boomkwekerij.plant.model.report.FustsReportObject;
import be.boomkwekerij.plant.util.DateFormatPattern;
import be.boomkwekerij.plant.util.DateUtils;
import org.joda.time.DateTime;

import java.util.ArrayList;
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

    public FustsReportObject createFustsReport(List<FustDTO> fusts, DateTime reportDate) {
        FustsReportObject fustsReportObject = new FustsReportObject();
        fustsReportObject.setReportDate(DateUtils.formatDate(reportDate, DateFormatPattern.DATE_FORMAT));
        ArrayList<FustReportObject> fustReportObjects = new ArrayList<>();
        for (FustDTO fust : fusts) {
            fustReportObjects.add(createFustReport(fust, reportDate));
        }
        fustsReportObject.setFusts(fustReportObjects);
        fustsReportObject.setTotalLageKisten(Integer.toString(fusts.stream().mapToInt(FustDTO::getLageKisten).sum()));
        fustsReportObject.setTotalHogeKisten(Integer.toString(fusts.stream().mapToInt(FustDTO::getHogeKisten).sum()));
        fustsReportObject.setTotalPalletBodem(Integer.toString(fusts.stream().mapToInt(FustDTO::getPalletBodem).sum()));
        fustsReportObject.setTotalBoxPallet(Integer.toString(fusts.stream().mapToInt(FustDTO::getBoxPallet).sum()));
        fustsReportObject.setTotalHalveBox(Integer.toString(fusts.stream().mapToInt(FustDTO::getHalveBox).sum()));
        fustsReportObject.setTotalFerroPalletKlein(Integer.toString(fusts.stream().mapToInt(FustDTO::getFerroPalletKlein).sum()));
        fustsReportObject.setTotalFerroPalletGroot(Integer.toString(fusts.stream().mapToInt(FustDTO::getFerroPalletGroot).sum()));
        fustsReportObject.setTotalKarrenEnBorden(Integer.toString(fusts.stream().mapToInt(FustDTO::getKarrenEnBorden).sum()));
        fustsReportObject.setTotalDiverse(Integer.toString(fusts.stream().mapToInt(FustDTO::getDiverse).sum()));
        return fustsReportObject;
    }
}
