package be.boomkwekerij.plant.model.repository;

import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class InvoiceLine {

    private DateTime date;
    private int amount;
    private long plantId;
    private double price;
    private double totalPrice;

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

    public long getPlantId() {
        return plantId;
    }

    @XmlElement
    public void setPlantId(long plantId) {
        this.plantId = plantId;
    }

    public double getPrice() {
        return price;
    }

    @XmlElement
    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    @XmlElement
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
