package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.view.model.PlantViewModel;
import be.boomkwekerij.plant.view.services.PlantListService;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class PlantListController implements PageController {

    private PlantListService plantListService = new PlantListService();

    @FXML
    private TextField plantSearchField;
    @FXML
    private TableView<PlantViewModel> plantList;
    @FXML
    private Button plantDetailsButton;
    @FXML
    private Button plantDeleteButton;

    @Override
    public void init(Pane root) {
        plantListService.setPlantSearchField(plantSearchField);
        plantListService.setPlantList(plantList);
        plantListService.setPlantDetailsButton(plantDetailsButton);
        plantListService.setPlantDeleteButton(plantDeleteButton);
        plantListService.init(root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addChangeListenerToSearchField();
        addChangeListenerToPlantList();
    }

    private void addChangeListenerToSearchField() {
        plantSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() >= 2) {
                plantListService.loadAllPlantsWithNameService.restart();
            } else {
                plantListService.loadAllPlantsService.restart();
            }
        });
    }

    private void addChangeListenerToPlantList() {
        plantList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            plantDetailsButton.setVisible(newValue != null);
            plantDeleteButton.setVisible(newValue != null);
        });
    }

    public void showPlant(Event event) {
        plantListService.showPlantDetailsService.restart();
    }

    public void deletePlant(Event event) {
        if (AlertController.areYouSure("Bent u zeker dat u deze plant wil verwijderen?", "Bedenk dat deze plant misschien reeds aan een factuur gekoppeld is!")) {
            plantListService.deletePlantService.restart();
        }
    }
}
