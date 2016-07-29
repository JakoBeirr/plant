package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.controller.PlantController;
import be.boomkwekerij.plant.model.dto.PlantDTO;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;
import be.boomkwekerij.plant.view.mapper.PlantViewMapper;
import be.boomkwekerij.plant.view.model.PlantViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PlantModifyController implements Initializable {

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
    private Button showModifyButton;
    @FXML
    private GridPane modifyPane;
    @FXML
    private TextField nameField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField measureField;
    @FXML
    private TextField priceField;

    private static final String NON_DECIMAL_NUMERIC_CHARACTERS = "[^\\d.]";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initNumericField();
        loadAllPlants();
        addChangeListenerToSearchField();
        addChangeListenerToPlantList();
    }

    private void initNumericField() {
        priceField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                priceField.setText(newValue.replaceAll(NON_DECIMAL_NUMERIC_CHARACTERS, ""));
            }
        });
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
                showModifyButton.setVisible(newValue != null);
            }
        });
    }

    public void showModify(ActionEvent actionEvent) {
        plantList.setDisable(true);
        searchField.setDisable(true);
        PlantViewModel selectedPlant = plantList.getSelectionModel().getSelectedItem();
        SearchResult<PlantDTO> plantResult = plantController.getPlant(selectedPlant.getId());
        if (plantResult.isSuccess()) {
            PlantDTO plant = plantResult.getFirst();
            nameField.setText(plant.getName());
            ageField.setText(plant.getAge());
            measureField.setText(plant.getMeasure());
            priceField.setText(Double.toString(plant.getPrice()));

            modifyPane.setVisible(true);
        }
    }

    public void modifyPlant(Event event) {
        try {
            PlantViewModel selectedPlant = plantList.getSelectionModel().getSelectedItem();
            CrudsResult plantModifyResult = modifyPlant(selectedPlant.getId());

            if (plantModifyResult.isSuccess()) {
                handleModifySuccess();
            } else {
                handleModifyError(plantModifyResult.getMessages());
            }
        } catch (Exception e) {
            handleModifyException(e);
        }
    }

    private CrudsResult modifyPlant(String id) {
        PlantDTO plant = new PlantDTO();
        plant.setId(id);
        plant.setName(nameField.getText());
        plant.setAge(ageField.getText());
        plant.setMeasure(measureField.getText());
        plant.setPrice(Double.parseDouble(priceField.getText()));
        return plantController.updatePlant(plant);
    }

    private void handleModifySuccess() {
        initializeTextFields();
        modifyPane.setVisible(false);
        plantList.getSelectionModel().clearSelection();
        loadAllPlantsWithName();
        plantList.setDisable(false);
        searchField.setDisable(false);
        AlertController.alertSuccess("Plant bewerkt!");
    }

    private void loadAllPlantsWithName() {
        ArrayList<PlantViewModel> plants = new ArrayList<>();

        SearchResult<PlantDTO> plantSearchResult = plantController.getAllPlantsWithName(searchField.getText());
        if (plantSearchResult.isSuccess()) {
            for (PlantDTO plantDTO : plantSearchResult.getResults()) {
                PlantViewModel plantViewModel = plantViewMapper.mapDTOToViewModel(plantDTO);
                plants.add(plantViewModel);
            }
        }

        plantList.getItems().setAll(plants);
    }

    private void initializeTextFields() {
        nameField.setText("");
        ageField.setText("");
        measureField.setText("");
        priceField.setText("0.0");
    }

    private void handleModifyError(List<String> errorMessages) {
        StringBuilder errorBuilder = new StringBuilder("Gefaald wegens volgende fout(en): ");
        for (int i = 0; i < errorMessages.size(); i++) {
            String errorMessage = errorMessages.get(i);
            errorBuilder.append("\n").append(i+1).append(") ").append(errorMessage);
        }
        AlertController.alertError("Plant bewerken gefaald!", errorBuilder.toString());
    }

    private void handleModifyException(Exception e) {
        AlertController.alertException("Plant bewerken gefaald!", e);
    }
}
