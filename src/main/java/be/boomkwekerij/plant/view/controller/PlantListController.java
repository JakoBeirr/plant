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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    @FXML
    private Button deleteButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initData();

        addChangeListenerToPlantList();
    }

    private void initData() {
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

    private void addChangeListenerToPlantList() {
        plantList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PlantViewModel>() {
            @Override
            public void changed(ObservableValue<? extends PlantViewModel> observable, PlantViewModel oldValue, PlantViewModel newValue) {
                deleteButton.setVisible(newValue != null);
            }
        });
    }

    public void deletePlant(Event event) {
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

    private void handleDeleteSuccess() {
        initData();
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
}
