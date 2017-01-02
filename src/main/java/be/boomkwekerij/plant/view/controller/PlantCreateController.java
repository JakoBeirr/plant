package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.view.services.PlantCreateService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class PlantCreateController implements PageController {

    private PlantCreateService plantCreateService = new PlantCreateService();

    @FXML
    private TextField name;
    @FXML
    private TextField age;
    @FXML
    private TextField measure;
    @FXML
    private TextField price;
    @FXML
    private Button plantCreateButton;

    private static final String NON_DECIMAL_NUMERIC_CHARACTERS = "[^\\d.]";

    @Override
    public void init(Pane root) {
        plantCreateService.setName(name);
        plantCreateService.setAge(age);
        plantCreateService.setMeasure(measure);
        plantCreateService.setPrice(price);
        plantCreateService.setPlantCreateButton(plantCreateButton);
        plantCreateService.init(root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initNumericField();
    }

    private void initNumericField() {
        price.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                price.setText(newValue.replaceAll(NON_DECIMAL_NUMERIC_CHARACTERS, ""));
            }
        });
    }

    public void createPlant(Event event) {
        plantCreateService.createPlantService.restart();
    }
}
