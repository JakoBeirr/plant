package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.view.model.CustomerViewModel;
import be.boomkwekerij.plant.view.services.FustCreateService;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class FustCreateController implements PageController {

    private FustCreateService fustCreateService = new FustCreateService();

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
    private TextField karrenEnBorden;
    @FXML
    private TextField diverse;
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
        fustCreateService.setKarrenEnBorden(karrenEnBorden);
        fustCreateService.setDiverse(diverse);
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
        customerList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showCreateFustButton.setVisible(newValue != null);
        });
    }

    private void initNumericFields() {
        lageKisten.textProperty().addListener((observable, oldValue, newValue) -> {
            lageKisten.setText(newValue.replaceAll(NON_NUMERIC_CHARACTERS, ""));
        });
        hogeKisten.textProperty().addListener((observable, oldValue, newValue) -> {
            hogeKisten.setText(newValue.replaceAll(NON_NUMERIC_CHARACTERS, ""));
        });
        palletBodem.textProperty().addListener((observable, oldValue, newValue) -> {
            palletBodem.setText(newValue.replaceAll(NON_NUMERIC_CHARACTERS, ""));
        });
        boxPallet.textProperty().addListener((observable, oldValue, newValue) -> {
            boxPallet.setText(newValue.replaceAll(NON_NUMERIC_CHARACTERS, ""));
        });
        halveBox.textProperty().addListener((observable, oldValue, newValue) -> {
            halveBox.setText(newValue.replaceAll(NON_NUMERIC_CHARACTERS, ""));
        });
        ferroPalletKlein.textProperty().addListener((observable, oldValue, newValue) -> {
            ferroPalletKlein.setText(newValue.replaceAll(NON_NUMERIC_CHARACTERS, ""));
        });
        ferroPalletGroot.textProperty().addListener((observable, oldValue, newValue) -> {
            ferroPalletGroot.setText(newValue.replaceAll(NON_NUMERIC_CHARACTERS, ""));
        });
        karrenEnBorden.textProperty().addListener((observable, oldValue, newValue) -> {
            karrenEnBorden.setText(newValue.replaceAll(NON_NUMERIC_CHARACTERS, ""));
        });
        diverse.textProperty().addListener((observable, oldValue, newValue) -> {
            diverse.setText(newValue.replaceAll(NON_NUMERIC_CHARACTERS, ""));
        });
    }

    public void showCreateFust(ActionEvent actionEvent) {
        fustCreateService.initFustCreateService.restart();
    }

    public void createFust(Event event) {
        fustCreateService.createFustService.restart();
    }
}
