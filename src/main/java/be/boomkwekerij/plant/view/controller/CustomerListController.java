package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.view.model.CustomerViewModel;
import be.boomkwekerij.plant.view.services.CustomerListService;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerListController implements PageController {

    private CustomerListService customerListService = new CustomerListService();

    @FXML
    private TextField customerSearchField;
    @FXML
    private TableView<CustomerViewModel> customerList;
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
        addChangeListenerToSearchField();
        addChangeListenerToCustomerList();
    }

    private void addChangeListenerToSearchField() {
        customerSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() >= 2) {
                customerListService.loadAllCustomersWithNameService.restart();
            } else {
                customerListService.loadAllCustomersService.restart();
            }
        });
    }

    private void addChangeListenerToCustomerList() {
        customerList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            customerDetailsButton.setVisible(newValue != null);
            customerDeleteButton.setVisible(newValue != null);
        });
    }

    public void showCustomer(Event event) {
        customerListService.showCustomerDetailsService.restart();
    }

    public void deleteCustomer(Event event) {
        if (AlertController.areYouSure("Bent u zeker dat u deze klant wil verwijderen?", "Bedenk dat dit enkel mogelijk is indien er geen facturen gelinkt zijn aan deze klant!")) {
            customerListService.deleteCustomerService.restart();
        }
    }
}
