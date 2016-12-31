package be.boomkwekerij.plant.view.services;

import be.boomkwekerij.plant.controller.CustomerController;
import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.util.CrudsResult;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.util.Arrays;

public class CustomerCreateService {

    private CustomerController customerController = new CustomerController();

    private TextField name1;
    private TextField name2;
    private TextField address1;
    private TextField address2;
    private TextField postalCode;
    private TextField residence;
    private TextField country;
    private TextField telephone;
    private TextField gsm;
    private TextField fax;
    private TextField btwNumber;
    private TextField emailAddress;
    private Button customerCreateButton;

    public final Service createCustomerService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Aanmaken klant");

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
                    CrudsResult createResult = customerController.createCustomer(customer);
                    if (createResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(createResult.getMessages().toArray()));
                    }

                    return null;
                }
            };
        }
    };

    public void setName1(TextField name1) {
        this.name1 = name1;
    }

    public void setName2(TextField name2) {
        this.name2 = name2;
    }

    public void setAddress1(TextField address1) {
        this.address1 = address1;
    }

    public void setAddress2(TextField address2) {
        this.address2 = address2;
    }

    public void setPostalCode(TextField postalCode) {
        this.postalCode = postalCode;
    }

    public void setResidence(TextField residence) {
        this.residence = residence;
    }

    public void setCountry(TextField country) {
        this.country = country;
    }

    public void setTelephone(TextField telephone) {
        this.telephone = telephone;
    }

    public void setGsm(TextField gsm) {
        this.gsm = gsm;
    }

    public void setFax(TextField fax) {
        this.fax = fax;
    }

    public void setBtwNumber(TextField btwNumber) {
        this.btwNumber = btwNumber;
    }

    public void setEmailAddress(TextField emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setCustomerCreateButton(Button customerCreateButton) {
        this.customerCreateButton = customerCreateButton;
    }

    public void init(Pane root) {
        root.cursorProperty()
                .bind(Bindings.when(createCustomerService.runningProperty())
                        .then(Cursor.WAIT)
                        .otherwise(Cursor.DEFAULT)
                );
        customerCreateButton.disableProperty()
                .bind(createCustomerService.runningProperty());

        createCustomerService.setOnSucceeded(event1 -> {
            initializeTextFields();

            ServiceHandler.success(createCustomerService);
        });
        createCustomerService.setOnFailed(event1 -> ServiceHandler.error(createCustomerService));

        initializeTextFields();
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
}
