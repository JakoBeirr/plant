package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.controller.CustomerController;
import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;
import be.boomkwekerij.plant.view.mapper.CustomerViewMapper;
import be.boomkwekerij.plant.view.model.CustomerViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerModifyController implements Initializable {

    private CustomerController customerController = new CustomerController();

    private CustomerViewMapper customerViewMapper = new CustomerViewMapper();

    @FXML
    private TextField searchField;
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
    private Button showModifyButton;
    @FXML
    private GridPane modifyPane;
    @FXML
    private TextField name1Field;
    @FXML
    private TextField name2Field;
    @FXML
    private TextField address1Field;
    @FXML
    private TextField address2Field;
    @FXML
    private TextField postalCodeField;
    @FXML
    private TextField residenceField;
    @FXML
    private TextField countryField;
    @FXML
    private TextField telephoneField;
    @FXML
    private TextField gsmField;
    @FXML
    private TextField faxField;
    @FXML
    private TextField btwNumberField;
    @FXML
    private TextField emailAddressField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadAllCustomers();
        addChangeListenerToSearchField();
        addChangeListenerToCustomerList();
    }

    private void loadAllCustomers() {
        List<CustomerViewModel> allCustomers = getAllCustomers();
        customerList.getItems().setAll(allCustomers);
    }

    private void addChangeListenerToSearchField() {
        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                List<CustomerViewModel> allCustomers;
                if (newValue.length() >= 2) {
                    allCustomers = getAllCustomersWithName(newValue);
                } else {
                    allCustomers = getAllCustomers();
                }
                customerList.getItems().setAll(allCustomers);
            }
        });
    }

    private List<CustomerViewModel> getAllCustomers() {
        List<CustomerViewModel> customers = new ArrayList<>();

        SearchResult<CustomerDTO> customerSearchResult = customerController.getAllCustomers();
        if (customerSearchResult.isSuccess()) {
            for (CustomerDTO customerDTO : customerSearchResult.getResults()) {
                CustomerViewModel customerViewModel = customerViewMapper.mapDTOToViewModel(customerDTO);
                customers.add(customerViewModel);
            }
        }

        return customers;
    }

    private List<CustomerViewModel> getAllCustomersWithName(String name) {
        ArrayList<CustomerViewModel> customers = new ArrayList<>();

        SearchResult<CustomerDTO> customerSearchResult = customerController.getAllCustomersWithName(name);
        if (customerSearchResult.isSuccess()) {
            for (CustomerDTO customerDTO : customerSearchResult.getResults()) {
                CustomerViewModel customerViewModel = customerViewMapper.mapDTOToViewModel(customerDTO);
                customers.add(customerViewModel);
            }
        }

        return customers;
    }

    private void addChangeListenerToCustomerList() {
        customerList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerViewModel>() {
            @Override
            public void changed(ObservableValue<? extends CustomerViewModel> observable, CustomerViewModel oldValue, CustomerViewModel newValue) {
                showModifyButton.setVisible(newValue != null);
            }
        });
    }

    public void showModify(ActionEvent actionEvent) {
        CustomerViewModel selectedCustomer = customerList.getSelectionModel().getSelectedItem();
        SearchResult<CustomerDTO> customerResult = customerController.getCustomer(selectedCustomer.getId());
        if (customerResult.isSuccess()) {
            CustomerDTO customer = customerResult.getFirst();
            name1Field.setText(customer.getName1());
            name2Field.setText(customer.getName2());
            address1Field.setText(customer.getAddress1());
            address2Field.setText(customer.getAddress2());
            postalCodeField.setText(customer.getPostalCode());
            residenceField.setText(customer.getResidence());
            countryField.setText(customer.getCountry());
            telephoneField.setText(customer.getTelephone());
            gsmField.setText(customer.getGsm());
            faxField.setText(customer.getFax());
            btwNumberField.setText(customer.getBtwNumber());
            emailAddressField.setText(customer.getEmailAddress());

            modifyPane.setVisible(true);
        }
    }

    public void modifyCustomer(Event event) {
        try {
            CustomerViewModel selectedCustomer = customerList.getSelectionModel().getSelectedItem();
            CrudsResult customerModifyResult = modifyCustomer(selectedCustomer.getId());

            if (customerModifyResult.isSuccess()) {
                handleModifySuccess();
            } else {
                handleModifyError(customerModifyResult.getMessages());
            }
        } catch (Exception e) {
            handleModifyException(e);
        }
    }

    private CrudsResult modifyCustomer(String id) {
        CustomerDTO customer = new CustomerDTO();
        customer.setId(id);
        customer.setName1(name1Field.getText());
        customer.setName2(name2Field.getText());
        customer.setAddress1(address1Field.getText());
        customer.setAddress2(address2Field.getText());
        customer.setPostalCode(postalCodeField.getText());
        customer.setResidence(residenceField.getText());
        customer.setCountry(countryField.getText());
        customer.setTelephone(telephoneField.getText());
        customer.setGsm(gsmField.getText());
        customer.setFax(faxField.getText());
        customer.setBtwNumber(btwNumberField.getText());
        customer.setEmailAddress(emailAddressField.getText());
        return customerController.updateCustomer(customer);
    }

    private void handleModifySuccess() {
        initializeTextFields();
        modifyPane.setVisible(false);
        customerList.getSelectionModel().clearSelection();
        loadAllCustomersWithName();
        AlertController.alertSuccess("Klant bewerkt!");
    }

    private void loadAllCustomersWithName() {
        ArrayList<CustomerViewModel> customers = new ArrayList<>();

        SearchResult<CustomerDTO> customerSearchResult = customerController.getAllCustomersWithName(searchField.getText());
        if (customerSearchResult.isSuccess()) {
            for (CustomerDTO customerDTO : customerSearchResult.getResults()) {
                CustomerViewModel customerViewModel = customerViewMapper.mapDTOToViewModel(customerDTO);
                customers.add(customerViewModel);
            }
        }

        customerList.getItems().setAll(customers);
    }

    private void initializeTextFields() {
        name1Field.setText("");
        name2Field.setText("");
        address1Field.setText("");
        address2Field.setText("");
        postalCodeField.setText("");
        residenceField.setText("");
        countryField.setText("BE");
        telephoneField.setText("");
        gsmField.setText("");
        faxField.setText("");
        btwNumberField.setText("");
        emailAddressField.setText("");
    }

    private void handleModifyError(List<String> errorMessages) {
        StringBuilder errorBuilder = new StringBuilder("Gefaald wegens volgende fout(en): ");
        for (int i = 0; i < errorMessages.size(); i++) {
            String errorMessage = errorMessages.get(i);
            errorBuilder.append("\n").append(i+1).append(") ").append(errorMessage);
        }
        AlertController.alertError("Klant bewerken gefaald!", errorBuilder.toString());
    }

    private void handleModifyException(Exception e) {
        AlertController.alertException("Klant bewerken gefaald!", e);
    }
}
