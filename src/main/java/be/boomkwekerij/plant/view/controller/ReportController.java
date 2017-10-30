package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.model.dto.DateDTO;
import be.boomkwekerij.plant.util.Month;
import be.boomkwekerij.plant.view.services.ReportService;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.joda.time.DateTime;

import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
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
    private Button fustsButton;
    @FXML
    private ComboBox<String> months;
    @FXML
    private TextField year;

    @Override
    public void init(Pane root) {
        reportService.setCustomerFileButton(customerFileButton);
        reportService.setUnpayedInvoicesButton(unpayedInvoicesButton);
        reportService.setAllInvoicesButton(allInvoicesButton);
        reportService.setFustsButton(fustsButton);
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

    public void createFustsReport(Event event) {
        Dialog<DateDTO> fustReportDialog = new Dialog<DateDTO>();
        fustReportDialog.setTitle("Fust rapport");
        fustReportDialog.setHeaderText("Print fust tot en met datum:");

        GridPane fieldPane = new GridPane();
        fieldPane.setHgap(10);
        fieldPane.setPadding(new Insets(20, 150, 10, 10));
        DatePicker fustReportDatePicker = new DatePicker();
        fieldPane.add(fustReportDatePicker, 1, 0);
        fustReportDialog.getDialogPane().setContent(fieldPane);

        ButtonType printButton = new ButtonType("Print", ButtonBar.ButtonData.OK_DONE);
        fustReportDialog.getDialogPane().getButtonTypes().addAll(printButton);

        fustReportDialog.setResultConverter(dialogButton -> {
            if (dialogButton == printButton) {
                LocalDate fustReportDate = fustReportDatePicker.getValue();

                if (fustReportDate != null) {
                    DateDTO dateDTO = new DateDTO();
                    dateDTO.setDate(new DateTime(fustReportDate.getYear(), fustReportDate.getMonthValue(), fustReportDate.getDayOfMonth(), 0, 0, 0, 0));
                    return dateDTO;
                }
            }
            return null;
        });

        Optional<DateDTO> fustReportDateResult = fustReportDialog.showAndWait();
        if (fustReportDateResult.isPresent()) {
            DateDTO dateDTO = fustReportDateResult.get();

            reportService.setFustReportDate(dateDTO);
            reportService.fustsReportService.restart();
        }
    }
}
