package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.controller.CustomerController;
import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.util.CrudsResult;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerCreateController implements Initializable {

    private CustomerController customerController = new CustomerController();

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTextFields();
    }

    public void createCustomer(Event event) {
        try {
            CrudsResult customerCreateResult = createCustomer();

            if (customerCreateResult.isSuccess()) {
                handleCreateSuccess();
            } else {
                handleCreateError(customerCreateResult.getMessages());
            }
        } catch (Exception e) {
            handleCreateException(e);
        }
    }

    private CrudsResult createCustomer() {
        CustomerDTO customer = new CustomerDTO();
        customer.setName1(name1.getText());
        customer.setName2(name2.getText());
        customer.setAddress1(address1.getText());
        customer.setAddress2(address2.getText());
        customer.setPostalCode(postalCode.getText());
        customer.setResidence(residence.getText());
        customer.setCountry(country.getText());
        customer.setTelephone(telephone.getText());
        customer.setGsm(gsm.getText());
        customer.setFax(fax.getText());
        customer.setBtwNumber(btwNumber.getText());
        customer.setEmailAddress(emailAddress.getText());
        return customerController.createCustomer(customer);
    }

    private void handleCreateSuccess() {
        initializeTextFields();
        AlertController.alertSuccess("Klant aangemaakt!");
    }

    private void initializeTextFields() {
        name1.setText("");
        name2.setText("");
        address1.setText("");
        address2.setText("");
        postalCode.setText("");
        residence.setText("");
        country.setText("BE");
        telephone.setText("");
        gsm.setText("");
        fax.setText("");
        btwNumber.setText("");
        emailAddress.setText("");
    }

    private void handleCreateError(List<String> errorMessages) {
        StringBuilder errorBuilder = new StringBuilder("Gefaald wegens volgende fout(en): ");
        for (int i = 0; i < errorMessages.size(); i++) {
            String errorMessage = errorMessages.get(i);
            errorBuilder.append(errorMessage);

            if (i != (errorMessages.size()-1)) {
                errorBuilder.append("; ");
            }
        }
        AlertController.alertError("Klant aanmaken gefaald!", errorBuilder.toString());
    }

    private void handleCreateException(Exception e) {
        AlertController.alertException("Klant aanmaken gefaald!", e);
    }
}
