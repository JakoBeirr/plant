package be.boomkwekerij.plant.model.repository;

import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Invoice {

    private long id;
    private long customerId;
    private String invoiceNumber;
    private DateTime date;
    private int amount;
    private List<InvoiceLine> invoiceLines;
    private double totalPrice;
    private double btw;

    public long getId() {
        return id;
    }

    @XmlElement
    public void setId(long id) {
        this.id = id;
    }

    public long getCustomerId() {
        return customerId;
    }

    @XmlElement
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    @XmlElement
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public DateTime getDate() {
        return date;
    }

    @XmlElement
    public void setDate(DateTime date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    @XmlElement
    public void setAmount(int amount) {
        this.amount = amount;
    }

    public List<InvoiceLine> getInvoiceLines() {
        return invoiceLines;
    }

    @XmlElement
    public void setInvoiceLines(List<InvoiceLine> invoiceLines) {
        this.invoiceLines = invoiceLines;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    @XmlElement
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getBtw() {
        return btw;
    }

    @XmlElement
    public void setBtw(double btw) {
        this.btw = btw;
    }
}
