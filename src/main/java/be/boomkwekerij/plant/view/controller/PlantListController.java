package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.controller.PlantController;
import be.boomkwekerij.plant.model.dto.PlantDTO;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;
import be.boomkwekerij.plant.view.mapper.PlantViewMapper;
import be.boomkwekerij.plant.view.model.PlantViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PlantListController implements Initializable {

    private PlantController plantController = new PlantController();

    private PlantViewMapper plantViewMapper = new PlantViewMapper();

    @FXML
    private TextField searchField;
    @FXML
    private TableView<PlantViewModel> plantList;
    @FXML
    private TableColumn<PlantViewModel, String> name;
    @FXML
    private TableColumn<PlantViewModel, String> age;
    @FXML
    private TableColumn<PlantViewModel, String> measure;
    @FXML
    private TableColumn<PlantViewModel, Double> price;
    @FXML
    private Button detailsButton;
    @FXML
    private Button deleteButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadAllPlants();
        addChangeListenerToSearchField();
        addChangeListenerToPlantList();
    }

    private void loadAllPlants() {
        List<PlantViewModel> allPlants = getAllPlants();
        plantList.getItems().setAll(allPlants);
    }

    private void addChangeListenerToSearchField() {
        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                List<PlantViewModel> allPlants;
                if (newValue.length() >= 2) {
                    allPlants = getAllPlantsWithName(newValue);
                } else {
                    allPlants = getAllPlants();
                }
                plantList.getItems().setAll(allPlants);
            }
        });
    }

    private List<PlantViewModel> getAllPlants() {
        List<PlantViewModel> plants = new ArrayList<>();

        SearchResult<PlantDTO> plantSearchResult = plantController.getAllPlants();
        if (plantSearchResult.isSuccess()) {
            for (PlantDTO plantDTO : plantSearchResult.getResults()) {
                PlantViewModel plantViewModel = plantViewMapper.mapDTOToViewModel(plantDTO);
                plants.add(plantViewModel);
            }
        }

        return plants;
    }

    private List<PlantViewModel> getAllPlantsWithName(String name) {
        ArrayList<PlantViewModel> plants = new ArrayList<>();

        SearchResult<PlantDTO> plantSearchResult = plantController.getAllPlantsWithName(name);
        if (plantSearchResult.isSuccess()) {
            for (PlantDTO plantDTO : plantSearchResult.getResults()) {
                PlantViewModel plantViewModel = plantViewMapper.mapDTOToViewModel(plantDTO);
                plants.add(plantViewModel);
            }
        }

        return plants;
    }

    private void addChangeListenerToPlantList() {
        plantList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PlantViewModel>() {
            @Override
            public void changed(ObservableValue<? extends PlantViewModel> observable, PlantViewModel oldValue, PlantViewModel newValue) {
                detailsButton.setVisible(newValue != null);
                deleteButton.setVisible(newValue != null);
            }
        });
    }

    public void deletePlant(Event event) {
        if (AlertController.areYouSure("Bent u zeker dat u deze plant wil verwijderen?", "Bedenk dat deze plant misschien reeds aan een factuur gekoppeld is!")) {
            try {
                PlantViewModel selectedPlant = plantList.getSelectionModel().getSelectedItem();
                CrudsResult deleteResult = plantController.deletePlant(selectedPlant.getId());

                if (deleteResult.isSuccess()) {
                    handleDeleteSuccess();
                } else {
                    handleDeleteError(deleteResult.getMessages());
                }
            } catch (Exception e) {
                handleDeleteException(e);
            }
        }
    }

    private void handleDeleteSuccess() {
        loadAllPlants();
        AlertController.alertSuccess("Plant verwijderd!");
    }

    private void handleDeleteError(List<String> errorMessages) {
        StringBuilder errorBuilder = new StringBuilder("Gefaald wegens volgende fout(en): ");
        for (int i = 0; i < errorMessages.size(); i++) {
            String errorMessage = errorMessages.get(i);
            errorBuilder.append(errorMessage);

            if (i != (errorMessages.size()-1)) {
                errorBuilder.append("; ");
            }
        }
        AlertController.alertError("Plant verwijderen gefaald!", errorBuilder.toString());
    }

    private void handleDeleteException(Exception e) {
        AlertController.alertException("Plant verwijderen gefaald!", e);
    }

    public void showPlant(Event event) {
        try {
            PlantViewModel selectedPlant = plantList.getSelectionModel().getSelectedItem();
            SearchResult<PlantDTO> plantSearchResult = plantController.getPlant(selectedPlant.getId());

            if (plantSearchResult.isSuccess()) {
                if (plantSearchResult.getResults().size() > 0) {
                    PlantDTO plant = plantSearchResult.getFirst();
                    showPlantDetails(plant);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showPlantDetails(PlantDTO plant) {
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
