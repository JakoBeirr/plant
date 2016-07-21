package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.controller.PlantController;
import be.boomkwekerij.plant.model.dto.PlantDTO;
import be.boomkwekerij.plant.util.CrudsResult;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PlantCreateController implements Initializable {

    private PlantController plantController = new PlantController();

    @FXML
    private TextField name;
    @FXML
    private TextField age;
    @FXML
    private TextField measure;
    @FXML
    private TextField price;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTextFields();
    }

    public void createPlant(Event event) {
        try {
            CrudsResult plantCreateResult = createPlant();

            if (plantCreateResult.isSuccess()) {
                handleCreateSuccess();
            } else {
                handleCreateError(plantCreateResult.getMessages());
            }
        } catch (Exception e) {
            handleCreateException(e);
        }
    }

    private CrudsResult createPlant() {
        PlantDTO plantDTO = new PlantDTO();
        plantDTO.setName(name.getText());
        plantDTO.setAge(age.getText());
        plantDTO.setMeasure(measure.getText());
        plantDTO.setPrice(Double.parseDouble(price.getText()));
        return plantController.createPlant(plantDTO);
    }

    private void handleCreateSuccess() {
        initializeTextFields();
        AlertController.alertSuccess("Plant aangemaakt!");
    }

    private void initializeTextFields() {
        name.setText("");
        age.setText("");
        measure.setText("");
        price.setText("0.0");
    }

    private void handleCreateError(List<String> errorMessages) {
        StringBuilder errorBuilder = new StringBuilder("Gefaald wegens volgende fout(en): ");
        for (int i = 0; i < errorMessages.size(); i++) {
            String errorMessage = errorMessages.get(i);
            errorBuilder.append(errorMessage);

            if (i != (errorMessages.size()-1)) {
                errorBuilder.append("; ");
            }
        }
        AlertController.alertError("Plant aanmaken gefaald!", errorBuilder.toString());
    }

    private void handleCreateException(Exception e) {
        AlertController.alertException("Plant aanmaken gefaald!", e);
    }
}
