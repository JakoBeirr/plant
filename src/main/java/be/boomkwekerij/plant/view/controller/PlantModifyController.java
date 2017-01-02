package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.view.model.PlantViewModel;
import be.boomkwekerij.plant.view.services.PlantModifyService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class PlantModifyController implements PageController {

    private PlantModifyService plantModifyService = new PlantModifyService();

    @FXML
    private TextField plantSearchField;
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
    @FXML
    private Button plantModifyButton;

    private static final String NON_DECIMAL_NUMERIC_CHARACTERS = "[^\\d.]";

    @Override
    public void init(Pane root) {
        plantModifyService.setPlantSearchField(plantSearchField);
        plantModifyService.setPlantList(plantList);
        plantModifyService.setShowModifyButton(showModifyButton);
        plantModifyService.setModifyPane(modifyPane);
        plantModifyService.setNameField(nameField);
        plantModifyService.setAgeField(ageField);
        plantModifyService.setMeasureField(measureField);
        plantModifyService.setPriceField(priceField);
        plantModifyService.setPlantModifyButton(plantModifyButton);
        plantModifyService.init(root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        plantModifyService.loadAllPlantsService.restart();
        addChangeListenerToSearchField();
        addChangeListenerToPlantList();
        initNumericField();
    }

    private void addChangeListenerToSearchField() {
        plantSearchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length() >= 2) {
                    plantModifyService.loadAllPlantsWithNameService.restart();
                } else {
                    plantModifyService.loadAllPlantsService.restart();
                }
            }
        });
    }

    private void addChangeListenerToPlantList() {
        plantList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PlantViewModel>() {
            @Override
            public void changed(ObservableValue<? extends PlantViewModel> observable, PlantViewModel oldValue, PlantViewModel newValue) {
                showModifyButton.setVisible(newValue != null);
            }
        });
    }

    private void initNumericField() {
        priceField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                priceField.setText(newValue.replaceAll(NON_DECIMAL_NUMERIC_CHARACTERS, ""));
            }
        });
    }

    public void showModify(ActionEvent actionEvent) {
        plantModifyService.initPlantModifyService.restart();
    }

    public void modifyPlant(Event event) {
        plantModifyService.modifyPlantService.restart();
    }
}
