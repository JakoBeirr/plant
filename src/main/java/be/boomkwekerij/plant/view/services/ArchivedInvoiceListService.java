package be.boomkwekerij.plant.view.services;

import be.boomkwekerij.plant.controller.ArchivedInvoiceController;
import be.boomkwekerij.plant.model.dto.InvoiceDTO;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;
import be.boomkwekerij.plant.view.mapper.InvoiceViewMapper;
import be.boomkwekerij.plant.view.model.InvoiceViewModel;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArchivedInvoiceListService {

    private final ArchivedInvoiceController archivedInvoiceController = new ArchivedInvoiceController();

    private final InvoiceViewMapper invoiceViewMapper = new InvoiceViewMapper();

    private TableView<InvoiceViewModel> invoiceList;
    private Button printInvoiceButton;
    private Button deleteInvoiceButton;

    public final Service loadAllInvoicesService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Inladen alle facturen");

                    SearchResult<InvoiceDTO> searchResult = archivedInvoiceController.getAllInvoices();
                    if (searchResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(searchResult.getMessages().toArray()));
                    }

                    List<InvoiceViewModel> invoices = new ArrayList<>();
                    for (InvoiceDTO invoiceDTO : searchResult.getResults()) {
                        InvoiceViewModel invoiceViewModel = invoiceViewMapper.mapDTOToViewModel(invoiceDTO);
                        invoices.add(invoiceViewModel);
                    }

                    Platform.runLater(() -> invoiceList.getItems().setAll(invoices));

                    return null;
                }
            };
        }
    };

    public final Service printInvoiceService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() {
                    updateTitle("Factuur printen");

                    ObservableList<InvoiceViewModel> selectedInvoices = invoiceList.getSelectionModel().getSelectedItems();
                    for (InvoiceViewModel selectedInvoice : selectedInvoices) {
                        CrudsResult printResult = archivedInvoiceController.printInvoiceDocument(selectedInvoice.getId());
                        if (printResult.isError()) {
                            throw new IllegalArgumentException(Arrays.toString(printResult.getMessages().toArray()));
                        }
                    }

                    return null;
                }
            };
        }
    };

    public final Service deleteInvoiceService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() {
                    updateTitle("Factuur verwijderen");

                    ObservableList<InvoiceViewModel> selectedInvoices = invoiceList.getSelectionModel().getSelectedItems();
                    for (InvoiceViewModel selectedInvoice : selectedInvoices) {
                        CrudsResult deleteResult = archivedInvoiceController.deleteInvoice(selectedInvoice.getId());
                        if (deleteResult.isError()) {
                            throw new IllegalArgumentException(Arrays.toString(deleteResult.getMessages().toArray()));
                        }
                    }

                    return null;
                }
            };
        }
    };

    public void setInvoiceList(TableView<InvoiceViewModel> invoiceList) {
        this.invoiceList = invoiceList;
    }

    public void setPrintInvoiceButton(Button printInvoiceButton) {
        this.printInvoiceButton = printInvoiceButton;
    }

    public void setDeleteInvoiceButton(Button deleteInvoiceButton) {
        this.deleteInvoiceButton = deleteInvoiceButton;
    }

    public void init(Pane root) {
        root.cursorProperty()
                .bind(Bindings.when(loadAllInvoicesService.runningProperty()
                        .or(printInvoiceService.runningProperty()
                                .or(deleteInvoiceService.runningProperty())))
                        .then(Cursor.WAIT)
                        .otherwise(Cursor.DEFAULT)
                );
        printInvoiceButton.disableProperty()
                .bind(printInvoiceService.runningProperty());
        deleteInvoiceButton.disableProperty()
                .bind(deleteInvoiceService.runningProperty());

        printInvoiceService.setOnSucceeded(serviceEvent -> ServiceHandler.success(printInvoiceService));
        printInvoiceService.setOnFailed(serviceEvent -> ServiceHandler.error(printInvoiceService));
        deleteInvoiceService.setOnSucceeded(serviceEvent -> {
            loadAllInvoicesService.restart();

            ServiceHandler.success(deleteInvoiceService);
        });
        deleteInvoiceService.setOnFailed(serviceEvent -> ServiceHandler.error(deleteInvoiceService));

        loadAllInvoicesService.restart();
    }
}
