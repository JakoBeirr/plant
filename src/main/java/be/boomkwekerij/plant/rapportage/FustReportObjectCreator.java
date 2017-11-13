package be.boomkwekerij.plant.rapportage;

import be.boomkwekerij.plant.model.dto.FustDTO;
import be.boomkwekerij.plant.model.dto.FustOverviewDTO;
import be.boomkwekerij.plant.model.report.FustOverviewReportObject;
import be.boomkwekerij.plant.model.report.FustReportObject;
import be.boomkwekerij.plant.model.report.FustsOverviewReportObject;
import be.boomkwekerij.plant.model.report.FustsReportObject;
import be.boomkwekerij.plant.util.DateFormatPattern;
import be.boomkwekerij.plant.util.DateUtils;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class FustReportObjectCreator {

    public FustsReportObject createFustsReport(List<FustDTO> fusts, DateTime reportDate) {
        FustsReportObject fustsReportObject = new FustsReportObject();
        fustsReportObject.setReportDate(DateUtils.formatDate(reportDate, DateFormatPattern.DATE_FORMAT));
        FustDTO fustDTO = fusts.get(0);
        fustsReportObject.setCustomerName(fustDTO.getCustomer().getName1() + " " + fustDTO.getCustomer().getName2());
        ArrayList<FustReportObject> fustReportObjects = new ArrayList<>();
        for (FustDTO fust : fusts) {
            fustReportObjects.add(createFustReport(fust));
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

    private FustReportObject createFustReport(FustDTO fustDTO) {
        FustReportObject fustReportObject = new FustReportObject();
        fustReportObject.setLageKisten(Integer.toString(fustDTO.getLageKisten()));
        fustReportObject.setHogeKisten(Integer.toString(fustDTO.getHogeKisten()));
        fustReportObject.setPalletBodem(Integer.toString(fustDTO.getPalletBodem()));
        fustReportObject.setBoxPallet(Integer.toString(fustDTO.getBoxPallet()));
        fustReportObject.setHalveBox(Integer.toString(fustDTO.getHalveBox()));
        fustReportObject.setFerroPalletKlein(Integer.toString(fustDTO.getFerroPalletKlein()));
        fustReportObject.setFerroPalletGroot(Integer.toString(fustDTO.getFerroPalletGroot()));
        fustReportObject.setKarrenEnBorden(Integer.toString(fustDTO.getKarrenEnBorden()));
        fustReportObject.setDiverse(Integer.toString(fustDTO.getDiverse()));
        fustReportObject.setDate(DateUtils.formatDate(fustDTO.getDatum(), DateFormatPattern.DATE_FORMAT));
        return fustReportObject;
    }

    public FustsOverviewReportObject createFustsOverviewReport(List<FustOverviewDTO> fusts, DateTime reportDate) {
        FustsOverviewReportObject fustsReportObject = new FustsOverviewReportObject();
        fustsReportObject.setReportDate(DateUtils.formatDate(reportDate, DateFormatPattern.DATE_FORMAT));
        ArrayList<FustOverviewReportObject> fustReportObjects = new ArrayList<>();
        for (FustOverviewDTO fust : fusts) {
            if (!emptyFust(fust)) {
                fustReportObjects.add(createFustOverviewReport(fust));
            }
        }
        fustsReportObject.setFusts(fustReportObjects);
        fustsReportObject.setTotalLageKisten(Integer.toString(fusts.stream().mapToInt(FustOverviewDTO::getLageKisten).sum()));
        fustsReportObject.setTotalHogeKisten(Integer.toString(fusts.stream().mapToInt(FustOverviewDTO::getHogeKisten).sum()));
        fustsReportObject.setTotalPalletBodem(Integer.toString(fusts.stream().mapToInt(FustOverviewDTO::getPalletBodem).sum()));
        fustsReportObject.setTotalBoxPallet(Integer.toString(fusts.stream().mapToInt(FustOverviewDTO::getBoxPallet).sum()));
        fustsReportObject.setTotalHalveBox(Integer.toString(fusts.stream().mapToInt(FustOverviewDTO::getHalveBox).sum()));
        fustsReportObject.setTotalFerroPalletKlein(Integer.toString(fusts.stream().mapToInt(FustOverviewDTO::getFerroPalletKlein).sum()));
        fustsReportObject.setTotalFerroPalletGroot(Integer.toString(fusts.stream().mapToInt(FustOverviewDTO::getFerroPalletGroot).sum()));
        fustsReportObject.setTotalKarrenEnBorden(Integer.toString(fusts.stream().mapToInt(FustOverviewDTO::getKarrenEnBorden).sum()));
        fustsReportObject.setTotalDiverse(Integer.toString(fusts.stream().mapToInt(FustOverviewDTO::getDiverse).sum()));
        return fustsReportObject;
    }

    private boolean emptyFust(FustOverviewDTO fust) {
        return fust.getLageKisten() == 0
                && fust.getHogeKisten() == 0
                && fust.getPalletBodem() == 0
                && fust.getBoxPallet() == 0
                && fust.getHalveBox() == 0
                && fust.getFerroPalletKlein() == 0
                && fust.getFerroPalletGroot() == 0
                && fust.getKarrenEnBorden() == 0
                && fust.getDiverse() == 0;
    }

    private FustOverviewReportObject createFustOverviewReport(FustOverviewDTO fustDTO) {
        FustOverviewReportObject fustReportObject = new FustOverviewReportObject();
        fustReportObject.setCustomerName(fustDTO.getCustomer().getName1() + " " + fustDTO.getCustomer().getName2());
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
