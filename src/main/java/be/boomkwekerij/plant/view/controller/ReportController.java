package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.controller.ReportingController;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.Month;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import org.joda.time.DateTime;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ReportController implements Initializable {

    private static final String NON_NUMERIC_CHARACTERS = "[^\\d]";

    private ReportingController reportingController = new ReportingController();

    @FXML
    private Button customerFileButton;
    @FXML
    private Button unpayedInvoicesButton;
    @FXML
    private Button invoicesButton;
    @FXML
    private ComboBox<String> months;
    @FXML
    private TextField year;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeYearField();
        initializeMonthList();
    }

    private void initializeYearField() {
        year.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                year.setText(newValue.replaceAll(NON_NUMERIC_CHARACTERS, ""));
            }
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
        try {
            CrudsResult crudsResult = reportingController.printCustomerFileReport();
            if (crudsResult.isSuccess()) {
                AlertController.alertSuccess("Klantenbestand rapport aangemaakt");
            } else {
                AlertController.alertError("Rapport aanmaken gefaald", Arrays.toString(crudsResult.getMessages().toArray()));
            }
        } catch (Exception e) {
            AlertController.alertException("Rapport aanmaken gefaald", e);
        }
    }

    public void createUnpayedInvoices(Event event) {
        try {
            CrudsResult crudsResult = reportingController.printUnpayedInvoicesReport();
            if (crudsResult.isSuccess()) {
                AlertController.alertSuccess("Onbetaalde facturen rapport aangemaakt");
            } else {
                AlertController.alertError("Rapport aanmaken gefaald", Arrays.toString(crudsResult.getMessages().toArray()));
            }
        } catch (Exception e) {
            AlertController.alertException("Rapport aanmaken gefaald", e);
        }
    }

    public void createInvoices(Event event) {
        try {
            String selectedMonth = months.getSelectionModel().getSelectedItem();
            int selectedYear = Integer.parseInt(year.getText());

            CrudsResult crudsResult = reportingController.printInvoicesReport(selectedMonth, selectedYear);
            if (crudsResult.isSuccess()) {
                AlertController.alertSuccess("Alle facturen rapport aangemaakt");
            } else {
                AlertController.alertError("Rapport aanmaken gefaald", Arrays.toString(crudsResult.getMessages().toArray()));
            }
        } catch (Exception e) {
            AlertController.alertException("Rapport aanmaken gefaald", e);
        }
    }
}
