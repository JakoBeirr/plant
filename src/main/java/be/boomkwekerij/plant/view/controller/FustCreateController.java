package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.view.model.CustomerViewModel;
import be.boomkwekerij.plant.view.services.FustCreateService;
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

public class FustCreateController implements PageController {

    private FustCreateService fustCreateService = new FustCreateService();

    @FXML
    private TextField customerSearchField;
    @FXML
    private TableView<CustomerViewModel> customerList;
    @FXML
    private Button showCreateFustButton;
    @FXML
    private GridPane createFustPane;
    @FXML
    private TextField customer;
    @FXML
    private Button fustCreateButton;

    //private static final String NON_NUMERIC_CHARACTERS = "[^\\d]";

    @Override
    public void init(Pane root) {
        fustCreateService.setCustomerSearchField(customerSearchField);
        fustCreateService.setCustomerList(customerList);
        fustCreateService.setShowCreateFustButton(showCreateFustButton);
        fustCreateService.setCreateFustPane(createFustPane);
        fustCreateService.setCustomer(customer);
        fustCreateService.setFustCreateButton(fustCreateButton);
        fustCreateService.init(root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addChangeListenerToSearchFields();
        addChangeListenersToList();
        initNumericFields();
    }

    private void addChangeListenerToSearchFields() {
        customerSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() >= 2) {
                fustCreateService.loadAllCustomersWithNameService.restart();
            } else {
                fustCreateService.loadAllCustomersService.restart();
            }
        });
    }

    private void addChangeListenersToList() {
        customerList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showCreateFustButton.setVisible(newValue != null);
        });
    }

    private void initNumericFields() {

    }

    public void showCreateFust(ActionEvent actionEvent) {
        fustCreateService.initFustCreateService.restart();
    }

    public void createFust(Event event) {
        fustCreateService.createFustService.restart();
    }
}
