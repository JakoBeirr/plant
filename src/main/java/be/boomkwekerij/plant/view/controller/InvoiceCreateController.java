package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.view.model.CustomerViewModel;
import be.boomkwekerij.plant.view.model.PlantViewModel;
import be.boomkwekerij.plant.view.services.InvoiceCreateService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class InvoiceCreateController implements PageController {

    private InvoiceCreateService invoiceCreateService = new InvoiceCreateService();

    @FXML
    private TextField customerSearchField;
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
    private Button startCreateInvoiceButton;
    @FXML
    private GridPane createInvoicePane;
    @FXML
    private TextField customer;
    @FXML
    private TextField invoiceNumber;
    @FXML
    private DatePicker invoiceDate;
    @FXML
    private VBox createdInvoiceLines;
    @FXML
    private Label labelInvoiceLinesList;
    @FXML
    private Label labelInvoiceLinesCreate;
    @FXML
    private TextField orderNumber;
    @FXML
    private DatePicker invoiceLineDate;
    @FXML
    private TextField invoiceLineBtw;
    @FXML
    private TextField amount;
    @FXML
    private TextField alternativePlantPrice;
    @FXML
    private TextField plantSearchField;
    @FXML
    private TableView<PlantViewModel> plantList;
    @FXML
    private TableColumn<PlantViewModel, String> name;
    @FXML
    private TableColumn<PlantViewModel, String> age;
    @FXML
    private TableColumn<PlantViewModel, String> measure;
    @FXML
    private TableColumn<PlantViewModel, Double> price;
    @FXML
    private Label chosenPlant;
    @FXML
    private Button choosePlantButton;
    @FXML
    private Button invoiceCreateButton;

    private static final String NON_NUMERIC_CHARACTERS = "[^\\d]";
    private static final String NON_DECIMAL_NUMERIC_CHARACTERS = "[^\\d.]";

    @Override
    public void init(Pane root) {
        invoiceCreateService.setCustomerSearchField(customerSearchField);
        invoiceCreateService.setCustomerList(customerList);
        invoiceCreateService.setPlantSearchField(plantSearchField);
        invoiceCreateService.setPlantList(plantList);
        invoiceCreateService.setCustomer(customer);
        invoiceCreateService.setInvoiceNumber(invoiceNumber);
        invoiceCreateService.setInvoiceDate(invoiceDate);
        invoiceCreateService.setCreateInvoicePane(createInvoicePane);
        invoiceCreateService.setChoosePlantButton(choosePlantButton);
        invoiceCreateService.setChosenPlant(chosenPlant);
        invoiceCreateService.setOrderNumber(orderNumber);
        invoiceCreateService.setInvoiceLineDate(invoiceLineDate);
        invoiceCreateService.setInvoiceLineBtw(invoiceLineBtw);
        invoiceCreateService.setAmount(amount);
        invoiceCreateService.setAlternativePlantPrice(alternativePlantPrice);
        invoiceCreateService.setCreatedInvoiceLines(createdInvoiceLines);
        invoiceCreateService.setInvoiceCreateButton(invoiceCreateButton);
        invoiceCreateService.init(root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        invoiceCreateService.loadAllCustomersService.restart();
        addChangeListenerToSearchFields();
        addChangeListenersToList();
        initNumericFields();
    }

    private void addChangeListenerToSearchFields() {
        customerSearchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length() >= 2) {
                    invoiceCreateService.loadAllCustomersWithNameService.restart();
                } else {
                    invoiceCreateService.loadAllCustomersService.restart();
                }
            }
        });
        plantSearchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length() > 0) {
                    plantList.setVisible(true);
                    plantList.setManaged(true);
                } else {
                    plantList.setVisible(false);
                    plantList.setManaged(false);
                }

                if (newValue.length() >= 2) {
                    invoiceCreateService.loadAllPlantsWithNameService.restart();
                }
            }
        });
    }

    private void addChangeListenersToList() {
        customerList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerViewModel>() {
            @Override
            public void changed(ObservableValue<? extends CustomerViewModel> observable, CustomerViewModel oldValue, CustomerViewModel newValue) {
                startCreateInvoiceButton.setVisible(newValue != null);
            }
        });
        plantList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PlantViewModel>() {
            @Override
            public void changed(ObservableValue<? extends PlantViewModel> observable, PlantViewModel oldValue, PlantViewModel newValue) {
                choosePlantButton.setVisible(newValue != null);
                choosePlantButton.setManaged(newValue != null);
            }
        });
        createdInvoiceLines.getChildren().addListener(new ListChangeListener<Node>() {
            @Override
            public void onChanged(Change<? extends Node> c) {
                if (c.getList().size() > 0) {
                    createdInvoiceLines.setVisible(true);
                    createdInvoiceLines.setManaged(true);
                    labelInvoiceLinesList.setVisible(true);
                    labelInvoiceLinesList.setManaged(true);
                    labelInvoiceLinesCreate.setVisible(false);
                    labelInvoiceLinesCreate.setManaged(false);
                } else {
                    createdInvoiceLines.setVisible(false);
                    createdInvoiceLines.setManaged(false);
                    labelInvoiceLinesList.setVisible(false);
                    labelInvoiceLinesList.setManaged(false);
                    labelInvoiceLinesCreate.setVisible(true);
                    labelInvoiceLinesCreate.setManaged(true);
                }
            }
        });
    }

    private void initNumericFields() {
        amount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                amount.setText(newValue.replaceAll(NON_NUMERIC_CHARACTERS, ""));
            }
        });
        alternativePlantPrice.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                alternativePlantPrice.setText(newValue.replaceAll(NON_DECIMAL_NUMERIC_CHARACTERS, ""));
            }
        });
        invoiceLineBtw.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                invoiceLineBtw.setText(newValue.replaceAll(NON_DECIMAL_NUMERIC_CHARACTERS, ""));
            }
        });
    }

    public void startInvoiceCreation(ActionEvent actionEvent) {
        invoiceCreateService.initInvoiceCreateService.restart();
    }

    public void choosePlant(ActionEvent actionEvent) {
        invoiceCreateService.choosePlantService.restart();
    }

    public void clearInvoiceLine(ActionEvent actionEvent) {
        invoiceCreateService.clearInvoiceLineService.restart();
    }

    public void createInvoiceLine(ActionEvent actionEvent) {
        invoiceCreateService.createInvoiceLineService.restart();
    }

    public void createInvoice(Event event) {
        invoiceCreateService.createInvoiceService.restart();
    }
}
