package be.boomkwekerij.plant.view.services;

import be.boomkwekerij.plant.controller.PlantController;
import be.boomkwekerij.plant.model.dto.PlantDTO;
import be.boomkwekerij.plant.util.CrudsResult;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.util.Arrays;

public class PlantCreateService {

    private PlantController plantController = new PlantController();

    private TextField name;
    private TextField age;
    private TextField measure;
    private TextField price;
    private Button plantCreateButton;

    public final Service createPlantService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Aanmaken plant");

                    PlantDTO plantDTO = new PlantDTO();
                    plantDTO.setName(name.getText());
                    plantDTO.setAge(age.getText());
                    plantDTO.setMeasure(measure.getText());
                    plantDTO.setPrice(Double.parseDouble(price.getText()));
                    CrudsResult createResult = plantController.createPlant(plantDTO);
                    if (createResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(createResult.getMessages().toArray()));
                    }

                    return null;
                }
            };
        }
    };

    public void setName(TextField name) {
        this.name = name;
    }

    public void setAge(TextField age) {
        this.age = age;
    }

    public void setMeasure(TextField measure) {
        this.measure = measure;
    }

    public void setPrice(TextField price) {
        this.price = price;
    }

    public void setPlantCreateButton(Button plantCreateButton) {
        this.plantCreateButton = plantCreateButton;
    }

    public void init(Pane root) {
        root.cursorProperty()
                .bind(Bindings.when(createPlantService.runningProperty())
                        .then(Cursor.WAIT)
                        .otherwise(Cursor.DEFAULT)
                );
        plantCreateButton.disableProperty()
                .bind(createPlantService.runningProperty());

        createPlantService.setOnSucceeded(serviceEvent -> {
            initializeTextFields();

            ServiceHandler.success(createPlantService);
        });
        createPlantService.setOnFailed(serviceEvent -> {
            ServiceHandler.error(createPlantService);
        });

        initializeTextFields();
    }

    private void initializeTextFields() {
        name.setText("");
        age.setText("");
        measure.setText("");
        price.setText("0.0");
    }
}
