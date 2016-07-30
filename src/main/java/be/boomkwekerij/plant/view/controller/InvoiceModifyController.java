package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.controller.CustomerController;
import be.boomkwekerij.plant.controller.InvoiceController;
import be.boomkwekerij.plant.controller.PlantController;
import be.boomkwekerij.plant.exception.ItemNotFoundException;
import be.boomkwekerij.plant.model.dto.InvoiceDTO;
import be.boomkwekerij.plant.model.dto.InvoiceLineDTO;
import be.boomkwekerij.plant.model.dto.PlantDTO;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;
import be.boomkwekerij.plant.view.mapper.CustomerViewMapper;
import be.boomkwekerij.plant.view.mapper.InvoiceViewMapper;
import be.boomkwekerij.plant.view.mapper.PlantViewMapper;
import be.boomkwekerij.plant.view.model.InvoiceViewModel;
import be.boomkwekerij.plant.view.model.PlantViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.joda.time.DateTime;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class InvoiceModifyController implements Initializable {

    private InvoiceController invoiceController = new InvoiceController();
    private CustomerController customerController = new CustomerController();
    private PlantController plantController = new PlantController();

    private InvoiceViewMapper invoiceViewMapper = new InvoiceViewMapper();
    private CustomerViewMapper customerViewMapper = new CustomerViewMapper();
    private PlantViewMapper plantViewMapper = new PlantViewMapper();

    @FXML
    private TextField searchField;
    @FXML
    private TableView<InvoiceViewModel> invoiceList;
    @FXML
    private TableColumn<InvoiceViewModel, String> customerName;
    @FXML
    private TableColumn<InvoiceViewModel, String> invoiceNumber;
    @FXML
    private TableColumn<InvoiceViewModel, String> date;
    @FXML
    private TableColumn<InvoiceViewModel, Double> totalPrice;
    @FXML
    private TableColumn<InvoiceViewModel, Boolean> payed;
    @FXML
    private Button startUpdateInvoiceButton;
    @FXML
    private GridPane updateInvoicePane;
    @FXML
    private TextField customer;
    @FXML
    private TextField invoiceNumberField;
    @FXML
    private DatePicker invoiceDate;
    @FXML
    private VBox createdInvoiceLines;
    @FXML
    private Label labelInvoiceLinesList;
    @FXML
    private Label labelInvoiceLinesCreate;
    @FXML
    private DatePicker invoiceLineDate;
    @FXML
    private TextField amount;
    @FXML
    private TextField alternativePlantPrice;
    @FXML
    private TextField plantSearchField;
    @FXML
    private TableView<PlantViewModel> plantList;
    @FXML
    private TableColumn<PlantViewModel, String> name;
    @FXML
    private TableColumn<PlantViewModel, String> age;
    @FXML
    private TableColumn<PlantViewModel, String> measure;
    @FXML
    private TableColumn<PlantViewModel, Double> price;
    @FXML
    private Label chosenPlant;
    @FXML
    private Button choosePlantButton;
    @FXML
    private TextField btw;

    private static final String NON_NUMERIC_CHARACTERS = "[^\\d]";
    private static final String NON_DECIMAL_NUMERIC_CHARACTERS = "[^\\d.]";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadAllInvoices();
        addChangeListenerToSearchFields();
        addChangeListenersToList();
    }

    private void loadAllInvoices() {
        List<InvoiceViewModel> allInvoices = getAllInvoices();
        invoiceList.getItems().setAll(allInvoices);
    }

    private void addChangeListenerToSearchFields() {
        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                List<InvoiceViewModel> invoices;
                if (newValue.length() > 2) {
                    invoices = getInvoicesWithNumber(newValue);
                } else {
                    invoices = getAllInvoices();
                }
                invoiceList.getItems().setAll(invoices);
            }
        });
        plantSearchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length() > 0) {
                    plantList.setVisible(true);
                    plantList.setManaged(true);
                } else {
                    plantList.setVisible(false);
                    plantList.setManaged(false);
                }

                if (newValue.length() >= 2) {
                    plantList.getItems().setAll(getAllPlantsWithName(newValue));
                }
            }
        });
    }

    private List<InvoiceViewModel> getAllInvoices() {
        List<InvoiceViewModel> invoices = new ArrayList<>();

        SearchResult<InvoiceDTO> invoiceSearchResult = invoiceController.getAllInvoices();
        if (invoiceSearchResult.isSuccess()) {
            for (InvoiceDTO invoiceDTO : invoiceSearchResult.getResults()) {
                InvoiceViewModel invoiceViewModel = invoiceViewMapper.mapDTOToViewModel(invoiceDTO);
                invoices.add(invoiceViewModel);
            }
        }

        return invoices;
    }

    private List<InvoiceViewModel> getInvoicesWithNumber(String invoiceNumber) {
        List<InvoiceViewModel> invoices = new ArrayList<>();

        SearchResult<InvoiceDTO> invoiceSearchResult = invoiceController.getAllInvoicesWithInvoiceNumber(invoiceNumber);
        if (invoiceSearchResult.isSuccess()) {
            for (InvoiceDTO invoiceDTO : invoiceSearchResult.getResults()) {
                InvoiceViewModel invoiceViewModel = invoiceViewMapper.mapDTOToViewModel(invoiceDTO);
                invoices.add(invoiceViewModel);
            }
        }

        return invoices;
    }

    private List<PlantViewModel> getAllPlantsWithName(String name) {
        ArrayList<PlantViewModel> plants = new ArrayList<>();

        SearchResult<PlantDTO> plantSearchResult = plantController.getAllPlantsWithName(name);
        if (plantSearchResult.isSuccess()) {
            for (PlantDTO plantDTO : plantSearchResult.getResults()) {
                PlantViewModel plantViewModel = plantViewMapper.mapDTOToViewModel(plantDTO);
                plants.add(plantViewModel);
            }
        }

        return plants;
    }

    private void addChangeListenersToList() {
        invoiceList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<InvoiceViewModel>() {
            @Override
            public void changed(ObservableValue<? extends InvoiceViewModel> observable, InvoiceViewModel oldValue, InvoiceViewModel newValue) {
                startUpdateInvoiceButton.setVisible(newValue != null);
            }
        });
        plantList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PlantViewModel>() {
            @Override
            public void changed(ObservableValue<? extends PlantViewModel> observable, PlantViewModel oldValue, PlantViewModel newValue) {
                choosePlantButton.setVisible(newValue != null);
                choosePlantButton.setManaged(newValue != null);
            }
        });
        createdInvoiceLines.getChildren().addListener(new ListChangeListener<Node>() {
            @Override
            public void onChanged(Change<? extends Node> c) {
                if (c.getList().size() > 0) {
                    createdInvoiceLines.setVisible(true);
                    createdInvoiceLines.setManaged(true);
                    labelInvoiceLinesList.setVisible(true);
                    labelInvoiceLinesList.setManaged(true);
                    labelInvoiceLinesCreate.setVisible(false);
                    labelInvoiceLinesCreate.setManaged(false);
                } else {
                    createdInvoiceLines.setVisible(false);
                    createdInvoiceLines.setManaged(false);
                    labelInvoiceLinesList.setVisible(false);
                    labelInvoiceLinesList.setManaged(false);
                    labelInvoiceLinesCreate.setVisible(true);
                    labelInvoiceLinesCreate.setManaged(true);
                }
            }
        });
    }

    private void initNumericFields() {
        amount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                amount.setText(newValue.replaceAll(NON_NUMERIC_CHARACTERS, ""));
            }
        });
        alternativePlantPrice.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                alternativePlantPrice.setText(newValue.replaceAll(NON_DECIMAL_NUMERIC_CHARACTERS, ""));
            }
        });
        btw.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                btw.setText(newValue.replaceAll(NON_DECIMAL_NUMERIC_CHARACTERS, ""));
            }
        });
    }

    public void startInvoiceUpdate(ActionEvent actionEvent) {
        invoiceList.setDisable(true);
        searchField.setDisable(true);
        initializeKnownInvoiceProperties();
        updateInvoicePane.setVisible(true);
    }

    private void initializeKnownInvoiceProperties() {
        InvoiceViewModel selectedInvoice = invoiceList.getSelectionModel().getSelectedItem();
        SearchResult<InvoiceDTO> searchResult = invoiceController.getInvoice(selectedInvoice.getId());

        if (searchResult.isSuccess()) {
            InvoiceDTO invoiceDTO = searchResult.getFirst();
            customer.setText(invoiceDTO.getCustomer().getName1());
            invoiceNumberField.setText(invoiceDTO.getInvoiceNumber());
            DateTime invoiceDTODate = invoiceDTO.getDate();
            invoiceDate.setValue(LocalDate.of(invoiceDTODate.getYear(), invoiceDTODate.getMonthOfYear(), invoiceDTODate.getDayOfMonth()));
            for (InvoiceLineDTO invoiceLineDTO : invoiceDTO.getInvoiceLines()) {
                DateTime invoiceLineDate = invoiceLineDTO.getDate();
                String plantName = invoiceLineDTO.getPlant().getName() + "    (" + invoiceLineDTO.getPlant().getAge() + " - " + invoiceLineDTO.getPlant().getMeasure() + ")";
                LocalDate invoiceLineLocalDate = LocalDate.of(invoiceLineDate.getYear(), invoiceLineDate.getMonthOfYear(), invoiceLineDate.getDayOfMonth());
                String amount = Integer.toString(invoiceLineDTO.getAmount());
                String price = Double.toString(invoiceLineDTO.getPrice());
                addCreatedInvoiceLine(invoiceLineDTO.getPlant().getId(), plantName, invoiceLineLocalDate, amount, price);
            }
            resetInvoiceLine();
            btw.setText(Double.toString(invoiceDTO.getBtw()));
        }
    }

    public void choosePlant(ActionEvent actionEvent) {
        PlantViewModel selectedPlant = plantList.getSelectionModel().getSelectedItem();

        chosenPlant.setText(selectedPlant.getId());
        plantSearchField.setText(selectedPlant.getName() + "    (" + selectedPlant.getAge() + " - " + selectedPlant.getMeasure() + ")");
        alternativePlantPrice.setText(selectedPlant.getPrice().replaceAll(",", "."));
        plantList.setVisible(false);
        plantList.setManaged(false);
        plantSearchField.setDisable(true);
        choosePlantButton.setVisible(false);
        choosePlantButton.setManaged(false);
    }

    public void clearInvoiceLine(ActionEvent actionEvent) {
        resetInvoiceLine();
    }

    public void createInvoiceLine(ActionEvent actionEvent) {
        if (!chosenPlant.getText().trim().isEmpty() && !plantSearchField.getText().trim().isEmpty() && invoiceLineDate.getValue() != null && !amount.getText().trim().isEmpty() && !alternativePlantPrice.getText().trim().isEmpty()) {
            addCreatedInvoiceLine(chosenPlant.getText(), plantSearchField.getText(), invoiceLineDate.getValue(), amount.getText(), alternativePlantPrice.getText());

            resetInvoiceLine();
        }
    }

    private void addCreatedInvoiceLine(String chosenPlant, String plantSearchField, LocalDate invoiceLineDate, String amount, String alternativePlantPrice) {
        HBox invoiceLine = new HBox(5);

        Label createdChosenPlant = new Label(chosenPlant);
        createdChosenPlant.setManaged(false);
        createdChosenPlant.setVisible(false);
        TextField createdPlant = new TextField(plantSearchField);
        createdPlant.setPrefColumnCount(45);
        createdPlant.setDisable(true);
        DatePicker createdInvoiceLineDate = new DatePicker(invoiceLineDate);
        createdInvoiceLineDate.setDisable(true);
        TextField createdAmount = new TextField(amount);
        createdAmount.setPrefColumnCount(3);
        createdAmount.setDisable(true);
        TextField createdPrice = new TextField(alternativePlantPrice);
        createdPrice.setPrefColumnCount(5);
        createdPrice.setDisable(true);
        Button deleteRow = new Button("Verwijder rij");
        deleteRow.getStyleClass().add("red-button");
        deleteRow.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createdInvoiceLines.getChildren().remove(invoiceLine);
            }
        });

        invoiceLine.getChildren().add(createdChosenPlant);
        invoiceLine.getChildren().add(createdPlant);
        invoiceLine.getChildren().add(createdInvoiceLineDate);
        invoiceLine.getChildren().add(createdAmount);
        invoiceLine.getChildren().add(createdPrice);
        invoiceLine.getChildren().add(deleteRow);
        createdInvoiceLines.getChildren().add(invoiceLine);
    }

    private void resetInvoiceLine() {
        chosenPlant.setText("");
        plantSearchField.setText("");
        amount.setText("");
        alternativePlantPrice.setText("");
        invoiceLineDate.setValue(LocalDate.of(DateTime.now().getYear(), DateTime.now().getMonthOfYear(), DateTime.now().getDayOfMonth()));
        plantSearchField.setDisable(false);
    }

    public void updateInvoice(Event event) {
        try {
            InvoiceViewModel selectedInvoice = invoiceList.getSelectionModel().getSelectedItem();
            SearchResult<InvoiceDTO> searchResult = invoiceController.getInvoice(selectedInvoice.getId());

            if (searchResult.isSuccess()) {
                InvoiceDTO invoice = searchResult.getFirst();
                InvoiceDTO invoiceDTO = new InvoiceDTO();
                invoiceDTO.setId(invoice.getId());
                invoiceDTO.setCustomer(invoice.getCustomer());
                invoiceDTO.setInvoiceNumber(invoiceNumberField.getText());
                LocalDate invoiceDate = this.invoiceDate.getValue();
                if (invoiceDate != null) {
                    invoiceDTO.setDate(new DateTime(invoiceDate.getYear(), invoiceDate.getMonthValue(), invoiceDate.getDayOfMonth(), 0, 0, 0, 0));
                } else {
                    invoiceDTO.setDate(null);
                }
                for (Node node : createdInvoiceLines.getChildren()) {
                    HBox createdInvoiceLine = (HBox) node;
                    ObservableList<Node> children = createdInvoiceLine.getChildren();
                    Label createdChosenPlant = (Label) children.get(0);
                    TextField createdPlant = (TextField) children.get(1);
                    DatePicker createdInvoiceLineDate = (DatePicker) children.get(2);
                    TextField createdAmount = (TextField) children.get(3);
                    TextField createdPrice = (TextField) children.get(4);

                    InvoiceLineDTO invoiceLineDTO = new InvoiceLineDTO();
                    LocalDate invoiceLineDate = createdInvoiceLineDate.getValue();
                    invoiceLineDTO.setDate(new DateTime(invoiceLineDate.getYear(), invoiceLineDate.getMonthValue(), invoiceLineDate.getDayOfMonth(), 0, 0, 0, 0));
                    invoiceLineDTO.setAmount(Integer.parseInt(createdAmount.getText()));
                    SearchResult<PlantDTO> plantSearchResult = plantController.getPlant(createdChosenPlant.getText());
                    if (plantSearchResult.isSuccess()) {
                        PlantDTO selectedPlant = plantSearchResult.getFirst();
                        invoiceLineDTO.setPlant(selectedPlant);
                    } else {
                        throw new ItemNotFoundException("Kon plant " + createdPlant.getText() + " niet vinden!");
                    }
                    invoiceLineDTO.setPrice(Double.parseDouble(createdPrice.getText()));
                    invoiceDTO.getInvoiceLines().add(invoiceLineDTO);
                }
                invoiceDTO.setBtw(Double.parseDouble(btw.getText()));
                invoiceDTO.setPayed(invoice.isPayed());

                CrudsResult invoiceCreateResult = invoiceController.updateInvoice(invoiceDTO);
                if (invoiceCreateResult.isSuccess()) {
                    handleUpdateSuccess();
                } else {
                    handleUpdateError(invoiceCreateResult.getMessages());
                }
            } else {
                handleUpdateError(Arrays.asList("Factuur bestaat niet"));
            }
        } catch (ItemNotFoundException e) {
            handleUpdateError(Arrays.asList(e.getMessage()));
        } catch (Exception e) {
            handleUpdateException(e);
        }
    }

    private void handleUpdateSuccess() {
        invoiceList.setDisable(false);
        invoiceList.getSelectionModel().clearSelection();
        searchField.setDisable(false);
        updateInvoicePane.setVisible(false);
        createdInvoiceLines.getChildren().setAll(Collections.emptyList());
        resetInvoiceLine();
        loadAllInvoices();
        AlertController.alertSuccess("Factuur bewerkt!");
    }

    private void handleUpdateError(List<String> errorMessages) {
        StringBuilder errorBuilder = new StringBuilder("Gefaald wegens volgende fout(en): ");
        for (int i = 0; i < errorMessages.size(); i++) {
            String errorMessage = errorMessages.get(i);
            errorBuilder.append("\n").append(i+1).append(") ").append(errorMessage);
        }
        AlertController.alertError("Factuur bewerken gefaald!", errorBuilder.toString());
    }

    private void handleUpdateException(Exception e) {
        AlertController.alertException("Factuur bewerken gefaald!", e);
    }
}
