package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.model.dto.DateDTO;
import be.boomkwekerij.plant.view.model.CustomerViewModel;
import be.boomkwekerij.plant.view.services.CustomerListService;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.joda.time.DateTime;

import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerListController implements PageController {

    private CustomerListService customerListService = new CustomerListService();

    @FXML
    private TextField customerSearchField;
    @FXML
    private TableView<CustomerViewModel> customerList;
    @FXML
    private Button customerDetailsButton;
    @FXML
    private Button printFustButton;
    @FXML
    private Button customerDeleteButton;

    @Override
    public void init(Pane root) {
        customerListService.setCustomerSearchField(customerSearchField);
        customerListService.setCustomerList(customerList);
        customerListService.setCustomerDetailsButton(customerDetailsButton);
        customerListService.setPrintFustButton(printFustButton);
        customerListService.setCustomerDeleteButton(customerDeleteButton);
        customerListService.init(root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addChangeListenerToSearchField();
        addChangeListenerToCustomerList();
    }

    private void addChangeListenerToSearchField() {
        customerSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() >= 2) {
                customerListService.loadAllCustomersWithNameService.restart();
            } else {
                customerListService.loadAllCustomersService.restart();
            }
        });
    }

    private void addChangeListenerToCustomerList() {
        customerList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            customerDetailsButton.setVisible(newValue != null);
            customerDetailsButton.setManaged(newValue != null);
            printFustButton.setVisible(newValue != null);
            printFustButton.setManaged(newValue != null);
            customerDeleteButton.setVisible(newValue != null);
            customerDeleteButton.setManaged(newValue != null);
        });
    }

    public void showCustomer(Event event) {
        customerListService.showCustomerDetailsService.restart();
    }

    public void printFust(Event event) {
        Dialog<DateDTO> fustReportDialog = new Dialog<DateDTO>();
        fustReportDialog.setTitle("Fust rapport");
        fustReportDialog.setHeaderText("Print fust tot en met datum:");

        GridPane fieldPane = new GridPane();
        fieldPane.setHgap(10);
        fieldPane.setPadding(new Insets(20, 150, 10, 10));
        DatePicker fustReportDatePicker = new DatePicker();
        fieldPane.add(fustReportDatePicker, 1, 0);
        fustReportDialog.getDialogPane().setContent(fieldPane);

        ButtonType printButton = new ButtonType("Print", ButtonBar.ButtonData.OK_DONE);
        fustReportDialog.getDialogPane().getButtonTypes().addAll(printButton);

        fustReportDialog.setResultConverter(dialogButton -> {
            if (dialogButton == printButton) {
                LocalDate fustReportDate = fustReportDatePicker.getValue();

                if (fustReportDate != null) {
                    DateDTO dateDTO = new DateDTO();
                    dateDTO.setDate(new DateTime(fustReportDate.getYear(), fustReportDate.getMonthValue(), fustReportDate.getDayOfMonth(), 0, 0, 0, 0));
                    return dateDTO;
                }
            }
            return null;
        });

        Optional<DateDTO> fustReportDateResult = fustReportDialog.showAndWait();
        if (fustReportDateResult.isPresent()) {
            DateDTO dateDTO = fustReportDateResult.get();

            customerListService.setFustReportDate(dateDTO);
            customerListService.printFustService.restart();
        }
    }

    public void deleteCustomer(Event event) {
        if (AlertController.areYouSure("Bent u zeker dat u deze klant wil verwijderen?", "Bedenk dat dit enkel mogelijk is indien er geen facturen gelinkt zijn aan deze klant!")) {
            customerListService.deleteCustomerService.restart();
        }
    }
}
