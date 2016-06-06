package be.boomkwekerij.plant.model.repository;

import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class InvoiceLine {

    private DateTime date;
    private int amount;
    private String plantId;
    private double price;

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

    public String getPlantId() {
        return plantId;
    }

    @XmlElement
    public void setPlantId(String plantId) {
        this.plantId = plantId;
    }

    public double getPrice() {
        return price;
    }

    @XmlElement
    public void setPrice(double price) {
        this.price = price;
    }
}
