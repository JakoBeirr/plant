package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.model.dto.DateDTO;
import be.boomkwekerij.plant.view.model.FustViewModel;
import be.boomkwekerij.plant.view.services.FustListService;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.joda.time.DateTime;

import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class FustListController implements PageController {

    private FustListService fustListService = new FustListService();

    @FXML
    private TextField fustSearchField;
    @FXML
    private TableView<FustViewModel> fustList;
    @FXML
    private Button printFustButton;
    @FXML
    private Button printFustsButton;

    @Override
    public void init(Pane root) {
        fustListService.setFustSearchField(fustSearchField);
        fustListService.setFustList(fustList);
        fustListService.setPrintFustButton(printFustButton);
        fustListService.setPrintFustsButton(printFustsButton);
        fustListService.init(root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fustList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        addChangeListenerToField();
        addChangeListenersToList();
    }

    private void addChangeListenerToField() {
        fustSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 2) {
                fustListService.loadAllFustFromCustomerWithName.restart();
            } else {
                fustListService.loadAllFustsService.restart();
            }
        });
    }

    private void addChangeListenersToList() {
        fustList.getSelectionModel().getSelectedItems().addListener((ListChangeListener<FustViewModel>) selectedInvoices -> {
            if (selectedInvoices.getList().size() > 0) {
                printFustButton.setVisible(true);
                printFustButton.setManaged(true);
            } else {
                printFustButton.setVisible(true);
                printFustButton.setManaged(true);
            }
        });
    }

    public void printFust(ActionEvent actionEvent) {
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

            fustListService.setFustReportDate(dateDTO);
            fustListService.printFustService.restart();
        }
    }

    public void printFusts(ActionEvent actionEvent) {
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

            fustListService.setFustReportDate(dateDTO);
            fustListService.printFustsService.restart();
        }
    }
}
