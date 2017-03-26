package be.boomkwekerij.plant.model.repository;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class InvoiceLine {

    private Date date;
    private String orderNumber;
    private int amount;
    private String plantId;
    private String plantName;
    private String remark;
    private String plantAge;
    private String plantMeasure;
    private double plantPrice;
    private double plantBtw;

    public Date getDate() {
        return date;
    }

    @XmlElement
    public void setDate(Date date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    @XmlElement
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
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

    public String getPlantName() {
        return plantName;
    }

    @XmlElement
    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getPlantAge() {
        return plantAge;
    }

    public String getRemark() {
        return remark;
    }

    @XmlElement
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @XmlElement
    public void setPlantAge(String plantAge) {
        this.plantAge = plantAge;
    }

    public String getPlantMeasure() {
        return plantMeasure;
    }

    @XmlElement
    public void setPlantMeasure(String plantMeasure) {
        this.plantMeasure = plantMeasure;
    }

    public double getPlantPrice() {
        return plantPrice;
    }

    @XmlElement
    public void setPlantPrice(double plantPrice) {
        this.plantPrice = plantPrice;
    }

    public double getPlantBtw() {
        return plantBtw;
    }

    @XmlElement
    public void setPlantBtw(double plantBtw) {
        this.plantBtw = plantBtw;
    }
}
