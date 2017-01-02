package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.view.model.PlantViewModel;
import be.boomkwekerij.plant.view.services.PlantModifyService;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    private Button showModifyPlantButton;
    @FXML
    private GridPane modifyPlantPane;
    @FXML
    private TextField name;
    @FXML
    private TextField age;
    @FXML
    private TextField measure;
    @FXML
    private TextField price;
    @FXML
    private Button plantModifyButton;

    private static final String NON_DECIMAL_NUMERIC_CHARACTERS = "[^\\d.]";

    @Override
    public void init(Pane root) {
        plantModifyService.setPlantSearchField(plantSearchField);
        plantModifyService.setPlantList(plantList);
        plantModifyService.setShowModifyPlantButton(showModifyPlantButton);
        plantModifyService.setModifyPlantPane(modifyPlantPane);
        plantModifyService.setName(name);
        plantModifyService.setAge(age);
        plantModifyService.setMeasure(measure);
        plantModifyService.setPrice(price);
        plantModifyService.setPlantModifyButton(plantModifyButton);
        plantModifyService.init(root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addChangeListenerToSearchField();
        addChangeListenerToPlantList();
        initNumericField();
    }

    private void addChangeListenerToSearchField() {
        plantSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() >= 2) {
                plantModifyService.loadAllPlantsWithNameService.restart();
            } else {
                plantModifyService.loadAllPlantsService.restart();
            }
        });
    }

    private void addChangeListenerToPlantList() {
        plantList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showModifyPlantButton.setVisible(newValue != null);
        });
    }

    private void initNumericField() {
        price.textProperty().addListener((observable, oldValue, newValue) -> {
            price.setText(newValue.replaceAll(NON_DECIMAL_NUMERIC_CHARACTERS, ""));
        });
    }

    public void showModifyPlant(ActionEvent actionEvent) {
        plantModifyService.initPlantModifyService.restart();
    }

    public void modifyPlant(Event event) {
        plantModifyService.modifyPlantService.restart();
    }
}
