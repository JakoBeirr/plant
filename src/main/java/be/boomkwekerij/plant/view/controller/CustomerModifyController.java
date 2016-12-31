package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.view.model.CustomerViewModel;
import be.boomkwekerij.plant.view.services.CustomerModifyService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerModifyController implements PageController {

    private CustomerModifyService customerModifyService = new CustomerModifyService();

    @FXML
    private TextField customerSearchField;
    @FXML
    private TableView<CustomerViewModel> customerList;
    @FXML
    private TableColumn<CustomerViewModel, String> name1;
    @FXML
    private TableColumn<CustomerViewModel, String> name2;
    @FXML
    private TableColumn<CustomerViewModel, String> address1;
    @FXML
    private TableColumn<CustomerViewModel, String> postalCode;
    @FXML
    private TableColumn<CustomerViewModel, String> residence;
    @FXML
    private TableColumn<CustomerViewModel, String> country;
    @FXML
    private TableColumn<CustomerViewModel, String> telephone;
    @FXML
    private TableColumn<CustomerViewModel, String> btwNumber;
    @FXML
    private Button showModifyButton;
    @FXML
    private GridPane modifyPane;
    @FXML
    private TextField name1Field;
    @FXML
    private TextField name2Field;
    @FXML
    private TextField address1Field;
    @FXML
    private TextField address2Field;
    @FXML
    private TextField postalCodeField;
    @FXML
    private TextField residenceField;
    @FXML
    private TextField countryField;
    @FXML
    private TextField telephoneField;
    @FXML
    private TextField gsmField;
    @FXML
    private TextField faxField;
    @FXML
    private TextField btwNumberField;
    @FXML
    private TextField emailAddressField;
    @FXML
    private Button customerModifyButton;

    @Override
    public void init(Pane root) {
        customerModifyService.setCustomerSearchField(customerSearchField);
        customerModifyService.setCustomerList(customerList);
        customerModifyService.setModifyPane(modifyPane);
        customerModifyService.setName1(name1Field);
        customerModifyService.setName2(name2Field);
        customerModifyService.setAddress1(address1Field);
        customerModifyService.setAddress2(address2Field);
        customerModifyService.setPostalCode(postalCodeField);
        customerModifyService.setResidence(residenceField);
        customerModifyService.setCountry(countryField);
        customerModifyService.setTelephone(telephoneField);
        customerModifyService.setGsm(gsmField);
        customerModifyService.setFax(faxField);
        customerModifyService.setBtwNumber(btwNumberField);
        customerModifyService.setEmailAddress(emailAddressField);
        customerModifyService.setCustomerModifyButton(customerModifyButton);
        customerModifyService.init(root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addChangeListenerToCustomerSearchField();
        addChangeListenerToCustomerList();
    }

    private void addChangeListenerToCustomerSearchField() {
        customerSearchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length() >= 2) {
                    customerModifyService.loadAllCustomersWithNameService.restart();
                } else {
                    customerModifyService.loadAllCustomersService.restart();
                }
            }
        });
    }

    private void addChangeListenerToCustomerList() {
        customerList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerViewModel>() {
            @Override
            public void changed(ObservableValue<? extends CustomerViewModel> observable, CustomerViewModel oldValue, CustomerViewModel newValue) {
                showModifyButton.setVisible(newValue != null);
            }
        });
    }

    public void showModify(ActionEvent actionEvent) {
        customerModifyService.initModifyService.restart();
    }

    public void modifyCustomer(Event event) {
        customerModifyService.modifyCustomerService.restart();
    }
}
