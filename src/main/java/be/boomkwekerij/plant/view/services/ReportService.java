package be.boomkwekerij.plant.view.services;

import be.boomkwekerij.plant.controller.FustController;
import be.boomkwekerij.plant.controller.ReportingController;
import be.boomkwekerij.plant.model.dto.DateDTO;
import be.boomkwekerij.plant.util.CrudsResult;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.util.Arrays;

public class ReportService {

    private ReportingController reportingController = new ReportingController();
    private FustController fustController = new FustController();

    private Button customerFileButton;
    private Button unpayedInvoicesButton;
    private Button allInvoicesButton;
    private Button fustsButton;
    private ComboBox<String> months;
    private TextField year;

    private DateDTO fustReportDate = null;

    public final Service customerFileService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Aanmaken klantenbestand");

                    CrudsResult printResult = reportingController.printCustomerFileReport();
                    if (printResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(printResult.getMessages().toArray()));
                    }

                    return null;
                }
            };
        }
    };

    public final Service unpayedInvoiceService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Aanmaken rapport onbetaalde facturen");

                    CrudsResult printResult = reportingController.printUnpayedInvoicesReport();
                    if (printResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(printResult.getMessages().toArray()));
                    }

                    return null;
                }
            };
        }
    };

    public final Service allInvoiceService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Aanmaken rapport alle facturen");

                    String selectedMonth = months.getSelectionModel().getSelectedItem();
                    int selectedYear = Integer.parseInt(year.getText());

                    CrudsResult printResult = reportingController.printInvoicesReport(selectedMonth, selectedYear);
                    if (printResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(printResult.getMessages().toArray()));
                    }

                    return null;
                }
            };
        }
    };

    public final Service fustsReportService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Totaaloverzicht fust printen");

                    if (fustReportDate == null) {
                        throw new IllegalArgumentException("Datum van rapport niet ingevuld");
                    }

                    CrudsResult printResult = fustController.printFustFromAllCustomersReport(fustReportDate);
                    if (printResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(printResult.getMessages().toArray()));
                    }

                    return null;
                }
            };
        }
    };

    public void setCustomerFileButton(Button customerFileButton) {
        this.customerFileButton = customerFileButton;
    }

    public void setUnpayedInvoicesButton(Button unpayedInvoicesButton) {
        this.unpayedInvoicesButton = unpayedInvoicesButton;
    }

    public void setAllInvoicesButton(Button allInvoicesButton) {
        this.allInvoicesButton = allInvoicesButton;
    }

    public void setFustsButton(Button fustsButton) {
        this.fustsButton = fustsButton;
    }

    public void setMonths(ComboBox<String> months) {
        this.months = months;
    }

    public void setYear(TextField year) {
        this.year = year;
    }

    public void setFustReportDate(DateDTO fustReportDate) {
        this.fustReportDate = fustReportDate;
    }

    public void init(Pane root) {
        root.cursorProperty()
                .bind(Bindings.when(customerFileService.runningProperty()
                            .or(unpayedInvoiceService.runningProperty()
                            .or(allInvoiceService.runningProperty()
                            .or(fustsReportService.runningProperty()))))
                        .then(Cursor.WAIT)
                        .otherwise(Cursor.DEFAULT)
                );
        customerFileButton.disableProperty()
                .bind(customerFileService.runningProperty());
        unpayedInvoicesButton.disableProperty()
                .bind(unpayedInvoiceService.runningProperty());
        allInvoicesButton.disableProperty()
                .bind(allInvoiceService.runningProperty());
        fustsButton.disableProperty()
                .bind(fustsReportService.runningProperty());

        customerFileService.setOnSucceeded(serviceEvent -> {
            ServiceHandler.success(customerFileService);
        });
        customerFileService.setOnFailed(serviceEvent -> {
            ServiceHandler.error(customerFileService);
        });
        unpayedInvoiceService.setOnSucceeded(serviceEvent -> {
            ServiceHandler.success(unpayedInvoiceService);
        });
        unpayedInvoiceService.setOnFailed(serviceEvent -> {
            ServiceHandler.error(unpayedInvoiceService);
        });
        allInvoiceService.setOnSucceeded(serviceEvent -> {
            ServiceHandler.success(allInvoiceService);
        });
        allInvoiceService.setOnFailed(serviceEvent -> {
            ServiceHandler.error(allInvoiceService);
        });
        fustsReportService.setOnSucceeded(serviceEvent -> {
            ServiceHandler.success(fustsReportService);
        });
        fustsReportService.setOnFailed(serviceEvent -> {
            ServiceHandler.error(fustsReportService);
        });
    }
}
