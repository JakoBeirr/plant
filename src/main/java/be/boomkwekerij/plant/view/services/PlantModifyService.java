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
    private Button showModifyPlantButton;
    private GridPane modifyPlantPane;
    private TextField name;
    private TextField age;
    private TextField measure;
    private TextField price;
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
                    name.setText(plant.getName());
                    age.setText(plant.getAge());
                    measure.setText(plant.getMeasure());
                    price.setText(Double.toString(plant.getPrice()));

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
                    plant.setName(name.getText());
                    plant.setAge(age.getText());
                    plant.setMeasure(measure.getText());
                    plant.setPrice(Double.parseDouble(price.getText()));
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

    public void setShowModifyPlantButton(Button showModifyPlantButton) {
        this.showModifyPlantButton = showModifyPlantButton;
    }

    public void setModifyPlantPane(GridPane modifyPlantPane) {
        this.modifyPlantPane = modifyPlantPane;
    }

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
            showModifyPlantButton.setDisable(true);
            plantList.setDisable(true);
            plantSearchField.setDisable(true);
            modifyPlantPane.setVisible(true);
        });
        modifyPlantService.setOnSucceeded(serviceEvent -> {
            showModifyPlantButton.setDisable(false);
            modifyPlantPane.setVisible(false);
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
        name.setText("");
        age.setText("");
        measure.setText("");
        price.setText("0.0");
    }
}
