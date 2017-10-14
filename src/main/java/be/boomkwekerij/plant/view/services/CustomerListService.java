package be.boomkwekerij.plant.view.services;

import be.boomkwekerij.plant.controller.CustomerController;
import be.boomkwekerij.plant.controller.FustController;
import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.model.dto.FustDTO;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;
import be.boomkwekerij.plant.view.controller.AlertController;
import be.boomkwekerij.plant.view.mapper.CustomerViewMapper;
import be.boomkwekerij.plant.view.model.CustomerViewModel;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomerListService {

    private CustomerController customerController = new CustomerController();
    private FustController fustController = new FustController();

    private CustomerViewMapper customerViewMapper = new CustomerViewMapper();

    private TextField customerSearchField;
    private TableView<CustomerViewModel> customerList;
    private Button customerDetailsButton;
    private Button printFustButton;
    private Button customerDeleteButton;

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

                    Platform.runLater(() -> customerList.getItems().setAll(customers));

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

                    Platform.runLater(() -> customerList.getItems().setAll(customers));

                    return null;
                }
            };
        }
    };

    public final Service showCustomerDetailsService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Tonen details klant");

                    CustomerViewModel selectedCustomer = customerList.getSelectionModel().getSelectedItem();
                    SearchResult<CustomerDTO> searchResult = customerController.getCustomer(selectedCustomer.getId());
                    if (searchResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(searchResult.getMessages().toArray()));
                    }
                    CustomerDTO customer = searchResult.getFirst();

                    Platform.runLater(() -> show(customer));

                    return null;
                }
            };
        }
    };

    public final Service printFustService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Fust printen");

                    CustomerViewModel selectedCustomer = customerList.getSelectionModel().getSelectedItem();

                    CrudsResult printResult = fustController.printFustFromCustomerReport(selectedCustomer.getId());
                    if (printResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(printResult.getMessages().toArray()));
                    }

                    return null;
                }
            };
        }
    };

    public final Service deleteCustomerService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Verwijderen klant");

                    CustomerViewModel selectedCustomer = customerList.getSelectionModel().getSelectedItem();
                    CrudsResult deleteResult = customerController.deleteCustomer(selectedCustomer.getId());
                    if (deleteResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(deleteResult.getMessages().toArray()));
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

    public void setCustomerDetailsButton(Button customerDetailsButton) {
        this.customerDetailsButton = customerDetailsButton;
    }

    public void setPrintFustButton(Button printFustButton) {
        this.printFustButton = printFustButton;
    }

    public void setCustomerDeleteButton(Button customerDeleteButton) {
        this.customerDeleteButton = customerDeleteButton;
    }

    public void init(Pane root) {
        root.cursorProperty()
                .bind(Bindings.when(loadAllCustomersService.runningProperty()
                            .or(loadAllCustomersWithNameService.runningProperty()
                            .or(showCustomerDetailsService.runningProperty()
                            .or(printFustService.runningProperty()
                            .or(deleteCustomerService.runningProperty())))))
                        .then(Cursor.WAIT)
                        .otherwise(Cursor.DEFAULT)
                );
        customerDetailsButton.disableProperty()
                .bind(showCustomerDetailsService.runningProperty());
        printFustButton.disableProperty()
                .bind(printFustService.runningProperty());
        customerDeleteButton.disableProperty()
                .bind(deleteCustomerService.runningProperty());

        showCustomerDetailsService.setOnFailed(serviceEvent -> {
            ServiceHandler.error(showCustomerDetailsService);
        });
        printFustService.setOnSucceeded(serviceEvent -> {
            ServiceHandler.success(printFustService);
        });
        printFustService.setOnFailed(serviceEvent -> {
            ServiceHandler.error(printFustService);
        });
        deleteCustomerService.setOnSucceeded(serviceEvent -> {
            if (customerSearchField.getText().length() > 2) {
                loadAllCustomersWithNameService.restart();
            } else {
                loadAllCustomersService.restart();
            }

            ServiceHandler.success(deleteCustomerService);
        });
        deleteCustomerService.setOnFailed(serviceEvent -> {
            ServiceHandler.error(deleteCustomerService);
        });

        loadAllCustomersService.restart();
    }

    private void show(CustomerDTO customer) {
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
