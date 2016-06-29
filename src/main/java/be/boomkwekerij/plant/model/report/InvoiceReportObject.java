package be.boomkwekerij.plant.model.report;

import java.util.ArrayList;
import java.util.List;

public class InvoiceReportObject {

    private String invoiceDate;
    private String invoiceNumber;
    private List<InvoiceLineReportObject> invoiceLines = new ArrayList<InvoiceLineReportObject>();
    private String subTotal;
    private String btw;
    private String btwAmount;
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

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getBtw() {
        return btw;
    }

    public void setBtw(String btw) {
        this.btw = btw;
    }

    public String getBtwAmount() {
        return btwAmount;
    }

    public void setBtwAmount(String btwAmount) {
        this.btwAmount = btwAmount;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
