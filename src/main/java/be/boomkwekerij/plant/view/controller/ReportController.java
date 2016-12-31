package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.util.Month;
import be.boomkwekerij.plant.view.services.ReportService;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import org.joda.time.DateTime;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportController implements PageController {

    private static final String NON_NUMERIC_CHARACTERS = "[^\\d]";

    private ReportService reportService = new ReportService();

    @FXML
    private Button customerFileButton;
    @FXML
    private Button unpayedInvoicesButton;
    @FXML
    private Button allInvoicesButton;
    @FXML
    private ComboBox<String> months;
    @FXML
    private TextField year;

    @Override
    public void init(Pane root) {
        reportService.setCustomerFileButton(customerFileButton);
        reportService.setUnpayedInvoicesButton(unpayedInvoicesButton);
        reportService.setAllInvoicesButton(allInvoicesButton);
        reportService.setMonths(months);
        reportService.setYear(year);
        reportService.init(root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeYearField();
        initializeMonthList();
    }

    private void initializeYearField() {
        year.textProperty().addListener((observable, oldValue, newValue) -> {
            year.setText(newValue.replaceAll(NON_NUMERIC_CHARACTERS, ""));
        });
        year.setText(Integer.toString(new DateTime().getYear()));
    }

    private void initializeMonthList() {
        for (Month month : Month.values()) {
            months.getItems().add(month.translation());
        }
        months.getSelectionModel().select(new DateTime().getMonthOfYear() - 1);
    }

    public void createCustomerFile(Event event) {
        reportService.customerFileService.restart();
    }

    public void createUnpayedInvoices(Event event) {
        reportService.unpayedInvoiceService.restart();
    }

    public void createInvoices(Event event) {
        reportService.allInvoiceService.restart();
    }
}
