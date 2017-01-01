package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.view.services.GeneralSettingsService;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class GeneralSettingsController implements PageController {

    private GeneralSettingsService generalSettingsService = new GeneralSettingsService();

    @FXML
    private TextField invoiceNumber;
    @FXML
    private TextField name;
    @FXML
    private TextField address;
    @FXML
    private TextField telephone;
    @FXML
    private TextField fax;
    @FXML
    private TextField gsm;
    @FXML
    private TextField accountNumberBelgium;
    @FXML
    private TextField ibanBelgium;
    @FXML
    private TextField bicBelgium;
    @FXML
    private TextField accountNumberNetherlands;
    @FXML
    private TextField ibanNetherlands;
    @FXML
    private TextField bicNetherlands;
    @FXML
    private TextField btwNumber;
    @FXML
    private Button modifySystemButton;
    @FXML
    private Button modifyCompanyButton;

    @Override
    public void init(Pane root) {
        generalSettingsService.setInvoiceNumber(invoiceNumber);
        generalSettingsService.setName(name);
        generalSettingsService.setAddress(address);
        generalSettingsService.setTelephone(telephone);
        generalSettingsService.setFax(fax);
        generalSettingsService.setGsm(gsm);
        generalSettingsService.setAccountNumberBelgium(accountNumberBelgium);
        generalSettingsService.setIbanBelgium(ibanBelgium);
        generalSettingsService.setBicBelgium(bicBelgium);
        generalSettingsService.setAccountNumberNetherlands(accountNumberNetherlands);
        generalSettingsService.setIbanNetherlands(ibanNetherlands);
        generalSettingsService.setBicNetherlands(bicNetherlands);
        generalSettingsService.setBtwNumber(btwNumber);
        generalSettingsService.setModifySystemButton(modifySystemButton);
        generalSettingsService.setModifyCompanyButton(modifyCompanyButton);
        generalSettingsService.init(root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generalSettingsService.initializeSystemService.restart();
        generalSettingsService.initializeCompanyService.restart();
    }

    public void modifySystem(Event event) {
        generalSettingsService.modifySystemService.restart();
    }

    public void modifyCompany(Event event) {
        generalSettingsService.modifyCompanyService.restart();
    }
}
