package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.controller.PlantController;
import be.boomkwekerij.plant.model.dto.PlantDTO;
import be.boomkwekerij.plant.util.SearchResult;
import be.boomkwekerij.plant.view.mapper.PlantViewMapper;
import be.boomkwekerij.plant.view.model.PlantViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PlantListController implements Initializable {

    private PlantController plantController = new PlantController();

    private PlantViewMapper plantViewMapper = new PlantViewMapper();

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<PlantViewModel> plants = new ArrayList<>();

        SearchResult<PlantDTO> plantSearchResult = plantController.getAllPlants();
        if (plantSearchResult.isSuccess()) {
            for (PlantDTO plantDTO : plantSearchResult.getResults()) {
                PlantViewModel plantViewModel = plantViewMapper.mapDTOToViewModel(plantDTO);
                plants.add(plantViewModel);
            }
        }

        plantList.getItems().setAll(plants);
    }
}
