package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.controller.CompanyController;
import be.boomkwekerij.plant.controller.SystemController;
import be.boomkwekerij.plant.model.dto.CompanyDTO;
import be.boomkwekerij.plant.model.dto.SystemDTO;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GeneralSettingsController implements Initializable {

    private SystemController systemController = new SystemController();
    private CompanyController companyController = new CompanyController();

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeSystemSettings();
        initializeCompanySettings();
    }

    private void initializeSystemSettings() {
        SearchResult<SystemDTO> systemSearchResult = systemController.getSystem();
        if (systemSearchResult.isSuccess()) {
            SystemDTO system = systemSearchResult.getFirst();
            if (system != null) {
                invoiceNumber.setText(system.getNextInvoiceNumber());
            }
        }
    }

    private void initializeCompanySettings() {
        SearchResult<CompanyDTO> companySearchResult = companyController.getCompany();
        if (companySearchResult.isSuccess()) {
            CompanyDTO company = companySearchResult.getFirst();
            if (company != null) {
                name.setText(company.getName());
                address.setText(company.getAddress());
                telephone.setText(company.getTelephone());
                fax.setText(company.getFax());
                gsm.setText(company.getGsm());
                accountNumberBelgium.setText(company.getAccountNumberBelgium());
                ibanBelgium.setText(company.getIbanBelgium());
                bicBelgium.setText(company.getBicBelgium());
                accountNumberNetherlands.setText(company.getAccountNumberNetherlands());
                ibanNetherlands.setText(company.getIbanNetherlands());
                bicNetherlands.setText(company.getBicNetherlands());
                btwNumber.setText(company.getBtwNumber());
            }
        }
    }

    public void modifySystem(Event event) {
        try {
            CrudsResult systemModifyResult = modifySystem();

            if (systemModifyResult.isSuccess()) {
                handleModifySystemSuccess();
            } else {
                handleModifySystemError(systemModifyResult.getMessages());
            }
        } catch (Exception e) {
            handleModifySystemException(e);
        }
    }

    private CrudsResult modifySystem() {
        SystemDTO system = new SystemDTO();
        system.setNextInvoiceNumber(invoiceNumber.getText());
        return systemController.updateSystem(system);
    }

    private void handleModifySystemSuccess() {
        initializeSystemSettings();
        AlertController.alertSuccess("Systeem bewerkt!");
    }

    private void handleModifySystemError(List<String> errorMessages) {
        StringBuilder errorBuilder = new StringBuilder("Gefaald wegens volgende fout(en): ");
        for (int i = 0; i < errorMessages.size(); i++) {
            String errorMessage = errorMessages.get(i);
            errorBuilder.append("\n").append(i+1).append(") ").append(errorMessage);
        }
        AlertController.alertError("Systeem bewerken gefaald!", errorBuilder.toString());
    }

    private void handleModifySystemException(Exception e) {
        AlertController.alertException("Systeem bewerken gefaald!", e);
    }

    public void modifyCompany(Event event) {
        try {
            CrudsResult companyModifyResult = modifyCompany();

            if (companyModifyResult.isSuccess()) {
                handleModifyCompanySuccess();
            } else {
                handleModifyCompanyError(companyModifyResult.getMessages());
            }
        } catch (Exception e) {
            handleModifyCompanyException(e);
        }
    }

    private CrudsResult modifyCompany() {
        CompanyDTO company = new CompanyDTO();
        company.setName(name.getText());
        company.setAddress(address.getText());
        company.setTelephone(telephone.getText());
        company.setFax(fax.getText());
        company.setGsm(gsm.getText());
        company.setAccountNumberBelgium(accountNumberBelgium.getText());
        company.setIbanBelgium(ibanBelgium.getText());
        company.setBicBelgium(bicBelgium.getText());
        company.setAccountNumberNetherlands(accountNumberNetherlands.getText());
        company.setIbanNetherlands(ibanNetherlands.getText());
        company.setBicNetherlands(bicNetherlands.getText());
        company.setBtwNumber(btwNumber.getText());
        return companyController.updateCompany(company);
    }

    private void handleModifyCompanySuccess() {
        initializeCompanySettings();
        AlertController.alertSuccess("Bedrijf bewerkt!");
    }

    private void handleModifyCompanyError(List<String> errorMessages) {
        StringBuilder errorBuilder = new StringBuilder("Gefaald wegens volgende fout(en): ");
        for (int i = 0; i < errorMessages.size(); i++) {
            String errorMessage = errorMessages.get(i);
            errorBuilder.append("\n").append(i+1).append(") ").append(errorMessage);
        }
        AlertController.alertError("Bedrijf bewerken gefaald!", errorBuilder.toString());
    }

    private void handleModifyCompanyException(Exception e) {
        AlertController.alertException("Bedrijf bewerken gefaald!", e);
    }
}
