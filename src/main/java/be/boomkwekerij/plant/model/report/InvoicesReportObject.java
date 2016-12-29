package be.boomkwekerij.plant.model.report;

import java.util.List;

public class InvoicesReportObject {

    private String companyName;
    private String reportDate;
    private String period;
    private String reportTitle;
    private List<InvoicesInvoiceReportObject> invoices;
    private String totalExclusive;
    private String totalInclusive;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public List<InvoicesInvoiceReportObject> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<InvoicesInvoiceReportObject> invoices) {
        this.invoices = invoices;
    }

    public String getTotalExclusive() {
        return totalExclusive;
    }

    public void setTotalExclusive(String totalExclusive) {
        this.totalExclusive = totalExclusive;
    }

    public String getTotalInclusive() {
        return totalInclusive;
    }

    public void setTotalInclusive(String totalInclusive) {
        this.totalInclusive = totalInclusive;
    }
}
