package be.boomkwekerij.plant.model.dto;

import org.joda.time.DateTime;

public class InvoiceLineDTO {

    private DateTime date;
    private String orderNumber;
    private int amount;
    private String plantId;
    private String plantName;
    private String plantAge;
    private String plantMeasure;
    private double plantPrice;
    private double totalPrice;

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPlantId() {
        return plantId;
    }

    public void setPlantId(String plantId) {
        this.plantId = plantId;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getPlantAge() {
        return plantAge;
    }

    public void setPlantAge(String plantAge) {
        this.plantAge = plantAge;
    }

    public String getPlantMeasure() {
        return plantMeasure;
    }

    public void setPlantMeasure(String plantMeasure) {
        this.plantMeasure = plantMeasure;
    }

    public double getPlantPrice() {
        return plantPrice;
    }

    public void setPlantPrice(double plantPrice) {
        this.plantPrice = plantPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
