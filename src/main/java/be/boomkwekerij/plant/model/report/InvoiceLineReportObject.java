package be.boomkwekerij.plant.model.report;

public class InvoiceLineReportObject {

    private String invoiceLineDate;
    private Integer plantAmount;
    private String plantSpecies;
    private String plantAgeAndMeasure;
    private String price;
    private String totalPrice;

    public String getInvoiceLineDate() {
        return invoiceLineDate;
    }

    public void setInvoiceLineDate(String invoiceLineDate) {
        this.invoiceLineDate = invoiceLineDate;
    }

    public Integer getPlantAmount() {
        return plantAmount;
    }

    public void setPlantAmount(Integer plantAmount) {
        this.plantAmount = plantAmount;
    }

    public String getPlantSpecies() {
        return plantSpecies;
    }

    public void setPlantSpecies(String plantSpecies) {
        this.plantSpecies = plantSpecies;
    }

    public String getPlantAgeAndMeasure() {
        return plantAgeAndMeasure;
    }

    public void setPlantAgeAndMeasure(String plantAgeAndMeasure) {
        this.plantAgeAndMeasure = plantAgeAndMeasure;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
