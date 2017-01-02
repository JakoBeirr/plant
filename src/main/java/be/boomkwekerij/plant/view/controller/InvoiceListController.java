package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.model.dto.DateDTO;
import be.boomkwekerij.plant.view.model.InvoiceViewModel;
import be.boomkwekerij.plant.view.services.InvoiceListService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.joda.time.DateTime;

import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class InvoiceListController implements PageController {

    private InvoiceListService invoiceListService = new InvoiceListService();

    @FXML
    private TextField invoiceSearchField;
    @FXML
    private TableView<InvoiceViewModel> invoiceList;
    @FXML
    private Button payInvoiceButton;
    @FXML
    private Button unPayInvoiceButton;
    @FXML
    private Button printInvoiceButton;
    @FXML
    private Button printSellingConditionsButton;
    @FXML
    private Button deleteInvoiceButton;

    @Override
    public void init(Pane root) {
        invoiceListService.setInvoiceSearchField(invoiceSearchField);
        invoiceListService.setInvoiceList(invoiceList);
        invoiceListService.setPayInvoiceButton(payInvoiceButton);
        invoiceListService.setUnPayInvoiceButton(unPayInvoiceButton);
        invoiceListService.setPrintInvoiceButton(printInvoiceButton);
        invoiceListService.setPrintSellingConditionsButton(printSellingConditionsButton);
        invoiceListService.setDeleteInvoiceButton(deleteInvoiceButton);
        invoiceListService.init(root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addChangeListenerToField();
        addChangeListenersToList();
    }

    private void addChangeListenerToField() {
        invoiceSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 2) {
                invoiceListService.loadAllInvoicesWithNumberService.restart();
            } else {
                invoiceListService.loadAllInvoicesService.restart();
            }
        });
    }

    private void addChangeListenersToList() {
        invoiceList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                payInvoiceButton.setVisible(!newValue.getPayed());
                payInvoiceButton.setManaged(!newValue.getPayed());
                unPayInvoiceButton.setVisible(newValue.getPayed());
                unPayInvoiceButton.setManaged(newValue.getPayed());
                printInvoiceButton.setVisible(true);
                printInvoiceButton.setManaged(true);
                deleteInvoiceButton.setVisible(true);
                deleteInvoiceButton.setManaged(true);
            } else {
                payInvoiceButton.setVisible(false);
                payInvoiceButton.setManaged(false);
                unPayInvoiceButton.setVisible(false);
                unPayInvoiceButton.setManaged(false);
                printInvoiceButton.setVisible(false);
                printInvoiceButton.setManaged(false);
                deleteInvoiceButton.setVisible(false);
                deleteInvoiceButton.setManaged(false);
            }
        });
    }

    public void payInvoice(ActionEvent actionEvent) {
        Dialog<DateDTO> payDialog = new Dialog<DateDTO>();
        payDialog.setTitle("Factuur betaald");
        payDialog.setHeaderText("Gelieve aan te geven wanneer deze factuur betaald werd!");

        GridPane fieldPane = new GridPane();
        fieldPane.setHgap(10);
        fieldPane.setPadding(new Insets(20, 150, 10, 10));
        DatePicker payDatePicker = new DatePicker();
        fieldPane.add(payDatePicker, 1, 0);
        payDialog.getDialogPane().setContent(fieldPane);

        ButtonType payButton = new ButtonType("Betaald", ButtonBar.ButtonData.OK_DONE);
        payDialog.getDialogPane().getButtonTypes().addAll(payButton);

        payDialog.setResultConverter(dialogButton -> {
            if (dialogButton == payButton) {
                LocalDate payDate = payDatePicker.getValue();

                if (payDate != null) {
                    DateDTO dateDTO = new DateDTO();
                    dateDTO.setPayDate(new DateTime(payDate.getYear(), payDate.getMonthValue(), payDate.getDayOfMonth(), 0, 0, 0, 0));
                    return dateDTO;
                }
            }
            return null;
        });

        Optional<DateDTO> payResult = payDialog.showAndWait();
        if (payResult.isPresent()) {
            DateDTO dateDTO = payResult.get();

            invoiceListService.setPayDate(dateDTO);
            invoiceListService.payInvoiceService.restart();
        }
    }

    public void unPayInvoice(ActionEvent actionEvent) {
        invoiceListService.unPayInvoiceService.restart();
    }

    public void printInvoice(ActionEvent actionEvent) {
        invoiceListService.printInvoiceService.restart();
    }

    public void printSellingConditions(ActionEvent actionEvent) {
        invoiceListService.printSellingConditionsService.restart();
    }

    public void deleteInvoice(ActionEvent actionEvent) {
        if (AlertController.areYouSure("Bent u zeker dat u deze factuur wil verwijderen?", "Bedenk dat u deze factuur nadien niet meer zal kunnen herstellen!")) {
            invoiceListService.deleteInvoiceService.restart();
        }
    }
}
