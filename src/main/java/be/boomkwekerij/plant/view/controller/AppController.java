package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.controller.CompanyController;
import be.boomkwekerij.plant.controller.SystemController;
import be.boomkwekerij.plant.model.dto.CompanyDTO;
import be.boomkwekerij.plant.model.dto.SystemDTO;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    private SystemController systemController = new SystemController();
    private CompanyController companyController = new CompanyController();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeSystemWhenNotPresent();
        initializeCompanyWhenNotPresent();
    }

    private void initializeSystemWhenNotPresent() {
        SystemDTO system = getSystem();
        if (system == null) {
            Dialog<SystemDTO> createSystemDialog = createSystemDialog();
            ButtonType createSystemButton = createSystemButton(createSystemDialog);

            GridPane fieldPane = new GridPane();
            fieldPane.setHgap(10);
            fieldPane.setPadding(new Insets(20, 150, 10, 10));

            TextField invoiceNumberField = new TextField();
            fieldPane.add(new Label("Factuurnummer:"), 0, 0);
            fieldPane.add(invoiceNumberField, 1, 0);

            createSystemDialog.getDialogPane().setContent(fieldPane);

            focusField(invoiceNumberField);
            handleSystemCreateResult(createSystemDialog, createSystemButton, invoiceNumberField);
        }
    }

    private SystemDTO getSystem() {
        SearchResult<SystemDTO> systemSearchResult = systemController.getSystem();
        if (systemSearchResult.isSuccess()) {
            return systemSearchResult.getFirst();
        }
        return null;
    }

    private Dialog<SystemDTO> createSystemDialog() {
        Dialog<SystemDTO> createSystemDialog = new Dialog<SystemDTO>();
        createSystemDialog.initStyle(StageStyle.UNDECORATED);
        createSystemDialog.setTitle("Systeem aanmaken");
        createSystemDialog.setHeaderText("Nog niet voldoende info aanwezig om te starten!\nGelieve deze eerst te voorzien!");
        return createSystemDialog;
    }

    private ButtonType createSystemButton(Dialog<SystemDTO> dialog) {
        ButtonType createSystemButton = new ButtonType("Aanmaken", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createSystemButton);
        return createSystemButton;
    }

    private void handleSystemCreateResult(Dialog<SystemDTO> createSystemDialog, ButtonType createSystemButton, TextField invoiceNumberField) {
        createSystemDialog.setResultConverter(dialogButton -> {
            if (dialogButton == createSystemButton) {
                SystemDTO systemDTO = new SystemDTO();
                systemDTO.setNextInvoiceNumber(invoiceNumberField.getText());
                return systemDTO;
            }
            return null;
        });

        boolean createSystemSuccess = false;
        while (!createSystemSuccess) {
            Optional<SystemDTO> result = createSystemDialog.showAndWait();
            if (result.isPresent()) {
                SystemDTO systemDTO = result.get();
                if (systemDTO != null) {
                    CrudsResult systemCreateResult = systemController.createSystem(systemDTO);
                    createSystemSuccess = systemCreateResult.isSuccess();
                }
            }
        }
    }

    private void initializeCompanyWhenNotPresent() {
        CompanyDTO company = getCompany();
        if (company == null) {
            Dialog<CompanyDTO> createCompanyDialog = createCompanyDialog();
            ButtonType createCompanyButton = createCompanyButton(createCompanyDialog);

            GridPane fieldPane = new GridPane();
            fieldPane.setVgap(5);
            fieldPane.setPadding(new Insets(20, 150, 10, 10));

            TextField nameField = new TextField();
            fieldPane.add(new Label("Naam:"), 0, 0);
            fieldPane.add(nameField, 1, 0);
            TextField addressField = new TextField();
            fieldPane.add(new Label("Adres:"), 0, 1);
            fieldPane.add(addressField, 1, 1);
            TextField telephoneField = new TextField();
            fieldPane.add(new Label("Telefoon:"), 0, 2);
            fieldPane.add(telephoneField, 1, 2);
            TextField faxField = new TextField();
            fieldPane.add(new Label("FAX:"), 0, 3);
            fieldPane.add(faxField, 1, 3);
            TextField gsmField = new TextField();
            fieldPane.add(new Label("GSM:"), 0, 4);
            fieldPane.add(gsmField, 1, 4);
            TextField accountNumberBelgiumField = new TextField();
            fieldPane.add(new Label("Kaartnummer BE:"), 0, 5);
            fieldPane.add(accountNumberBelgiumField, 1, 5);
            TextField ibanBelgiumField = new TextField();
            fieldPane.add(new Label("IBAN BE:"), 0, 6);
            fieldPane.add(ibanBelgiumField, 1, 6);
            TextField bicBelgiumField = new TextField();
            fieldPane.add(new Label("BIC BE:"), 0, 7);
            fieldPane.add(bicBelgiumField, 1, 7);
            TextField accountNumberNetherlandsField = new TextField();
            fieldPane.add(new Label("Kaartnummer NL:"), 0, 8);
            fieldPane.add(accountNumberNetherlandsField, 1, 8);
            TextField ibanNetherlandsField = new TextField();
            fieldPane.add(new Label("IBAN NL:"), 0, 9);
            fieldPane.add(ibanNetherlandsField, 1, 9);
            TextField bicNetherlandsField = new TextField();
            fieldPane.add(new Label("BIC BE:"), 0, 10);
            fieldPane.add(bicNetherlandsField, 1, 10);
            TextField btwNumberField = new TextField();
            fieldPane.add(new Label("BTW nummer:"), 0, 11);
            fieldPane.add(btwNumberField, 1, 11);

            createCompanyDialog.getDialogPane().setContent(fieldPane);

            focusField(nameField);
            handleCompanyCreateResult(createCompanyDialog, createCompanyButton, nameField, addressField, telephoneField, faxField, gsmField, accountNumberBelgiumField, ibanBelgiumField, bicBelgiumField, accountNumberNetherlandsField, ibanNetherlandsField, bicNetherlandsField, btwNumberField);
        }
    }

    private CompanyDTO getCompany() {
        SearchResult<CompanyDTO> companySearchResult = companyController.getCompany();
        if (companySearchResult.isSuccess()) {
            return companySearchResult.getFirst();
        }
        return null;
    }

    private Dialog<CompanyDTO> createCompanyDialog() {
        Dialog<CompanyDTO> createCompanyDialog = new Dialog<CompanyDTO>();
        createCompanyDialog.initStyle(StageStyle.UNDECORATED);
        createCompanyDialog.setTitle("Bedrijf aanmaken");
        createCompanyDialog.setHeaderText("Nog niet voldoende info aanwezig om te starten!\nGelieve eerst je bedrijfsgegevens te voorzien!");
        return createCompanyDialog;
    }

    private ButtonType createCompanyButton(Dialog<CompanyDTO> dialog) {
        ButtonType createCompanyButton = new ButtonType("Aanmaken", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createCompanyButton);
        return createCompanyButton;
    }

    private void handleCompanyCreateResult(Dialog<CompanyDTO> dialog, ButtonType createCompanyButton, TextField nameField, TextField addressField, TextField telephoneField, TextField faxField, TextField gsmField, TextField accountNumberBelgiumField, TextField ibanBelgiumField, TextField bicBelgiumField, TextField accountNumberNetherlandsField, TextField ibanNetherlandsField, TextField bicNetherlandsField, TextField btwNumberField) {
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createCompanyButton) {
                CompanyDTO companyDTO = new CompanyDTO();
                companyDTO.setName(nameField.getText());
                companyDTO.setAddress(addressField.getText());
                companyDTO.setTelephone(telephoneField.getText());
                companyDTO.setFax(faxField.getText());
                companyDTO.setGsm(gsmField.getText());
                companyDTO.setAccountNumberBelgium(accountNumberBelgiumField.getText());
                companyDTO.setIbanBelgium(ibanBelgiumField.getText());
                companyDTO.setBicBelgium(bicBelgiumField.getText());
                companyDTO.setAccountNumberNetherlands(accountNumberNetherlandsField.getText());
                companyDTO.setIbanNetherlands(ibanNetherlandsField.getText());
                companyDTO.setBicNetherlands(bicNetherlandsField.getText());
                companyDTO.setBtwNumber(btwNumberField.getText());
                return companyDTO;
            }
            return null;
        });

        boolean companyCreateSuccess = false;
        while (!companyCreateSuccess) {
            Optional<CompanyDTO> result = dialog.showAndWait();
            if (result.isPresent()) {
                CompanyDTO companyDTO = result.get();
                if (companyDTO != null) {
                    CrudsResult companyCreateResult = companyController.createCompany(companyDTO);
                    companyCreateSuccess = companyCreateResult.isSuccess();
                }
            }
        }
    }

    private void focusField(TextField textField) {
        Platform.runLater(textField::requestFocus);
    }
}
