package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.view.model.CustomerViewModel;
import be.boomkwekerij.plant.view.model.PlantViewModel;
import be.boomkwekerij.plant.view.services.InvoiceCreateService;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseDragEvent;
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
    private Button showCreateInvoiceButton;
    @FXML
    private GridPane createInvoicePane;
    @FXML
    private TextField customer;
    @FXML
    private TextField invoiceNumber;
    @FXML
    private DatePicker invoiceDate;
    @FXML
    private Label labelInvoiceLinesList;
    @FXML
    private VBox invoiceLines;
    @FXML
    private Label labelInvoiceLine;
    @FXML
    private TextField plantSearchField;
    @FXML
    private TableView<PlantViewModel> plantList;
    @FXML
    private Label chosenPlant;
    @FXML
    private Button choosePlantButton;
    @FXML
    private Label remarkLabel;
    @FXML
    private TextField remark;
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
    private Button invoiceCreateButton;

    private static final String NON_NUMERIC_CHARACTERS = "[^\\d]";
    private static final String NON_DECIMAL_NUMERIC_CHARACTERS = "[^\\d.]";

    @Override
    public void init(Pane root) {
        invoiceCreateService.setCustomerSearchField(customerSearchField);
        invoiceCreateService.setCustomerList(customerList);
        invoiceCreateService.setShowCreateInvoiceButton(showCreateInvoiceButton);
        invoiceCreateService.setCreateInvoicePane(createInvoicePane);
        invoiceCreateService.setCustomer(customer);
        invoiceCreateService.setInvoiceNumber(invoiceNumber);
        invoiceCreateService.setInvoiceDate(invoiceDate);
        invoiceCreateService.setInvoiceLines(invoiceLines);
        invoiceCreateService.setPlantSearchField(plantSearchField);
        invoiceCreateService.setPlantList(plantList);
        invoiceCreateService.setChoosePlantButton(choosePlantButton);
        invoiceCreateService.setChosenPlant(chosenPlant);
        invoiceCreateService.setRemarkLabel(remarkLabel);
        invoiceCreateService.setRemark(remark);
        invoiceCreateService.setOrderNumber(orderNumber);
        invoiceCreateService.setInvoiceLineDate(invoiceLineDate);
        invoiceCreateService.setInvoiceLineBtw(invoiceLineBtw);
        invoiceCreateService.setAmount(amount);
        invoiceCreateService.setAlternativePlantPrice(alternativePlantPrice);
        invoiceCreateService.setInvoiceCreateButton(invoiceCreateButton);
        invoiceCreateService.init(root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addChangeListenerToSearchFields();
        addChangeListenersToList();
        initNumericFields();
        initDragAndDrop();
    }

    private void addChangeListenerToSearchFields() {
        customerSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() >= 2) {
                invoiceCreateService.loadAllCustomersWithNameService.restart();
            } else {
                invoiceCreateService.loadAllCustomersService.restart();
            }
        });
        plantSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
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
        });
    }

    private void addChangeListenersToList() {
        customerList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showCreateInvoiceButton.setVisible(newValue != null);
        });
        plantList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (plantList.isVisible()) {
                choosePlantButton.setVisible(newValue != null);
                choosePlantButton.setManaged(newValue != null);
            } else {
                choosePlantButton.setVisible(false);
                choosePlantButton.setManaged(false);
            }
        });
        invoiceLines.getChildren().addListener((ListChangeListener<Node>) c -> {
            if (c.getList().size() > 0) {
                invoiceLines.setVisible(true);
                invoiceLines.setManaged(true);
                labelInvoiceLinesList.setVisible(true);
                labelInvoiceLinesList.setManaged(true);
                labelInvoiceLine.setVisible(false);
                labelInvoiceLine.setManaged(false);
            } else {
                invoiceLines.setVisible(false);
                invoiceLines.setManaged(false);
                labelInvoiceLinesList.setVisible(false);
                labelInvoiceLinesList.setManaged(false);
                labelInvoiceLine.setVisible(true);
                labelInvoiceLine.setManaged(true);
            }
        });
    }

    private void initNumericFields() {
        amount.textProperty().addListener((observable, oldValue, newValue) -> {
            amount.setText(newValue.replaceAll(NON_NUMERIC_CHARACTERS, ""));
        });
        alternativePlantPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            alternativePlantPrice.setText(newValue.replaceAll(NON_DECIMAL_NUMERIC_CHARACTERS, ""));
        });
        invoiceLineBtw.textProperty().addListener((observable, oldValue, newValue) -> {
            invoiceLineBtw.setText(newValue.replaceAll(NON_DECIMAL_NUMERIC_CHARACTERS, ""));
        });
    }

    private void initDragAndDrop() {
        invoiceLines.setOnMouseDragReleased(event -> {
            int indexOfDraggingNode = invoiceLines.getChildren().indexOf(event.getGestureSource());
            rotateNodes(invoiceLines, indexOfDraggingNode, invoiceLines.getChildren().size()-1);
        });
    }

    private void rotateNodes(VBox root, int indexOfDraggingNode, int indexOfDropTarget) {
        if (indexOfDraggingNode >= 0 && indexOfDropTarget >= 0) {
            Node node = root.getChildren().remove(indexOfDraggingNode);
            root.getChildren().add(indexOfDropTarget, node);
        }
    }

    public void showCreateInvoice(ActionEvent actionEvent) {
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
