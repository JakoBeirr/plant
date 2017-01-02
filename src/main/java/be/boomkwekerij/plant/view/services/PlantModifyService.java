package be.boomkwekerij.plant.view.services;

import be.boomkwekerij.plant.controller.PlantController;
import be.boomkwekerij.plant.model.dto.PlantDTO;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;
import be.boomkwekerij.plant.view.mapper.PlantViewMapper;
import be.boomkwekerij.plant.view.model.PlantViewModel;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlantModifyService {

    private PlantController plantController = new PlantController();

    private PlantViewMapper plantViewMapper = new PlantViewMapper();

    private TextField plantSearchField;
    private TableView<PlantViewModel> plantList;
    private Button showModifyButton;
    private GridPane modifyPane;
    private TextField nameField;
    private TextField ageField;
    private TextField measureField;
    private TextField priceField;
    private Button plantModifyButton;

    public final Service loadAllPlantsService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Inladen alle planten");

                    SearchResult<PlantDTO> searchResult = plantController.getAllPlants();
                    if (searchResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(searchResult.getMessages().toArray()));
                    }

                    List<PlantViewModel> plants = new ArrayList<>();
                    for (PlantDTO plantDTO : searchResult.getResults()) {
                        PlantViewModel plantViewModel = plantViewMapper.mapDTOToViewModel(plantDTO);
                        plants.add(plantViewModel);
                    }
                    plantList.getItems().setAll(plants);

                    return null;
                }
            };
        }
    };

    public final Service loadAllPlantsWithNameService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Inladen alle planten met naam");

                    String name = plantSearchField.getText();
                    SearchResult<PlantDTO> searchResult = plantController.getAllPlantsWithName(name);
                    if (searchResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(searchResult.getMessages().toArray()));
                    }

                    List<PlantViewModel> plants = new ArrayList<>();
                    for (PlantDTO plantDTO : searchResult.getResults()) {
                        PlantViewModel plantViewModel = plantViewMapper.mapDTOToViewModel(plantDTO);
                        plants.add(plantViewModel);
                    }
                    plantList.getItems().setAll(plants);

                    return null;
                }
            };
        }
    };

    public final Service initPlantModifyService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Initialiseren gegevens voor bewerking");

                    PlantViewModel selectedPlant = plantList.getSelectionModel().getSelectedItem();
                    SearchResult<PlantDTO> searchResult = plantController.getPlant(selectedPlant.getId());
                    if (searchResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(searchResult.getMessages().toArray()));
                    }

                    PlantDTO plant = searchResult.getFirst();
                    nameField.setText(plant.getName());
                    ageField.setText(plant.getAge());
                    measureField.setText(plant.getMeasure());
                    priceField.setText(Double.toString(plant.getPrice()));

                    return null;
                }
            };
        }
    };

    public final Service modifyPlantService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Bewerken plant");

                    PlantViewModel selectedPlant = plantList.getSelectionModel().getSelectedItem();

                    PlantDTO plant = new PlantDTO();
                    plant.setId(selectedPlant.getId());
                    plant.setName(nameField.getText());
                    plant.setAge(ageField.getText());
                    plant.setMeasure(measureField.getText());
                    plant.setPrice(Double.parseDouble(priceField.getText()));
                    CrudsResult modifyResult = plantController.updatePlant(plant);
                    if (modifyResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(modifyResult.getMessages().toArray()));
                    }

                    return null;
                }
            };
        }
    };

    public void setPlantSearchField(TextField plantSearchField) {
        this.plantSearchField = plantSearchField;
    }

    public void setPlantList(TableView<PlantViewModel> plantList) {
        this.plantList = plantList;
    }

    public void setShowModifyButton(Button showModifyButton) {
        this.showModifyButton = showModifyButton;
    }

    public void setModifyPane(GridPane modifyPane) {
        this.modifyPane = modifyPane;
    }

    public void setNameField(TextField nameField) {
        this.nameField = nameField;
    }

    public void setAgeField(TextField ageField) {
        this.ageField = ageField;
    }

    public void setMeasureField(TextField measureField) {
        this.measureField = measureField;
    }

    public void setPriceField(TextField priceField) {
        this.priceField = priceField;
    }

    public void setPlantModifyButton(Button plantModifyButton) {
        this.plantModifyButton = plantModifyButton;
    }

    public void init(Pane root) {
        root.cursorProperty()
                .bind(Bindings.when(loadAllPlantsService.runningProperty()
                            .or(loadAllPlantsWithNameService.runningProperty()
                            .or(initPlantModifyService.runningProperty()
                            .or(modifyPlantService.runningProperty()))))
                        .then(Cursor.WAIT)
                        .otherwise(Cursor.DEFAULT)
                );
        plantModifyButton.disableProperty()
                .bind(modifyPlantService.runningProperty());

        initPlantModifyService.setOnSucceeded(serviceEvent -> {
            showModifyButton.setDisable(true);
            plantList.setDisable(true);
            plantSearchField.setDisable(true);
            modifyPane.setVisible(true);
        });
        modifyPlantService.setOnSucceeded(serviceEvent -> {
            showModifyButton.setDisable(false);
            modifyPane.setVisible(false);
            plantList.setDisable(false);
            plantSearchField.setDisable(false);
            plantList.getSelectionModel().clearSelection();
            initializeTextFields();

            loadAllPlantsWithNameService.restart();

            ServiceHandler.success(modifyPlantService);
        });
        modifyPlantService.setOnFailed(serviceEvent -> {
            ServiceHandler.error(modifyPlantService);
        });
    }

    private void initializeTextFields() {
        nameField.setText("");
        ageField.setText("");
        measureField.setText("");
        priceField.setText("0.0");
    }
}
