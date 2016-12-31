package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.view.services.CustomerCreateService;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerCreateController implements PageController {

    private CustomerCreateService customerCreateService = new CustomerCreateService();

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
    private Button customerCreateButton;

    @Override
    public void init(Pane root) {
        customerCreateService.setName1(name1);
        customerCreateService.setName2(name2);
        customerCreateService.setAddress1(address1);
        customerCreateService.setAddress2(address2);
        customerCreateService.setPostalCode(postalCode);
        customerCreateService.setResidence(residence);
        customerCreateService.setCountry(country);
        customerCreateService.setTelephone(telephone);
        customerCreateService.setGsm(gsm);
        customerCreateService.setFax(fax);
        customerCreateService.setBtwNumber(btwNumber);
        customerCreateService.setEmailAddress(emailAddress);
        customerCreateService.setCustomerCreateButton(customerCreateButton);
        customerCreateService.init(root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    public void createCustomer(Event event) {
        customerCreateService.createCustomerService.restart();
    }
}
