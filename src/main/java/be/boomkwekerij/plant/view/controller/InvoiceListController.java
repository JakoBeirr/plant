package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.controller.InvoiceController;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    private Button printButton;

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
                    payButton.setManaged(!newValue.getPayed());
                    payButton.setVisible(!newValue.getPayed());
                    unPayButton.setManaged(newValue.getPayed());
                    unPayButton.setVisible(newValue.getPayed());
                    printButton.setVisible(true);
                } else {
                    payButton.setManaged(false);
                    payButton.setVisible(false);
                    unPayButton.setManaged(false);
                    unPayButton.setVisible(false);
                    printButton.setVisible(false);
                }
            }
        });
    }

    public void payInvoice(ActionEvent actionEvent) {
        InvoiceViewModel selectedInvoice = invoiceList.getSelectionModel().getSelectedItem();
        CrudsResult payInvoiceResult = invoiceController.payInvoice(selectedInvoice.getId());

        if (payInvoiceResult.isSuccess()) {
            loadAllInvoices();
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
            AlertController.alertSuccess("Factuur aangemaakt!");
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
}
