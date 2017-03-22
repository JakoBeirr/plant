package be.boomkwekerij.plant.view.model;

import javafx.beans.property.SimpleStringProperty;

public class FustViewModel {

    private SimpleStringProperty id = new SimpleStringProperty("");
    private SimpleStringProperty customerName = new SimpleStringProperty("");

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
}
