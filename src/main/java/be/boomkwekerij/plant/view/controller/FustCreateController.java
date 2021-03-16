package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.view.model.CustomerViewModel;
import be.boomkwekerij.plant.view.services.FustCreateService;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class FustCreateController implements PageController {

    private final FustCreateService fustCreateService = new FustCreateService();

    @FXML
    private TextField customerSearchField;
    @FXML
    private TableView<CustomerViewModel> customerList;
    @FXML
    private Button showCreateFustButton;
    @FXML
    private GridPane createFustPane;
    @FXML
    private TextField customer;
    @FXML
    private TextField lageKisten;
    @FXML
    private TextField hogeKisten;
    @FXML
    private TextField palletBodem;
    @FXML
    private TextField boxPallet;
    @FXML
    private TextField halveBox;
    @FXML
    private TextField ferroPalletKlein;
    @FXML
    private TextField ferroPalletGroot;
    @FXML
    private TextField karren;
    @FXML
    private TextField borden;
    @FXML
    private TextField diverse;
    @FXML
    private DatePicker datum;
    @FXML
    private Button fustCreateButton;

    private static final String NON_NUMERIC_CHARACTERS = "[^\\d]";

    @Override
    public void init(Pane root) {
        fustCreateService.setCustomerSearchField(customerSearchField);
        fustCreateService.setCustomerList(customerList);
        fustCreateService.setShowCreateFustButton(showCreateFustButton);
        fustCreateService.setCreateFustPane(createFustPane);
        fustCreateService.setCustomer(customer);
        fustCreateService.setLageKisten(lageKisten);
        fustCreateService.setHogeKisten(hogeKisten);
        fustCreateService.setPalletBodem(palletBodem);
        fustCreateService.setBoxPallet(boxPallet);
        fustCreateService.setHalveBox(halveBox);
        fustCreateService.setFerroPalletKlein(ferroPalletKlein);
        fustCreateService.setFerroPalletGroot(ferroPalletGroot);
        fustCreateService.setKarren(karren);
        fustCreateService.setBorden(borden);
        fustCreateService.setDiverse(diverse);
        fustCreateService.setDatum(datum);
        fustCreateService.setFustCreateButton(fustCreateButton);
        fustCreateService.init(root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addChangeListenerToSearchFields();
        addChangeListenersToList();
        initNumericFields();
    }

    private void addChangeListenerToSearchFields() {
        customerSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() >= 2) {
                fustCreateService.loadAllCustomersWithNameService.restart();
            } else {
                fustCreateService.loadAllCustomersService.restart();
            }
        });
    }

    private void addChangeListenersToList() {
        customerList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showCreateFustButton.setVisible(newValue != null)
        );
    }

    private void initNumericFields() {
        makeFieldNegativeNumeric(lageKisten);
        makeFieldNegativeNumeric(hogeKisten);
        makeFieldNegativeNumeric(palletBodem);
        makeFieldNegativeNumeric(boxPallet);
        makeFieldNegativeNumeric(halveBox);
        makeFieldNegativeNumeric(ferroPalletKlein);
        makeFieldNegativeNumeric(ferroPalletKlein);
        makeFieldNegativeNumeric(ferroPalletGroot);
        makeFieldNegativeNumeric(karren);
        makeFieldNegativeNumeric(borden);
        makeFieldNegativeNumeric(diverse);
    }

    private void makeFieldNegativeNumeric(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.startsWith("-")) {
                textField.setText("-" + newValue.replaceAll(NON_NUMERIC_CHARACTERS, ""));
            } else {
                textField.setText(newValue.replaceAll(NON_NUMERIC_CHARACTERS, ""));
            }
        });
    }

    public void showCreateFust(ActionEvent actionEvent) {
        fustCreateService.initFustCreateService.restart();
    }

    public void createFust(Event event) {
        fustCreateService.createFustService.restart();
    }
}
