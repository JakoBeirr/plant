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
    private MenuItem companyDetails;
    @FXML
    private MenuItem systemDetails;

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
        companyDetails.setOnAction(changeTab("companyDetails"));
        systemDetails.setOnAction(changeTab("systemDetails"));
    }

    private EventHandler<ActionEvent> changeTab(String tab) {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    applicationLoader.reload(tab);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
