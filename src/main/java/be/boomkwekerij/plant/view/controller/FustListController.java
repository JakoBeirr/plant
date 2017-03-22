package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.view.model.FustViewModel;
import be.boomkwekerij.plant.view.services.FustListService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class FustListController implements PageController {

    private FustListService fustListService = new FustListService();

    @FXML
    private TextField fustSearchField;
    @FXML
    private TableView<FustViewModel> fustList;
    @FXML
    private Button deleteFustButton;

    @Override
    public void init(Pane root) {
        fustListService.setFustSearchField(fustSearchField);
        fustListService.setFustList(fustList);
        fustListService.setDeleteFustButton(deleteFustButton);
        fustListService.init(root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addChangeListenerToField();
        addChangeListenersToList();
    }

    private void addChangeListenerToField() {
        fustSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 2) {
                fustListService.loadAllFustFromCustomerWithName.restart();
            } else {
                fustListService.loadAllFustsService.restart();
            }
        });
    }

    private void addChangeListenersToList() {
        fustList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            deleteFustButton.setVisible(newValue != null);
            deleteFustButton.setManaged(newValue != null);
        });
    }

    public void deleteFust(ActionEvent actionEvent) {
        if (AlertController.areYouSure("Bent u zeker dat u deze fust wil verwijderen?", "Bedenk dat u deze fust nadien niet meer zal kunnen herstellen!")) {
            fustListService.deleteFustService.restart();
        }
    }
}
