package be.boomkwekerij.plant.view.services;

import be.boomkwekerij.plant.controller.CompanyController;
import be.boomkwekerij.plant.controller.SystemController;
import be.boomkwekerij.plant.model.dto.CompanyDTO;
import be.boomkwekerij.plant.model.dto.SystemDTO;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.util.Arrays;

public class GeneralSettingsService {

    private SystemController systemController = new SystemController();
    private CompanyController companyController = new CompanyController();

    private TextField invoiceNumber;
    private Button modifySystemButton;
    private TextField name1;
    private TextField name2;
    private TextField address;
    private TextField telephone;
    private TextField fax;
    private TextField gsm;
    private TextField accountNumberBelgium;
    private TextField ibanBelgium;
    private TextField bicBelgium;
    private TextField accountNumberNetherlands;
    private TextField ibanNetherlands;
    private TextField bicNetherlands;
    private TextField btwNumber;
    private TextField email;
    private Button modifyCompanyButton;

    public final Service initializeSystemService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Initializeren systeem");

                    SearchResult<SystemDTO> searchResult = systemController.getSystem();
                    if (searchResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(searchResult.getMessages().toArray()));
                    }

                    SystemDTO system = searchResult.getFirst();
                    if (system != null) {
                        invoiceNumber.setText(system.getNextInvoiceNumber());
                    }

                    return null;
                }
            };
        }
    };

    public final Service initializeCompanyService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Initializeren bedrijf");

                    SearchResult<CompanyDTO> searchResult = companyController.getCompany();
                    if (searchResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(searchResult.getMessages().toArray()));
                    }

                    CompanyDTO company = searchResult.getFirst();
                    if (company != null) {
                        name1.setText(company.getName1());
                        name2.setText(company.getName2());
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
                        email.setText(company.getEmailAddress());
                    }

                    return null;
                }
            };
        }
    };

    public final Service modifySystemService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Bewerken systeem");

                    SystemDTO system = new SystemDTO();
                    system.setNextInvoiceNumber(invoiceNumber.getText());

                    CrudsResult modifyResult = systemController.updateSystem(system);
                    if (modifyResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(modifyResult.getMessages().toArray()));
                    }

                    return null;
                }
            };
        }
    };

    public final Service modifyCompanyService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Bewerken bedrijf");

                    CompanyDTO company = new CompanyDTO();
                    company.setName1(name1.getText());
                    company.setName2(name2.getText());
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
                    company.setEmailAddress(email.getText());
                    CrudsResult modifyResult = companyController.updateCompany(company);
                    if (modifyResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(modifyResult.getMessages().toArray()));
                    }

                    return null;
                }
            };
        }
    };

    public void setInvoiceNumber(TextField invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public void setModifySystemButton(Button modifySystemButton) {
        this.modifySystemButton = modifySystemButton;
    }

    public void setName1(TextField name1) {
        this.name1 = name1;
    }

    public void setName2(TextField name2) {
        this.name2 = name2;
    }

    public void setAddress(TextField address) {
        this.address = address;
    }

    public void setTelephone(TextField telephone) {
        this.telephone = telephone;
    }

    public void setFax(TextField fax) {
        this.fax = fax;
    }

    public void setGsm(TextField gsm) {
        this.gsm = gsm;
    }

    public void setAccountNumberBelgium(TextField accountNumberBelgium) {
        this.accountNumberBelgium = accountNumberBelgium;
    }

    public void setIbanBelgium(TextField ibanBelgium) {
        this.ibanBelgium = ibanBelgium;
    }

    public void setBicBelgium(TextField bicBelgium) {
        this.bicBelgium = bicBelgium;
    }

    public void setAccountNumberNetherlands(TextField accountNumberNetherlands) {
        this.accountNumberNetherlands = accountNumberNetherlands;
    }

    public void setIbanNetherlands(TextField ibanNetherlands) {
        this.ibanNetherlands = ibanNetherlands;
    }

    public void setBicNetherlands(TextField bicNetherlands) {
        this.bicNetherlands = bicNetherlands;
    }

    public void setBtwNumber(TextField btwNumber) {
        this.btwNumber = btwNumber;
    }

    public void setEmail(TextField email) {
        this.email = email;
    }

    public void setModifyCompanyButton(Button modifyCompanyButton) {
        this.modifyCompanyButton = modifyCompanyButton;
    }

    public void init(Pane root) {
        root.cursorProperty()
                .bind(Bindings.when(initializeSystemService.runningProperty()
                            .or(initializeCompanyService.runningProperty()))
                        .then(Cursor.WAIT)
                        .otherwise(Cursor.DEFAULT)
                );
        modifySystemButton.disableProperty()
                .bind(modifySystemService.runningProperty());
        modifyCompanyButton.disableProperty()
                .bind(modifyCompanyService.runningProperty());

        modifySystemService.setOnSucceeded(serviceEvent -> {
            initializeSystemService.restart();
            ServiceHandler.success(modifySystemService);
        });
        modifySystemService.setOnFailed(serviceEvent -> {
            ServiceHandler.error(modifySystemService);
        });
        modifyCompanyService.setOnSucceeded(serviceEvent -> {
            initializeCompanyService.restart();
            ServiceHandler.success(modifyCompanyService);
        });
        modifyCompanyService.setOnFailed(serviceEvent -> {
            ServiceHandler.error(modifyCompanyService);
        });

        initializeSystemService.restart();
        initializeCompanyService.restart();
    }
}
