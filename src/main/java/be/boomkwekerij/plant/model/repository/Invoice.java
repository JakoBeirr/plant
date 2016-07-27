package be.boomkwekerij.plant.model.repository;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

@XmlRootElement
public class Invoice {

    private String id;
    private String customerId;
    private String invoiceNumber;
    private Date date;
    private List<InvoiceLine> invoiceLines;
    private double btw;
    private boolean payed;

    public String getId() {
        return id;
    }

    @XmlElement
    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    @XmlElement
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    @XmlElement
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Date getDate() {
        return date;
    }

    @XmlElement
    public void setDate(Date date) {
        this.date = date;
    }

    public List<InvoiceLine> getInvoiceLines() {
        return invoiceLines;
    }

    @XmlElement
    public void setInvoiceLines(List<InvoiceLine> invoiceLines) {
        this.invoiceLines = invoiceLines;
    }

    public double getBtw() {
        return btw;
    }

    @XmlElement
    public void setBtw(double btw) {
        this.btw = btw;
    }

    public boolean isPayed() {
        return payed;
    }

    @XmlElement
    public void setPayed(boolean payed) {
        this.payed = payed;
    }
}
