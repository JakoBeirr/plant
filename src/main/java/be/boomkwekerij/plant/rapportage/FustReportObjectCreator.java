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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FustReportObjectCreator {

    private static final String YEAR_MONTH = "yyyy-MM";

    public FustsReportObject createFustsReport(List<FustDTO> fusts, DateTime reportDate) {
        FustsReportObject fustsReportObject = new FustsReportObject();
        fustsReportObject.setReportDate(DateUtils.formatDate(reportDate, DateFormatPattern.DATE_FORMAT));
        FustDTO fustDTO = fusts.get(0);
        fustsReportObject.setCustomerName(fustDTO.getCustomer().getName1() + " " + fustDTO.getCustomer().getName2());
        fustsReportObject.setStatePreviousMonthLageKisten(Integer.toString(fusts.stream()
                .filter(f -> !isInReportMonth(f, reportDate))
                .mapToInt(FustDTO::getLageKisten).sum()));
        fustsReportObject.setStatePreviousMonthHogeKisten(Integer.toString(fusts.stream()
                .filter(f -> !isInReportMonth(f, reportDate))
                .mapToInt(FustDTO::getHogeKisten).sum()));
        fustsReportObject.setStatePreviousMonthPalletBodem(Integer.toString(fusts.stream()
                .filter(f -> !isInReportMonth(f, reportDate))
                .mapToInt(FustDTO::getPalletBodem).sum()));
        fustsReportObject.setStatePreviousMonthBoxPallet(Integer.toString(fusts.stream()
                .filter(f -> !isInReportMonth(f, reportDate))
                .mapToInt(FustDTO::getBoxPallet).sum()));
        fustsReportObject.setStatePreviousMonthHalveBox(Integer.toString(fusts.stream()
                .filter(f -> !isInReportMonth(f, reportDate))
                .mapToInt(FustDTO::getHalveBox).sum()));
        fustsReportObject.setStatePreviousMonthFerroPalletKlein(Integer.toString(fusts.stream()
                .filter(f -> !isInReportMonth(f, reportDate))
                .mapToInt(FustDTO::getFerroPalletKlein).sum()));
        fustsReportObject.setStatePreviousMonthFerroPalletGroot(Integer.toString(fusts.stream()
                .filter(f -> !isInReportMonth(f, reportDate))
                .mapToInt(FustDTO::getFerroPalletGroot).sum()));
        fustsReportObject.setStatePreviousMonthKarrenEnBorden(Integer.toString(fusts.stream()
                .filter(f -> !isInReportMonth(f, reportDate))
                .mapToInt(FustDTO::getKarrenEnBorden).sum()));
        fustsReportObject.setStatePreviousMonthDiverse(Integer.toString(fusts.stream()
                .filter(f -> !isInReportMonth(f, reportDate))
                .mapToInt(FustDTO::getDiverse).sum()));
        List<FustReportObject> fustReportObjects = new ArrayList<>();

        for (FustDTO fust : fusts) {
            if (isInReportMonth(fust, reportDate)) {
                fustReportObjects.add(createFustReport(fust));
            }
        }

        fustsReportObject.setFustsInReportMonth(fustReportObjects);
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

    private boolean isInReportMonth(FustDTO f, DateTime reportDate) {
        String year_month_fust = (new SimpleDateFormat(YEAR_MONTH)).format(f.getDatum().toDate());
        String year_month_report = (new SimpleDateFormat(YEAR_MONTH)).format(reportDate.toDate());
        return year_month_fust.equals(year_month_report);
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
