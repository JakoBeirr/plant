package be.boomkwekerij.plant.model.dto;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class InvoiceDTO {

    private String id;
    private CustomerDTO customer;
    private String invoiceNumber;
    private DateTime date;
    private List<InvoiceLineDTO> invoiceLines = new ArrayList<InvoiceLineDTO>();
    private double subTotal;
    private List<BtwDTO> btw;
    private double totalPrice;
    private boolean payed;
    private double defaultBtw;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public List<InvoiceLineDTO> getInvoiceLines() {
        return invoiceLines;
    }

    public void setInvoiceLines(List<InvoiceLineDTO> invoiceLines) {
        this.invoiceLines = invoiceLines;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public List<BtwDTO> getBtw() {
        return btw;
    }

    public void setBtw(List<BtwDTO> btw) {
        this.btw = btw;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    public double getDefaultBtw() {
        return defaultBtw;
    }

    public void setDefaultBtw(double defaultBtw) {
        this.defaultBtw = defaultBtw;
    }
}
