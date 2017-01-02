package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.view.model.InvoiceViewModel;
import be.boomkwekerij.plant.view.model.PlantViewModel;
import be.boomkwekerij.plant.view.services.InvoiceModifyService;
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

public class InvoiceModifyController implements PageController {

    private InvoiceModifyService invoiceModifyService = new InvoiceModifyService();

    @FXML
    private TextField invoiceSearchField;
    @FXML
    private TableView<InvoiceViewModel> invoiceList;
    @FXML
    private TableColumn<InvoiceViewModel, String> customerName;
    @FXML
    private TableColumn<InvoiceViewModel, String> invoiceNumber;
    @FXML
    private TableColumn<InvoiceViewModel, String> date;
    @FXML
    private TableColumn<InvoiceViewModel, Double> totalPrice;
    @FXML
    private TableColumn<InvoiceViewModel, Boolean> payed;
    @FXML
    private Button showModifyButton;
    @FXML
    private GridPane modifyInvoicePane;
    @FXML
    private TextField customer;
    @FXML
    private TextField invoiceNumberField;
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
    private Button invoiceModifyButton;

    private static final String NON_NUMERIC_CHARACTERS = "[^\\d]";
    private static final String NON_DECIMAL_NUMERIC_CHARACTERS = "[^\\d.]";

    @Override
    public void init(Pane root) {
        invoiceModifyService.setInvoiceSearchField(invoiceSearchField);
        invoiceModifyService.setInvoiceList(invoiceList);
        invoiceModifyService.setPlantSearchField(plantSearchField);
        invoiceModifyService.setPlantList(plantList);
        invoiceModifyService.setCustomer(customer);
        invoiceModifyService.setInvoiceNumberField(invoiceNumberField);
        invoiceModifyService.setInvoiceDate(invoiceDate);
        invoiceModifyService.setShowModifyButton(showModifyButton);
        invoiceModifyService.setModifyInvoicePane(modifyInvoicePane);
        invoiceModifyService.setChoosePlantButton(choosePlantButton);
        invoiceModifyService.setChosenPlant(chosenPlant);
        invoiceModifyService.setOrderNumber(orderNumber);
        invoiceModifyService.setInvoiceLineDate(invoiceLineDate);
        invoiceModifyService.setInvoiceLineBtw(invoiceLineBtw);
        invoiceModifyService.setAmount(amount);
        invoiceModifyService.setAlternativePlantPrice(alternativePlantPrice);
        invoiceModifyService.setCreatedInvoiceLines(createdInvoiceLines);
        invoiceModifyService.setInvoiceModifyButton(invoiceModifyButton);
        invoiceModifyService.init(root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        invoiceModifyService.loadAllInvoicesService.restart();
        addChangeListenerToSearchFields();
        addChangeListenersToList();
        initNumericFields();
    }

    private void addChangeListenerToSearchFields() {
        invoiceSearchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length() > 2) {
                    invoiceModifyService.loadAllInvoicesWithNumberService.restart();
                } else {
                    invoiceModifyService.loadAllInvoicesService.restart();
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
                    invoiceModifyService.loadAllPlantsWithNameService.restart();
                }
            }
        });
    }

    private void addChangeListenersToList() {
        invoiceList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<InvoiceViewModel>() {
            @Override
            public void changed(ObservableValue<? extends InvoiceViewModel> observable, InvoiceViewModel oldValue, InvoiceViewModel newValue) {
                showModifyButton.setVisible(newValue != null);
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

    public void showModify(ActionEvent actionEvent) {
        invoiceModifyService.initInvoiceModifyService.restart();

    }

    public void choosePlant(ActionEvent actionEvent) {
        invoiceModifyService.choosePlantService.restart();
    }

    public void createInvoiceLine(ActionEvent actionEvent) {
        invoiceModifyService.createInvoiceLineService.restart();
    }

    public void clearInvoiceLine(ActionEvent actionEvent) {
        invoiceModifyService.clearInvoiceLineService.restart();
    }

    public void modifyInvoice(Event event) {
        invoiceModifyService.modifyInvoiceService.restart();
    }
}
