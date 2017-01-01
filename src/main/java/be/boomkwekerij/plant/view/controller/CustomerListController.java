package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.controller.CustomerController;
import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;
import be.boomkwekerij.plant.view.mapper.CustomerViewMapper;
import be.boomkwekerij.plant.view.model.CustomerViewModel;
import be.boomkwekerij.plant.view.services.CustomerListService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerListController implements PageController {

    private CustomerListService customerListService = new CustomerListService();

    @FXML
    private TextField customerSearchField;
    @FXML
    private TableView<CustomerViewModel> customerList;
    @FXML
    private TableColumn<CustomerViewModel, String> name1;
    @FXML
    private TableColumn<CustomerViewModel, String> name2;
    @FXML
    private TableColumn<CustomerViewModel, String> address1;
    @FXML
    private TableColumn<CustomerViewModel, String> postalCode;
    @FXML
    private TableColumn<CustomerViewModel, String> residence;
    @FXML
    private TableColumn<CustomerViewModel, String> country;
    @FXML
    private TableColumn<CustomerViewModel, String> telephone;
    @FXML
    private TableColumn<CustomerViewModel, String> btwNumber;
    @FXML
    private Button customerDetailsButton;
    @FXML
    private Button customerDeleteButton;

    @Override
    public void init(Pane root) {
        customerListService.setCustomerSearchField(customerSearchField);
        customerListService.setCustomerList(customerList);
        customerListService.setCustomerDetailsButton(customerDetailsButton);
        customerListService.setCustomerDeleteButton(customerDeleteButton);
        customerListService.init(root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customerListService.loadAllCustomersService.restart();
        addChangeListenerToSearchField();
        addChangeListenerToCustomerList();
    }

    private void addChangeListenerToSearchField() {
        customerSearchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length() >= 2) {
                    customerListService.loadAllCustomersWithNameService.restart();
                } else {
                    customerListService.loadAllCustomersService.restart();
                }
            }
        });
    }

    private void addChangeListenerToCustomerList() {
        customerList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerViewModel>() {
            @Override
            public void changed(ObservableValue<? extends CustomerViewModel> observable, CustomerViewModel oldValue, CustomerViewModel newValue) {
                customerDetailsButton.setVisible(newValue != null);
                customerDeleteButton.setVisible(newValue != null);
            }
        });
    }

    public void deleteCustomer(Event event) {
        if (AlertController.areYouSure("Bent u zeker dat u deze klant wil verwijderen?", "Bedenk dat dit enkel mogelijk is indien er geen facturen gelinkt zijn aan deze klant!")) {
            customerListService.deleteCustomerService.restart();
        }
    }

    public void showCustomer(Event event) {
        customerListService.showCustomerDetailsService.restart();
    }
}
