package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.controller.CustomerController;
import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;
import be.boomkwekerij.plant.view.mapper.CustomerViewMapper;
import be.boomkwekerij.plant.view.model.CustomerViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerListController implements Initializable {

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
    private Button deleteButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadAllCustomers();
        addChangeListenerToSearchField();
        addChangeListenerToCustomerList();
    }

    private void loadAllCustomers() {
        List<CustomerViewModel> customers = getAllCustomers();
        customerList.getItems().setAll(customers);
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
                deleteButton.setVisible(newValue != null);
            }
        });
    }

    public void deleteCustomer(Event event) {
        try {
            CustomerViewModel selectedCustomer = customerList.getSelectionModel().getSelectedItem();
            CrudsResult deleteResult = customerController.deleteCustomer(selectedCustomer.getId());

            if (deleteResult.isSuccess()) {
                handleDeleteSuccess();
            } else {
                handleDeleteError(deleteResult.getMessages());
            }
        } catch (Exception e) {
            handleDeleteException(e);
        }
    }

    private void handleDeleteSuccess() {
        loadAllCustomers();
        AlertController.alertSuccess("Klant verwijderd!");
    }

    private void handleDeleteError(List<String> errorMessages) {
        StringBuilder errorBuilder = new StringBuilder("Gefaald wegens volgende fout(en): ");
        for (int i = 0; i < errorMessages.size(); i++) {
            String errorMessage = errorMessages.get(i);
            errorBuilder.append(errorMessage);

            if (i != (errorMessages.size()-1)) {
                errorBuilder.append("; ");
            }
        }
        AlertController.alertError("Klant verwijderen gefaald!", errorBuilder.toString());
    }

    private void handleDeleteException(Exception e) {
        AlertController.alertException("Klant verwijderen gefaald!", e);
    }
}
