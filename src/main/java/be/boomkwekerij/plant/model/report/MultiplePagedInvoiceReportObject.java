package be.boomkwekerij.plant.model.report;

import java.util.ArrayList;
import java.util.List;

public class MultiplePagedInvoiceReportObject {

    private String invoiceDate;
    private String invoiceNumber;
    private List<InvoiceLineReportObject> invoiceLines = new ArrayList<InvoiceLineReportObject>();
    private Boolean hasOrderNumbers;
    private String transportPreviousPage;
    private String transportCurrentPage;
    private String subTotal;
    private List<BtwReportObject> btw;
    private String totalPrice;

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public List<InvoiceLineReportObject> getInvoiceLines() {
        return invoiceLines;
    }

    public void setInvoiceLines(List<InvoiceLineReportObject> invoiceLines) {
        this.invoiceLines = invoiceLines;
    }

    public Boolean getHasOrderNumbers() {
        return hasOrderNumbers;
    }

    public void setHasOrderNumbers(Boolean hasOrderNumbers) {
        this.hasOrderNumbers = hasOrderNumbers;
    }

    public String getTransportPreviousPage() {
        return transportPreviousPage;
    }

    public void setTransportPreviousPage(String transportPreviousPage) {
        this.transportPreviousPage = transportPreviousPage;
    }

    public String getTransportCurrentPage() {
        return transportCurrentPage;
    }

    public void setTransportCurrentPage(String transportCurrentPage) {
        this.transportCurrentPage = transportCurrentPage;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public List<BtwReportObject> getBtw() {
        return btw;
    }

    public void setBtw(List<BtwReportObject> btw) {
        this.btw = btw;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
