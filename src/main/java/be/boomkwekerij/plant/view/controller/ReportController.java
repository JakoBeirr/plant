package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.controller.ReportingController;
import be.boomkwekerij.plant.util.CrudsResult;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ReportController implements Initializable {

    private ReportingController reportingController = new ReportingController();

    @FXML
    private Button customerFileButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void createCustomerFile(Event event) {
        CrudsResult crudsResult = reportingController.printCustomerFileReport();
        if (crudsResult.isSuccess()) {
            AlertController.alertSuccess("Rapport aangemaakt");
        } else {
            AlertController.alertError("Rapport aanmaken gefaald", Arrays.toString(crudsResult.getMessages().toArray()));
        }
    }
}
