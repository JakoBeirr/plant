package be.boomkwekerij.plant.model.dto;

import org.joda.time.DateTime;

public class InvoiceLineDTO {

    private DateTime date;
    private int amount;
    private PlantDTO plant;
    private double price;
    private double totalPrice;

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public PlantDTO getPlant() {
        return plant;
    }

    public void setPlant(PlantDTO plant) {
        this.plant = plant;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
