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
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

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
    private Button detailsButton;
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
                detailsButton.setVisible(newValue != null);
                deleteButton.setVisible(newValue != null);
            }
        });
    }

    public void deleteCustomer(Event event) {
        if (AlertController.areYouSure("Bent u zeker dat u deze klant wil verwijderen?", "Bedenk dat dit enkel mogelijk is indien er geen facturen gelinkt zijn aan deze klant!")) {
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

    public void showCustomer(Event event) {
        try {
            CustomerViewModel selectedCustomer = customerList.getSelectionModel().getSelectedItem();
            SearchResult<CustomerDTO> customerSearchResult = customerController.getCustomer(selectedCustomer.getId());

            if (customerSearchResult.isSuccess()) {
                if (customerSearchResult.getResults().size() > 0) {
                    CustomerDTO customer = customerSearchResult.getFirst();
                    showCustomerDetails(customer);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showCustomerDetails(CustomerDTO customer) {
        Dialog detailsDialog = new Dialog();
        detailsDialog.setTitle("Klant: " + customer.getName1());

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(5);
        grid.setPadding(new Insets(20, 150, 10, 10));
        Label name1Label = new Label("Naam 1:");
        name1Label.setStyle("-fx-font-weight: bold;");
        grid.add(name1Label, 0, 0);
        grid.add(new Label(customer.getName1()), 1, 0);
        Label name2Label = new Label("Naam 2:");
        name2Label.setStyle("-fx-font-weight: bold;");
        grid.add(name2Label, 0, 1);
        grid.add(new Label(customer.getName2()), 1, 1);
        Label address1Label = new Label("Adres 1:");
        address1Label.setStyle("-fx-font-weight: bold;");
        grid.add(address1Label, 0, 2);
        grid.add(new Label(customer.getAddress1()), 1, 2);
        Label address2Label = new Label("Adres 2:");
        address2Label.setStyle("-fx-font-weight: bold;");
        grid.add(address2Label, 0, 3);
        grid.add(new Label(customer.getAddress2()), 1, 3);
        Label postalCodeLabel = new Label("Postcode:");
        postalCodeLabel.setStyle("-fx-font-weight: bold;");
        grid.add(postalCodeLabel, 0, 4);
        grid.add(new Label(customer.getPostalCode()), 1, 4);
        Label residenceLabel = new Label("Woonplaats:");
        residenceLabel.setStyle("-fx-font-weight: bold;");
        grid.add(residenceLabel, 0, 5);
        grid.add(new Label(customer.getResidence()), 1, 5);
        Label countryLabel = new Label("Land:");
        countryLabel.setStyle("-fx-font-weight: bold;");
        grid.add(countryLabel, 0, 6);
        grid.add(new Label(customer.getCountry()), 1, 6);
        Label telephoneLabel = new Label("Telefoon:");
        telephoneLabel.setStyle("-fx-font-weight: bold;");
        grid.add(telephoneLabel, 0, 7);
        grid.add(new Label(customer.getTelephone()), 1, 7);
        Label gsmLabel = new Label("GSM:");
        gsmLabel.setStyle("-fx-font-weight: bold;");
        grid.add(gsmLabel, 0, 8);
        grid.add(new Label(customer.getGsm()), 1, 8);
        Label faxLabel = new Label("FAX:");
        faxLabel.setStyle("-fx-font-weight: bold;");
        grid.add(faxLabel, 0, 9);
        grid.add(new Label(customer.getFax()), 1, 9);
        Label btwNumberLabel = new Label("BTW nummer:");
        btwNumberLabel.setStyle("-fx-font-weight: bold;");
        grid.add(btwNumberLabel, 0, 10);
        grid.add(new Label(customer.getBtwNumber()), 1, 10);
        Label emailAddressLabel = new Label("E-mail adres:");
        emailAddressLabel.setStyle("-fx-font-weight: bold;");
        grid.add(emailAddressLabel, 0, 11);
        grid.add(new Label(customer.getEmailAddress()), 1, 11);
        detailsDialog.getDialogPane().setContent(grid);

        detailsDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);

        detailsDialog.showAndWait();
    }
}
