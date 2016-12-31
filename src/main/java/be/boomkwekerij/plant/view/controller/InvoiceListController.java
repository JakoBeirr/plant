package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.controller.InvoiceController;
import be.boomkwekerij.plant.model.dto.DateDTO;
import be.boomkwekerij.plant.model.dto.InvoiceDTO;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;
import be.boomkwekerij.plant.view.mapper.InvoiceViewMapper;
import be.boomkwekerij.plant.view.model.InvoiceViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.joda.time.DateTime;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class InvoiceListController implements Initializable {

    private InvoiceController invoiceController = new InvoiceController();

    private InvoiceViewMapper invoiceViewMapper = new InvoiceViewMapper();

    @FXML
    private TextField searchField;
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
    private Button payButton;
    @FXML
    private Button unPayButton;
    @FXML
    private Button printInvoiceButton;
    @FXML
    private Button printSellingConditionsButton;
    @FXML
    private Button deleteButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadAllInvoices();
        addChangeListenerToField();
        addChangeListenersToList();
    }

    private void loadAllInvoices() {
        List<InvoiceViewModel> allInvoices = getAllInvoices();
        invoiceList.getItems().setAll(allInvoices);
    }

    private void addChangeListenerToField() {
        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                List<InvoiceViewModel> invoices;
                if (newValue.length() > 2) {
                    invoices = getInvoicesWithNumber(newValue);
                } else {
                    invoices = getAllInvoices();
                }
                invoiceList.getItems().setAll(invoices);
            }
        });
    }

    private List<InvoiceViewModel> getAllInvoices() {
        List<InvoiceViewModel> invoices = new ArrayList<>();

        SearchResult<InvoiceDTO> invoiceSearchResult = invoiceController.getAllInvoices();
        if (invoiceSearchResult.isSuccess()) {
            for (InvoiceDTO invoiceDTO : invoiceSearchResult.getResults()) {
                InvoiceViewModel invoiceViewModel = invoiceViewMapper.mapDTOToViewModel(invoiceDTO);
                invoices.add(invoiceViewModel);
            }
        }

        return invoices;
    }

    private List<InvoiceViewModel> getInvoicesWithNumber(String invoiceNumber) {
        List<InvoiceViewModel> invoices = new ArrayList<>();

        SearchResult<InvoiceDTO> invoiceSearchResult = invoiceController.getAllInvoicesWithInvoiceNumber(invoiceNumber);
        if (invoiceSearchResult.isSuccess()) {
            for (InvoiceDTO invoiceDTO : invoiceSearchResult.getResults()) {
                InvoiceViewModel invoiceViewModel = invoiceViewMapper.mapDTOToViewModel(invoiceDTO);
                invoices.add(invoiceViewModel);
            }
        }

        return invoices;
    }

    private void addChangeListenersToList() {
        invoiceList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<InvoiceViewModel>() {
            @Override
            public void changed(ObservableValue<? extends InvoiceViewModel> observable, InvoiceViewModel oldValue, InvoiceViewModel newValue) {
                if (newValue != null) {
                    payButton.setVisible(!newValue.getPayed());
                    payButton.setManaged(!newValue.getPayed());
                    unPayButton.setVisible(newValue.getPayed());
                    unPayButton.setManaged(newValue.getPayed());
                    printInvoiceButton.setVisible(true);
                    printInvoiceButton.setManaged(true);
                    deleteButton.setVisible(true);
                    deleteButton.setManaged(true);
                } else {
                    payButton.setVisible(false);
                    payButton.setManaged(false);
                    unPayButton.setVisible(false);
                    unPayButton.setManaged(false);
                    printInvoiceButton.setVisible(false);
                    printInvoiceButton.setManaged(false);
                    deleteButton.setVisible(false);
                    deleteButton.setManaged(false);
                }
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

            InvoiceViewModel selectedInvoice = invoiceList.getSelectionModel().getSelectedItem();
            CrudsResult payInvoiceResult = invoiceController.payInvoice(selectedInvoice.getId(), dateDTO);

            if (payInvoiceResult.isSuccess()) {
                loadAllInvoices();
            }
        }
    }

    public void unPayInvoice(ActionEvent actionEvent) {
        InvoiceViewModel selectedInvoice = invoiceList.getSelectionModel().getSelectedItem();
        CrudsResult unPayInvoiceResult = invoiceController.unPayInvoice(selectedInvoice.getId());

        if (unPayInvoiceResult.isSuccess()) {
            loadAllInvoices();
        }
    }

    public void printInvoice(ActionEvent actionEvent) {
        InvoiceViewModel selectedInvoice = invoiceList.getSelectionModel().getSelectedItem();
        CrudsResult printResult = invoiceController.printInvoiceDocument(selectedInvoice.getId());

        if (printResult.isSuccess()) {
            AlertController.alertSuccess("Factuur klaar!");
        } else {
            handlePrintError(printResult.getMessages());
        }
    }

    public void printSellingConditions(ActionEvent actionEvent) {
        CrudsResult printResult = invoiceController.printSellingConditions();

        if (printResult.isSuccess()) {
            AlertController.alertSuccess("Algemene voorwaarden klaar!");
        } else {
            handlePrintError(printResult.getMessages());
        }
    }

    private void handlePrintError(List<String> errorMessages) {
        StringBuilder errorBuilder = new StringBuilder("Gefaald wegens volgende fout(en): ");
        for (int i = 0; i < errorMessages.size(); i++) {
            String errorMessage = errorMessages.get(i);
            errorBuilder.append("\n").append(i+1).append(") ").append(errorMessage);
        }
        AlertController.alertError("Factuur aanmaken gefaald!", errorBuilder.toString());
    }

    public void deleteInvoice(ActionEvent actionEvent) {
        if (AlertController.areYouSure("Bent u zeker dat u deze factuur wil verwijderen?", "Bedenk dat u deze factuur nadien niet meer zal kunnen herstellen!")) {
            try {
                InvoiceViewModel selectedInvoice = invoiceList.getSelectionModel().getSelectedItem();
                CrudsResult crudsResult = invoiceController.deleteInvoice(selectedInvoice.getId());

                if (crudsResult.isSuccess()) {
                    handleDeleteSuccess();
                } else {
                    handleDeleteError(crudsResult.getMessages());
                }
            } catch (Exception e) {
                handleDeleteException(e);
            }
        }
    }

    private void handleDeleteSuccess() {
        loadAllInvoices();
        AlertController.alertSuccess("Factuur verwijderd!");
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
        AlertController.alertError("Factuur verwijderen gefaald!", errorBuilder.toString());
    }

    private void handleDeleteException(Exception e) {
        AlertController.alertException("Factuur verwijderen gefaald!", e);
    }
}
