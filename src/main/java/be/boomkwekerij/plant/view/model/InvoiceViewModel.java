package be.boomkwekerij.plant.view.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class InvoiceViewModel {

    private SimpleStringProperty id = new SimpleStringProperty("");
    private SimpleStringProperty customerName = new SimpleStringProperty("");
    private SimpleStringProperty invoiceNumber = new SimpleStringProperty("");
    private SimpleStringProperty date = new SimpleStringProperty("");
    private SimpleDoubleProperty totalPrice = new SimpleDoubleProperty(0.0);
    private SimpleBooleanProperty payed = new SimpleBooleanProperty(false);

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public SimpleStringProperty customerNameProperty() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }

    public String getInvoiceNumber() {
        return invoiceNumber.get();
    }

    public SimpleStringProperty invoiceNumberProperty() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber.set(invoiceNumber);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public double getTotalPrice() {
        return totalPrice.get();
    }

    public SimpleDoubleProperty totalPriceProperty() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice.set(totalPrice);
    }

    public boolean getPayed() {
        return payed.get();
    }

    public SimpleBooleanProperty payedProperty() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed.set(payed);
    }
}
