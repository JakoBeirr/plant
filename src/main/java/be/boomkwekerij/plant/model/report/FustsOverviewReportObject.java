package be.boomkwekerij.plant.model.report;

import java.util.List;

public class FustsOverviewReportObject {

    private String reportDate;
    private List<FustOverviewReportObject> fusts;
    private String totalLageKisten;
    private String totalHogeKisten;
    private String totalPalletBodem;
    private String totalBoxPallet;
    private String totalHalveBox;
    private String totalFerroPalletKlein;
    private String totalFerroPalletGroot;
    private String totalKarren;
    private String totalBorden;
    private String totalDiverse;

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public List<FustOverviewReportObject> getFusts() {
        return fusts;
    }

    public void setFusts(List<FustOverviewReportObject> fusts) {
        this.fusts = fusts;
    }

    public String getTotalLageKisten() {
        return totalLageKisten;
    }

    public void setTotalLageKisten(String totalLageKisten) {
        this.totalLageKisten = totalLageKisten;
    }

    public String getTotalHogeKisten() {
        return totalHogeKisten;
    }

    public void setTotalHogeKisten(String totalHogeKisten) {
        this.totalHogeKisten = totalHogeKisten;
    }

    public String getTotalPalletBodem() {
        return totalPalletBodem;
    }

    public void setTotalPalletBodem(String totalPalletBodem) {
        this.totalPalletBodem = totalPalletBodem;
    }

    public String getTotalBoxPallet() {
        return totalBoxPallet;
    }

    public void setTotalBoxPallet(String totalBoxPallet) {
        this.totalBoxPallet = totalBoxPallet;
    }

    public String getTotalHalveBox() {
        return totalHalveBox;
    }

    public void setTotalHalveBox(String totalHalveBox) {
        this.totalHalveBox = totalHalveBox;
    }

    public String getTotalFerroPalletKlein() {
        return totalFerroPalletKlein;
    }

    public void setTotalFerroPalletKlein(String totalFerroPalletKlein) {
        this.totalFerroPalletKlein = totalFerroPalletKlein;
    }

    public String getTotalFerroPalletGroot() {
        return totalFerroPalletGroot;
    }

    public void setTotalFerroPalletGroot(String totalFerroPalletGroot) {
        this.totalFerroPalletGroot = totalFerroPalletGroot;
    }

    public String getTotalKarren() {
        return totalKarren;
    }

    public void setTotalKarren(String totalKarren) {
        this.totalKarren = totalKarren;
    }

    public String getTotalBorden() {
        return totalBorden;
    }

    public void setTotalBorden(String totalBorden) {
        this.totalBorden = totalBorden;
    }

    public String getTotalDiverse() {
        return totalDiverse;
    }

    public void setTotalDiverse(String totalDiverse) {
        this.totalDiverse = totalDiverse;
    }
}
