package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.view.model.InvoiceViewModel;
import be.boomkwekerij.plant.view.services.ArchivedInvoiceListService;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class ArchivedInvoiceListController implements PageController {

    private final ArchivedInvoiceListService archivedInvoiceListService = new ArchivedInvoiceListService();

    @FXML
    private TableView<InvoiceViewModel> invoiceList;
    @FXML
    private Button printInvoiceButton;
    @FXML
    private Button deleteInvoiceButton;

    @Override
    public void init(Pane root) {
        archivedInvoiceListService.setInvoiceList(invoiceList);
        archivedInvoiceListService.setPrintInvoiceButton(printInvoiceButton);
        archivedInvoiceListService.setDeleteInvoiceButton(deleteInvoiceButton);
        archivedInvoiceListService.init(root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        invoiceList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        addChangeListenersToList();
    }

    private void addChangeListenersToList() {
        invoiceList.getSelectionModel().getSelectedItems().addListener((ListChangeListener<InvoiceViewModel>) selectedInvoices -> {
            if (selectedInvoices.getList().size() == 1) {
                printInvoiceButton.setVisible(true);
                printInvoiceButton.setManaged(true);
                deleteInvoiceButton.setVisible(true);
                deleteInvoiceButton.setManaged(true);
            } else if (selectedInvoices.getList().size() > 1) {
                printInvoiceButton.setVisible(true);
                printInvoiceButton.setManaged(true);
                deleteInvoiceButton.setVisible(true);
                deleteInvoiceButton.setManaged(true);
            } else {
                printInvoiceButton.setVisible(false);
                printInvoiceButton.setManaged(false);
                deleteInvoiceButton.setVisible(false);
                deleteInvoiceButton.setManaged(false);
            }
        });
    }

    public void printInvoice(ActionEvent actionEvent) {
        archivedInvoiceListService.printInvoiceService.restart();
    }

    public void deleteInvoice(ActionEvent actionEvent) {
        if (AlertController.areYouSure("Bent u zeker dat u deze factuur wil verwijderen?", "Bedenk dat u deze factuur nadien niet meer zal kunnen herstellen!")) {
            archivedInvoiceListService.deleteInvoiceService.restart();
        }
    }
}
