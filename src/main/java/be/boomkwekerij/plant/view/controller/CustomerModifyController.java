package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.view.model.CustomerViewModel;
import be.boomkwekerij.plant.view.services.CustomerModifyService;
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

public class CustomerModifyController implements PageController {

    private CustomerModifyService customerModifyService = new CustomerModifyService();

    @FXML
    private TextField customerSearchField;
    @FXML
    private TableView<CustomerViewModel> customerList;
    @FXML
    private Button showModifyCustomerButton;
    @FXML
    private GridPane modifyCustomerPane;
    @FXML
    private TextField name1;
    @FXML
    private TextField name2;
    @FXML
    private TextField address1;
    @FXML
    private TextField address2;
    @FXML
    private TextField postalCode;
    @FXML
    private TextField residence;
    @FXML
    private TextField country;
    @FXML
    private TextField telephone;
    @FXML
    private TextField gsm;
    @FXML
    private TextField fax;
    @FXML
    private TextField btwNumber;
    @FXML
    private TextField emailAddress;
    @FXML
    private Button customerModifyButton;

    @Override
    public void init(Pane root) {
        customerModifyService.setCustomerSearchField(customerSearchField);
        customerModifyService.setCustomerList(customerList);
        customerModifyService.setShowModifyCustomerButton(showModifyCustomerButton);
        customerModifyService.setModifyCustomerPane(modifyCustomerPane);
        customerModifyService.setName1(name1);
        customerModifyService.setName2(name2);
        customerModifyService.setAddress1(address1);
        customerModifyService.setAddress2(address2);
        customerModifyService.setPostalCode(postalCode);
        customerModifyService.setResidence(residence);
        customerModifyService.setCountry(country);
        customerModifyService.setTelephone(telephone);
        customerModifyService.setGsm(gsm);
        customerModifyService.setFax(fax);
        customerModifyService.setBtwNumber(btwNumber);
        customerModifyService.setEmailAddress(emailAddress);
        customerModifyService.setCustomerModifyButton(customerModifyButton);
        customerModifyService.init(root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customerModifyService.loadAllCustomersService.restart();
        addChangeListenerToCustomerSearchField();
        addChangeListenerToCustomerList();
    }

    private void addChangeListenerToCustomerSearchField() {
        customerSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() >= 2) {
                customerModifyService.loadAllCustomersWithNameService.restart();
            } else {
                customerModifyService.loadAllCustomersService.restart();
            }
        });
    }

    private void addChangeListenerToCustomerList() {
        customerList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showModifyCustomerButton.setVisible(newValue != null);
        });
    }

    public void showModifyCustomer(ActionEvent actionEvent) {
        customerModifyService.initCustomerModifyService.restart();
    }

    public void modifyCustomer(Event event) {
        customerModifyService.modifyCustomerService.restart();
    }
}
