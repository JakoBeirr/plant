package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.view.model.InvoiceViewModel;
import be.boomkwekerij.plant.view.model.PlantViewModel;
import be.boomkwekerij.plant.view.services.InvoiceModifyService;
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
    private Button showModifyInvoiceButton;
    @FXML
    private GridPane modifyInvoicePane;
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
    private Button invoiceModifyButton;

    private static final String NON_NUMERIC_CHARACTERS = "[^\\d]";
    private static final String NON_DECIMAL_NUMERIC_CHARACTERS = "[^\\d.]";

    @Override
    public void init(Pane root) {
        invoiceModifyService.setInvoiceSearchField(invoiceSearchField);
        invoiceModifyService.setInvoiceList(invoiceList);
        invoiceModifyService.setShowModifyInvoiceButton(showModifyInvoiceButton);
        invoiceModifyService.setModifyInvoicePane(modifyInvoicePane);
        invoiceModifyService.setCustomer(customer);
        invoiceModifyService.setInvoiceNumber(invoiceNumber);
        invoiceModifyService.setInvoiceDate(invoiceDate);
        invoiceModifyService.setInvoiceLines(invoiceLines);
        invoiceModifyService.setPlantSearchField(plantSearchField);
        invoiceModifyService.setPlantList(plantList);
        invoiceModifyService.setChoosePlantButton(choosePlantButton);
        invoiceModifyService.setChosenPlant(chosenPlant);
        invoiceModifyService.setRemarkLabel(remarkLabel);
        invoiceModifyService.setRemark(remark);
        invoiceModifyService.setOrderNumber(orderNumber);
        invoiceModifyService.setInvoiceLineDate(invoiceLineDate);
        invoiceModifyService.setInvoiceLineBtw(invoiceLineBtw);
        invoiceModifyService.setAmount(amount);
        invoiceModifyService.setAlternativePlantPrice(alternativePlantPrice);
        invoiceModifyService.setInvoiceModifyButton(invoiceModifyButton);
        invoiceModifyService.init(root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addChangeListenerToSearchFields();
        addChangeListenersToList();
        initNumericFields();
        initDragAndDrop();
    }

    private void addChangeListenerToSearchFields() {
        invoiceSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 2) {
                invoiceModifyService.loadAllInvoicesWithNumberService.restart();
            } else {
                invoiceModifyService.loadAllInvoicesService.restart();
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
                invoiceModifyService.loadAllPlantsWithNameService.restart();
            }
        });
    }

    private void addChangeListenersToList() {
        invoiceList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showModifyInvoiceButton.setVisible(newValue != null);
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
            if (newValue.startsWith("-")) {
                alternativePlantPrice.setText("-" + newValue.replaceAll(NON_DECIMAL_NUMERIC_CHARACTERS, ""));
            } else {
                alternativePlantPrice.setText(newValue.replaceAll(NON_DECIMAL_NUMERIC_CHARACTERS, ""));
            }
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

    public void showModifyInvoice(ActionEvent actionEvent) {
        invoiceModifyService.initInvoiceModifyService.restart();
    }

    public void choosePlant(ActionEvent actionEvent) {
        invoiceModifyService.choosePlantService.restart();
    }

    public void clearInvoiceLine(ActionEvent actionEvent) {
        invoiceModifyService.clearInvoiceLineService.restart();
    }

    public void createInvoiceLine(ActionEvent actionEvent) {
        invoiceModifyService.createInvoiceLineService.restart();
    }

    public void modifyInvoice(Event event) {
        invoiceModifyService.modifyInvoiceService.restart();
    }
}
