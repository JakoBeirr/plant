package be.boomkwekerij.plant.view.services;

import be.boomkwekerij.plant.controller.InvoiceController;
import be.boomkwekerij.plant.model.dto.DateDTO;
import be.boomkwekerij.plant.model.dto.InvoiceDTO;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;
import be.boomkwekerij.plant.view.mapper.InvoiceViewMapper;
import be.boomkwekerij.plant.view.model.InvoiceViewModel;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InvoiceListService {

    private InvoiceController invoiceController = new InvoiceController();

    private InvoiceViewMapper invoiceViewMapper = new InvoiceViewMapper();

    private TextField invoiceSearchField;
    private TableView<InvoiceViewModel> invoiceList;
    private Button payInvoiceButton;
    private Button unPayInvoiceButton;
    private Button printInvoiceButton;
    private Button printSellingConditionsButton;
    private Button deleteInvoiceButton;

    private DateDTO payDate = null;

    public final Service loadAllInvoicesService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Inladen alle facturen");

                    SearchResult<InvoiceDTO> searchResult = invoiceController.getAllInvoices();
                    if (searchResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(searchResult.getMessages().toArray()));
                    }

                    List<InvoiceViewModel> invoices = new ArrayList<>();
                    for (InvoiceDTO invoiceDTO : searchResult.getResults()) {
                        InvoiceViewModel invoiceViewModel = invoiceViewMapper.mapDTOToViewModel(invoiceDTO);
                        invoices.add(invoiceViewModel);
                    }
                    invoiceList.getItems().setAll(invoices);

                    return null;
                }
            };
        }
    };

    public final Service loadAllInvoicesWithNumberService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Inladen alle facturen met nummer");

                    String invoiceNumber = invoiceSearchField.getText();
                    SearchResult<InvoiceDTO> searchResult = invoiceController.getAllInvoicesWithInvoiceNumber(invoiceNumber);
                    if (searchResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(searchResult.getMessages().toArray()));
                    }

                    List<InvoiceViewModel> invoices = new ArrayList<>();
                    for (InvoiceDTO invoiceDTO : searchResult.getResults()) {
                        InvoiceViewModel invoiceViewModel = invoiceViewMapper.mapDTOToViewModel(invoiceDTO);
                        invoices.add(invoiceViewModel);
                    }
                    invoiceList.getItems().setAll(invoices);

                    return null;
                }
            };
        }
    };

    public final Service payInvoiceService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Factuur betalen");

                    if (payDate == null) {
                        throw new IllegalArgumentException("Datum van betalen niet ingevuld");
                    }
                    InvoiceViewModel selectedInvoice = invoiceList.getSelectionModel().getSelectedItem();
                    CrudsResult payInvoiceResult = invoiceController.payInvoice(selectedInvoice.getId(), payDate);
                    if (payInvoiceResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(payInvoiceResult.getMessages().toArray()));
                    }

                    return null;
                }
            };
        }
    };

    public final Service unPayInvoiceService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Factuur betaalgegevens wissen");

                    InvoiceViewModel selectedInvoice = invoiceList.getSelectionModel().getSelectedItem();
                    CrudsResult unPayInvoiceResult = invoiceController.unPayInvoice(selectedInvoice.getId());
                    if (unPayInvoiceResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(unPayInvoiceResult.getMessages().toArray()));
                    }

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
                protected Void call() throws Exception {
                    updateTitle("Factuur printen");

                    InvoiceViewModel selectedInvoice = invoiceList.getSelectionModel().getSelectedItem();
                    CrudsResult printResult = invoiceController.printInvoiceDocument(selectedInvoice.getId());
                    if (printResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(printResult.getMessages().toArray()));
                    }

                    return null;
                }
            };
        }
    };

    public final Service printSellingConditionsService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Algemene voorwaarden printen");

                    CrudsResult printResult = invoiceController.printSellingConditions();
                    if (printResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(printResult.getMessages().toArray()));
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
                protected Void call() throws Exception {
                    updateTitle("Factuur verwijderen");

                    InvoiceViewModel selectedInvoice = invoiceList.getSelectionModel().getSelectedItem();
                    CrudsResult deleteResult = invoiceController.deleteInvoice(selectedInvoice.getId());
                    if (deleteResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(deleteResult.getMessages().toArray()));
                    }

                    return null;
                }
            };
        }
    };

    public void setInvoiceSearchField(TextField invoiceSearchField) {
        this.invoiceSearchField = invoiceSearchField;
    }

    public void setInvoiceList(TableView<InvoiceViewModel> invoiceList) {
        this.invoiceList = invoiceList;
    }

    public void setPayInvoiceButton(Button payInvoiceButton) {
        this.payInvoiceButton = payInvoiceButton;
    }

    public void setUnPayInvoiceButton(Button unPayInvoiceButton) {
        this.unPayInvoiceButton = unPayInvoiceButton;
    }

    public void setPrintInvoiceButton(Button printInvoiceButton) {
        this.printInvoiceButton = printInvoiceButton;
    }

    public void setPrintSellingConditionsButton(Button printSellingConditionsButton) {
        this.printSellingConditionsButton = printSellingConditionsButton;
    }

    public void setDeleteInvoiceButton(Button deleteInvoiceButton) {
        this.deleteInvoiceButton = deleteInvoiceButton;
    }

    public void setPayDate(DateDTO payDate) {
        this.payDate = payDate;
    }

    public void init(Pane root) {
        root.cursorProperty()
                .bind(Bindings.when(loadAllInvoicesService.runningProperty()
                            .or(loadAllInvoicesWithNumberService.runningProperty()
                            .or(payInvoiceService.runningProperty()
                            .or(unPayInvoiceService.runningProperty()
                            .or(printInvoiceService.runningProperty()
                            .or(printSellingConditionsService.runningProperty()
                            .or(deleteInvoiceService.runningProperty())))))))
                        .then(Cursor.WAIT)
                        .otherwise(Cursor.DEFAULT)
                );
        payInvoiceButton.disableProperty()
                .bind(payInvoiceService.runningProperty());
        unPayInvoiceButton.disableProperty()
                .bind(unPayInvoiceService.runningProperty());
        printInvoiceButton.disableProperty()
                .bind(printInvoiceService.runningProperty());
        printSellingConditionsButton.disableProperty()
                .bind(printSellingConditionsService.runningProperty());
        deleteInvoiceButton.disableProperty()
                .bind(deleteInvoiceService.runningProperty());

        payInvoiceService.setOnSucceeded(serviceEvent -> {
            loadAllInvoicesService.restart();
            ServiceHandler.success(payInvoiceService);
        });
        payInvoiceService.setOnFailed(serviceEvent -> {
            ServiceHandler.error(payInvoiceService);
        });
        unPayInvoiceService.setOnSucceeded(serviceEvent -> {
            loadAllInvoicesService.restart();
            ServiceHandler.success(unPayInvoiceService);
        });
        unPayInvoiceService.setOnFailed(serviceEvent -> {
            ServiceHandler.error(unPayInvoiceService);
        });
        printInvoiceService.setOnSucceeded(serviceEvent -> {
            ServiceHandler.success(printInvoiceService);
        });
        printInvoiceService.setOnFailed(serviceEvent -> {
            ServiceHandler.error(printInvoiceService);
        });
        printSellingConditionsService.setOnSucceeded(serviceEvent -> {
            ServiceHandler.success(printSellingConditionsService);
        });
        printSellingConditionsService.setOnFailed(serviceEvent -> {
            ServiceHandler.error(printSellingConditionsService);
        });
        deleteInvoiceService.setOnSucceeded(serviceEvent -> {
            loadAllInvoicesService.restart();
            ServiceHandler.success(deleteInvoiceService);
        });
        deleteInvoiceService.setOnFailed(serviceEvent -> {
            ServiceHandler.error(deleteInvoiceService);
        });
    }
}
