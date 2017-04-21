package be.boomkwekerij.plant.view.services;

import be.boomkwekerij.plant.controller.PlantController;
import be.boomkwekerij.plant.model.dto.PlantDTO;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;
import be.boomkwekerij.plant.view.mapper.PlantViewMapper;
import be.boomkwekerij.plant.view.model.PlantViewModel;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlantListService {

    private PlantController plantController = new PlantController();

    private PlantViewMapper plantViewMapper = new PlantViewMapper();

    private TextField plantSearchField;
    private TableView<PlantViewModel> plantList;
    private Button plantDetailsButton;
    private Button plantDeleteButton;

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

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            plantList.getItems().setAll(plants);
                        }
                    });

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

                    List<PlantViewModel> plants = new ArrayList<>();

                    String name = plantSearchField.getText();
                    if (name.matches(".*(.*-.*)")) {
                        String plantName = StringUtils.substringBefore(name, "(").trim();
                        String plantAge = StringUtils.substringBetween(name, "(", "-").trim();
                        String plantMeasure = StringUtils.substringBetween(name, "-", ")").trim();

                        SearchResult<PlantDTO> searchResult = plantController.getAllPlantsWithNameAndAgeAndMeasure(plantName, plantAge, plantMeasure);
                        if (searchResult.isError()) {
                            throw new IllegalArgumentException(Arrays.toString(searchResult.getMessages().toArray()));
                        }

                        for (PlantDTO plantDTO : searchResult.getResults()) {
                            PlantViewModel plantViewModel = plantViewMapper.mapDTOToViewModel(plantDTO);
                            plants.add(plantViewModel);
                        }
                    } else {
                        SearchResult<PlantDTO> searchResult = plantController.getAllPlantsWithName(name);
                        if (searchResult.isError()) {
                            throw new IllegalArgumentException(Arrays.toString(searchResult.getMessages().toArray()));
                        }

                        for (PlantDTO plantDTO : searchResult.getResults()) {
                            PlantViewModel plantViewModel = plantViewMapper.mapDTOToViewModel(plantDTO);
                            plants.add(plantViewModel);
                        }
                    }

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            plantList.getItems().setAll(plants);
                        }
                    });

                    return null;
                }
            };
        }
    };

    public final Service showPlantDetailsService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Tonen details plant");

                    PlantViewModel selectedPlant = plantList.getSelectionModel().getSelectedItem();
                    SearchResult<PlantDTO> searchResult = plantController.getPlant(selectedPlant.getId());
                    if (searchResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(searchResult.getMessages().toArray()));
                    }
                    PlantDTO plant = searchResult.getFirst();

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            show(plant);
                        }
                    });

                    return null;
                }
            };
        }
    };

    public final Service deletePlantService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Verwijderen plant");

                    PlantViewModel selectedPlant = plantList.getSelectionModel().getSelectedItem();
                    CrudsResult deleteResult = plantController.deletePlant(selectedPlant.getId());
                    if (deleteResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(deleteResult.getMessages().toArray()));
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

    public void setPlantDetailsButton(Button plantDetailsButton) {
        this.plantDetailsButton = plantDetailsButton;
    }

    public void setPlantDeleteButton(Button plantDeleteButton) {
        this.plantDeleteButton = plantDeleteButton;
    }

    public void init(Pane root) {
        root.cursorProperty()
                .bind(Bindings.when(loadAllPlantsService.runningProperty()
                            .or(loadAllPlantsWithNameService.runningProperty()
                            .or(showPlantDetailsService.runningProperty()
                            .or(deletePlantService.runningProperty()))))
                        .then(Cursor.WAIT)
                        .otherwise(Cursor.DEFAULT)
                );
        plantDetailsButton.disableProperty()
                .bind(showPlantDetailsService.runningProperty());
        plantDeleteButton.disableProperty()
                .bind(deletePlantService.runningProperty());

        showPlantDetailsService.setOnFailed(serviceEvent -> {
            ServiceHandler.error(showPlantDetailsService);
        });
        deletePlantService.setOnSucceeded(serviceEvent -> {
            if (plantSearchField.getText().length() > 2) {
                loadAllPlantsWithNameService.restart();
            } else {
                loadAllPlantsService.restart();
            }

            ServiceHandler.success(deletePlantService);
        });
        deletePlantService.setOnFailed(serviceEvent -> {
            ServiceHandler.error(deletePlantService);
        });

        loadAllPlantsService.restart();
    }

    private void show(PlantDTO plant) {
        Dialog detailsDialog = new Dialog();
        detailsDialog.setTitle("Plant: " + plant.getName());

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(5);
        grid.setPadding(new Insets(20, 150, 10, 10));
        Label name1Label = new Label("Naam:");
        name1Label.setStyle("-fx-font-weight: bold;");
        grid.add(name1Label, 0, 0);
        grid.add(new Label(plant.getName()), 1, 0);
        Label name2Label = new Label("Leeftijd:");
        name2Label.setStyle("-fx-font-weight: bold;");
        grid.add(name2Label, 0, 1);
        grid.add(new Label(plant.getAge()), 1, 1);
        Label address1Label = new Label("Maat:");
        address1Label.setStyle("-fx-font-weight: bold;");
        grid.add(address1Label, 0, 2);
        grid.add(new Label(plant.getMeasure()), 1, 2);
        Label address2Label = new Label("Prijs:");
        address2Label.setStyle("-fx-font-weight: bold;");
        grid.add(address2Label, 0, 3);
        grid.add(new Label(Double.toString(plant.getPrice())), 1, 3);
        detailsDialog.getDialogPane().setContent(grid);

        detailsDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);

        detailsDialog.showAndWait();
    }
}
