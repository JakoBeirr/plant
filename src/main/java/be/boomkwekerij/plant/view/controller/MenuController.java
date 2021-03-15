package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.view.MultipleScreenApplicationLoader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    private MenuItem customerList;
    @FXML
    private MenuItem customerCreate;
    @FXML
    private MenuItem customerModify;
    @FXML
    private MenuItem plantList;
    @FXML
    private MenuItem plantCreate;
    @FXML
    private MenuItem plantModify;
    @FXML
    private MenuItem invoiceList;
    @FXML
    private MenuItem invoiceCreate;
    @FXML
    private MenuItem invoiceModify;
    @FXML
    private MenuItem archivedInvoiceList;
    @FXML
    private MenuItem fustList;
    @FXML
    private MenuItem fustCreate;
    @FXML
    private MenuItem generalSettings;
    @FXML
    private MenuItem backup;
    @FXML
    private MenuItem rapportage;

    private MultipleScreenApplicationLoader applicationLoader;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        applicationLoader = new MultipleScreenApplicationLoader();
        initEventHandlers();
    }

    private void initEventHandlers() {
        customerList.setOnAction(changeTab("customerList"));
        customerCreate.setOnAction(changeTab("customerCreate"));
        customerModify.setOnAction(changeTab("customerModify"));
        plantList.setOnAction(changeTab("plantList"));
        plantCreate.setOnAction(changeTab("plantCreate"));
        plantModify.setOnAction(changeTab("plantModify"));
        invoiceList.setOnAction(changeTab("invoiceList"));
        invoiceCreate.setOnAction(changeTab("invoiceCreate"));
        invoiceModify.setOnAction(changeTab("invoiceModify"));
        archivedInvoiceList.setOnAction(changeTab("archivedInvoiceList"));
        fustList.setOnAction(changeTab("fustList"));
        fustCreate.setOnAction(changeTab("fustCreate"));
        generalSettings.setOnAction(changeTab("generalSettings"));
        backup.setOnAction(changeTab("backup"));
        rapportage.setOnAction(changeTab("report"));
    }

    private EventHandler<ActionEvent> changeTab(String tab) {
        return event -> {
            try {
                applicationLoader.reload(tab);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}
