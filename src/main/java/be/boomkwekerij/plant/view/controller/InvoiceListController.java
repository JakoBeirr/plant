package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.controller.InvoiceController;
import be.boomkwekerij.plant.model.dto.InvoiceDTO;
import be.boomkwekerij.plant.util.SearchResult;
import be.boomkwekerij.plant.view.mapper.InvoiceViewMapper;
import be.boomkwekerij.plant.view.model.InvoiceViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class InvoiceListController implements Initializable {

    private InvoiceController invoiceController = new InvoiceController();

    private InvoiceViewMapper invoiceViewMapper = new InvoiceViewMapper();

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadAllInvoices();
    }

    private void loadAllInvoices() {
        List<InvoiceViewModel> allInvoices = getAllInvoices();
        invoiceList.getItems().setAll(allInvoices);
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
}
