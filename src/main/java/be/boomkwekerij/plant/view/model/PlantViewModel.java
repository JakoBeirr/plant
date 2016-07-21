package be.boomkwekerij.plant.view.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class PlantViewModel {

    private final SimpleStringProperty id = new SimpleStringProperty("");
    private final SimpleStringProperty name = new SimpleStringProperty("");
    private final SimpleStringProperty age = new SimpleStringProperty("");
    private final SimpleStringProperty measure = new SimpleStringProperty("");
    private final SimpleDoubleProperty price = new SimpleDoubleProperty(0.0);

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getAge() {
        return age.get();
    }

    public void setAge(String age) {
        this.age.set(age);
    }

    public String getMeasure() {
        return measure.get();
    }

    public void setMeasure(String measure) {
        this.measure.set(measure);
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }
}
