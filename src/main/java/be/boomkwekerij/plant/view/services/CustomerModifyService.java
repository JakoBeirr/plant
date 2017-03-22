package be.boomkwekerij.plant.view.services;

import be.boomkwekerij.plant.controller.CustomerController;
import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;
import be.boomkwekerij.plant.view.mapper.CustomerViewMapper;
import be.boomkwekerij.plant.view.model.CustomerViewModel;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomerModifyService {

    private CustomerController customerController = new CustomerController();

    private CustomerViewMapper customerViewMapper = new CustomerViewMapper();

    private TextField customerSearchField;
    private TableView<CustomerViewModel> customerList;
    private Button showModifyCustomerButton;
    private GridPane modifyCustomerPane;
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
    private Button customerModifyButton;

    public final Service loadAllCustomersService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Inladen alle klanten");

                    SearchResult<CustomerDTO> searchResult = customerController.getAllCustomers();
                    if (searchResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(searchResult.getMessages().toArray()));
                    }

                    List<CustomerViewModel> customers = new ArrayList<>();
                    for (CustomerDTO customerDTO : searchResult.getResults()) {
                        CustomerViewModel customerViewModel = customerViewMapper.mapDTOToViewModel(customerDTO);
                        customers.add(customerViewModel);
                    }

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            customerList.getItems().setAll(customers);
                        }
                    });

                    return null;
                }
            };
        }
    };

    public final Service loadAllCustomersWithNameService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Inladen alle klanten met naam");

                    String name = customerSearchField.getText();
                    SearchResult<CustomerDTO> searchResult = customerController.getAllCustomersWithName(name);
                    if (searchResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(searchResult.getMessages().toArray()));
                    }

                    List<CustomerViewModel> customers = new ArrayList<>();
                    for (CustomerDTO customerDTO : searchResult.getResults()) {
                        CustomerViewModel customerViewModel = customerViewMapper.mapDTOToViewModel(customerDTO);
                        customers.add(customerViewModel);
                    }

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            customerList.getItems().setAll(customers);
                        }
                    });

                    return null;
                }
            };
        }
    };

    public final Service initCustomerModifyService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Initialiseren gegevens voor bewerking");

                    CustomerViewModel selectedCustomer = customerList.getSelectionModel().getSelectedItem();
                    SearchResult<CustomerDTO> searchResult = customerController.getCustomer(selectedCustomer.getId());
                    if (searchResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(searchResult.getMessages().toArray()));
                    }
                    CustomerDTO customer = searchResult.getFirst();

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            name1.setText(customer.getName1());
                            name2.setText(customer.getName2());
                            address1.setText(customer.getAddress1());
                            address2.setText(customer.getAddress2());
                            postalCode.setText(customer.getPostalCode());
                            residence.setText(customer.getResidence());
                            country.setText(customer.getCountry());
                            telephone.setText(customer.getTelephone());
                            gsm.setText(customer.getGsm());
                            fax.setText(customer.getFax());
                            btwNumber.setText(customer.getBtwNumber());
                            emailAddress.setText(customer.getEmailAddress());
                        }
                    });

                    return null;
                }
            };
        }
    };

    public final Service modifyCustomerService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Bewerken klant");

                    CustomerViewModel selectedCustomer = customerList.getSelectionModel().getSelectedItem();

                    CustomerDTO customer = new CustomerDTO();
                    customer.setId(selectedCustomer.getId());
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
                    CrudsResult modifyResult = customerController.updateCustomer(customer);
                    if (modifyResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(modifyResult.getMessages().toArray()));
                    }

                    return null;
                }
            };
        }
    };

    public void setCustomerSearchField(TextField customerSearchField) {
        this.customerSearchField = customerSearchField;
    }

    public void setCustomerList(TableView<CustomerViewModel> customerList) {
        this.customerList = customerList;
    }

    public void setShowModifyCustomerButton(Button showModifyCustomerButton) {
        this.showModifyCustomerButton = showModifyCustomerButton;
    }

    public void setModifyCustomerPane(GridPane modifyCustomerPane) {
        this.modifyCustomerPane = modifyCustomerPane;
    }

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

    public void setCustomerModifyButton(Button customerModifyButton) {
        this.customerModifyButton = customerModifyButton;
    }

    public void init(Pane root) {
        root.cursorProperty()
                .bind(Bindings.when(loadAllCustomersService.runningProperty()
                            .or(loadAllCustomersWithNameService.runningProperty()
                            .or(initCustomerModifyService.runningProperty()
                            .or(modifyCustomerService.runningProperty()))))
                        .then(Cursor.WAIT)
                        .otherwise(Cursor.DEFAULT)
                );
        customerModifyButton.disableProperty()
                .bind(modifyCustomerService.runningProperty());

        initCustomerModifyService.setOnSucceeded(serviceEvent -> {
            showModifyCustomerButton.setDisable(true);
            customerList.setDisable(true);
            customerSearchField.setDisable(true);
            modifyCustomerPane.setVisible(true);
        });
        modifyCustomerService.setOnSucceeded(serviceEvent -> {
            showModifyCustomerButton.setDisable(false);
            modifyCustomerPane.setVisible(false);
            customerList.setDisable(false);
            customerSearchField.setDisable(false);
            customerList.getSelectionModel().clearSelection();
            initializeTextFields();

            if (customerSearchField.getText().length() > 2) {
                loadAllCustomersWithNameService.restart();
            } else {
                loadAllCustomersService.restart();
            }

            ServiceHandler.success(modifyCustomerService);
        });
        modifyCustomerService.setOnFailed(serviceEvent -> {
            ServiceHandler.error(modifyCustomerService);
        });

        loadAllCustomersService.restart();
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
