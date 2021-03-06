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

            TextField name1Field = new TextField();
            fieldPane.add(new Label("Naam1*:"), 0, 0);
            fieldPane.add(name1Field, 1, 0);
            TextField name2Field = new TextField();
            fieldPane.add(new Label("Naam2*:"), 0, 1);
            fieldPane.add(name2Field, 1, 1);
            TextField addressField = new TextField();
            fieldPane.add(new Label("Adres*:"), 0, 2);
            fieldPane.add(addressField, 1, 2);
            TextField telephoneField = new TextField();
            fieldPane.add(new Label("Telefoon*:"), 0, 3);
            fieldPane.add(telephoneField, 1, 3);
            TextField faxField = new TextField();
            fieldPane.add(new Label("FAX*:"), 0, 4);
            fieldPane.add(faxField, 1, 4);
            TextField gsmField = new TextField();
            fieldPane.add(new Label("GSM*:"), 0, 5);
            fieldPane.add(gsmField, 1, 5);
            TextField accountNumberBelgiumField = new TextField();
            fieldPane.add(new Label("KBC:"), 0, 6);
            fieldPane.add(accountNumberBelgiumField, 1, 6);
            TextField ibanBelgiumField = new TextField();
            fieldPane.add(new Label("IBAN BE*:"), 0, 7);
            fieldPane.add(ibanBelgiumField, 1, 7);
            TextField bicBelgiumField = new TextField();
            fieldPane.add(new Label("BIC BE*:"), 0, 8);
            fieldPane.add(bicBelgiumField, 1, 8);
            TextField accountNumberNetherlandsField = new TextField();
            fieldPane.add(new Label("Rabobank Zundert:"), 0, 9);
            fieldPane.add(accountNumberNetherlandsField, 1, 9);
            TextField ibanNetherlandsField = new TextField();
            fieldPane.add(new Label("IBAN NL*:"), 0, 10);
            fieldPane.add(ibanNetherlandsField, 1, 10);
            TextField bicNetherlandsField = new TextField();
            fieldPane.add(new Label("BIC NL*:"), 0, 11);
            fieldPane.add(bicNetherlandsField, 1, 11);
            TextField btwNumberField = new TextField();
            fieldPane.add(new Label("BTW nummer*:"), 0, 12);
            fieldPane.add(btwNumberField, 1, 12);
            TextField emailAddressField = new TextField();
            fieldPane.add(new Label("Email-adres*:"), 0, 13);
            fieldPane.add(emailAddressField, 1, 13);

            createCompanyDialog.getDialogPane().setContent(fieldPane);

            focusField(name1Field);
            handleCompanyCreateResult(createCompanyDialog, createCompanyButton, name1Field, name2Field, addressField, telephoneField, faxField, gsmField, accountNumberBelgiumField, ibanBelgiumField, bicBelgiumField, accountNumberNetherlandsField, ibanNetherlandsField, bicNetherlandsField, btwNumberField, emailAddressField);
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
        createCompanyDialog.setTitle("Bedrijf aanmaken");
        createCompanyDialog.setHeaderText("Nog niet voldoende info aanwezig om te starten!\nGelieve eerst je bedrijfsgegevens te voorzien!");
        return createCompanyDialog;
    }

    private ButtonType createCompanyButton(Dialog<CompanyDTO> dialog) {
        ButtonType createCompanyButton = new ButtonType("Aanmaken", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createCompanyButton);
        return createCompanyButton;
    }

    private void handleCompanyCreateResult(Dialog<CompanyDTO> dialog, ButtonType createCompanyButton, TextField name1Field, TextField name2Field, TextField addressField, TextField telephoneField, TextField faxField, TextField gsmField, TextField accountNumberBelgiumField, TextField ibanBelgiumField, TextField bicBelgiumField, TextField accountNumberNetherlandsField, TextField ibanNetherlandsField, TextField bicNetherlandsField, TextField btwNumberField, TextField emailAddressField) {
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createCompanyButton) {
                CompanyDTO companyDTO = new CompanyDTO();
                companyDTO.setName1(name1Field.getText());
                companyDTO.setName2(name2Field.getText());
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
                companyDTO.setEmailAddress(emailAddressField.getText());
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
