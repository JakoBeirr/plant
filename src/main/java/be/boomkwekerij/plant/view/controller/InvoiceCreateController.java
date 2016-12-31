package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.controller.CustomerController;
import be.boomkwekerij.plant.controller.InvoiceController;
import be.boomkwekerij.plant.controller.PlantController;
import be.boomkwekerij.plant.exception.ItemNotFoundException;
import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.model.dto.InvoiceDTO;
import be.boomkwekerij.plant.model.dto.InvoiceLineDTO;
import be.boomkwekerij.plant.model.dto.PlantDTO;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;
import be.boomkwekerij.plant.view.mapper.CustomerViewMapper;
import be.boomkwekerij.plant.view.mapper.PlantViewMapper;
import be.boomkwekerij.plant.view.model.CustomerViewModel;
import be.boomkwekerij.plant.view.model.PlantViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.joda.time.DateTime;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class InvoiceCreateController implements PageController {

    private InvoiceController invoiceController = new InvoiceController();
    private CustomerController customerController = new CustomerController();
    private PlantController plantController = new PlantController();

    private CustomerViewMapper customerViewMapper = new CustomerViewMapper();
    private PlantViewMapper plantViewMapper = new PlantViewMapper();

    private InvoiceDTO newInvoice;

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
    private Button startCreateInvoiceButton;
    @FXML
    private GridPane createInvoicePane;
    @FXML
    private TextField customer;
    @FXML
    private TextField invoiceNumber;
    @FXML
    private DatePicker invoiceDate;
    @FXML
    private VBox createdInvoiceLines;
    @FXML
    private Label labelInvoiceLinesList;
    @FXML
    private Label labelInvoiceLinesCreate;
    @FXML
    private TextField orderNumber;
    @FXML
    private DatePicker invoiceLineDate;
    @FXML
    private TextField invoiceLineBtw;
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

    private static final String NON_NUMERIC_CHARACTERS = "[^\\d]";
    private static final String NON_DECIMAL_NUMERIC_CHARACTERS = "[^\\d.]";

    @Override
    public void init(Pane root) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadAllCustomers();
        addChangeListenerToSearchFields();
        addChangeListenersToList();
        initNumericFields();
    }

    private void loadAllCustomers() {
        List<CustomerViewModel> allCustomers = getAllCustomers();
        customerList.getItems().setAll(allCustomers);
    }

    private void addChangeListenerToSearchFields() {
        customerSearchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                List<CustomerViewModel> allCustomers;
                if (newValue.length() >= 2) {
                    allCustomers = getAllCustomersWithName(newValue);
                } else {
                    allCustomers = getAllCustomers();
                }
                customerList.getItems().setAll(allCustomers);
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

    private List<CustomerViewModel> getAllCustomers() {
        List<CustomerViewModel> customers = new ArrayList<>();

        SearchResult<CustomerDTO> customerSearchResult = customerController.getAllCustomers();
        if (customerSearchResult.isSuccess()) {
            for (CustomerDTO customerDTO : customerSearchResult.getResults()) {
                CustomerViewModel customerViewModel = customerViewMapper.mapDTOToViewModel(customerDTO);
                customers.add(customerViewModel);
            }
        }

        return customers;
    }

    private List<CustomerViewModel> getAllCustomersWithName(String name) {
        ArrayList<CustomerViewModel> customers = new ArrayList<>();

        SearchResult<CustomerDTO> customerSearchResult = customerController.getAllCustomersWithName(name);
        if (customerSearchResult.isSuccess()) {
            for (CustomerDTO customerDTO : customerSearchResult.getResults()) {
                CustomerViewModel customerViewModel = customerViewMapper.mapDTOToViewModel(customerDTO);
                customers.add(customerViewModel);
            }
        }

        return customers;
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
        customerList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerViewModel>() {
            @Override
            public void changed(ObservableValue<? extends CustomerViewModel> observable, CustomerViewModel oldValue, CustomerViewModel newValue) {
                startCreateInvoiceButton.setVisible(newValue != null);
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
        invoiceLineBtw.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                invoiceLineBtw.setText(newValue.replaceAll(NON_DECIMAL_NUMERIC_CHARACTERS, ""));
            }
        });
    }

    public void startInvoiceCreation(ActionEvent actionEvent) {
        customerList.setDisable(true);
        customerSearchField.setDisable(true);
        initializeKnownInvoiceProperties();
        createInvoicePane.setVisible(true);
    }

    private void initializeKnownInvoiceProperties() {
        CustomerViewModel selectedCustomer = customerList.getSelectionModel().getSelectedItem();
        newInvoice = invoiceController.makeNewInvoice(selectedCustomer.getId());

        customer.setText(newInvoice.getCustomer().getName1());
        invoiceNumber.setText(newInvoice.getInvoiceNumber());
        DateTime invoiceDTODate = newInvoice.getDate();
        invoiceDate.setValue(LocalDate.of(invoiceDTODate.getYear(), invoiceDTODate.getMonthOfYear(), invoiceDTODate.getDayOfMonth()));
        resetInvoiceLine();
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
            HBox invoiceLine = new HBox(5);

            Label createdChosenPlant = new Label(chosenPlant.getText());
            createdChosenPlant.setManaged(false);
            createdChosenPlant.setVisible(false);
            TextField createdPlant = new TextField(plantSearchField.getText());
            createdPlant.setPrefColumnCount(37);
            createdPlant.setDisable(true);
            TextField createdOrderNumber = new TextField(orderNumber.getText());
            createdOrderNumber.setPrefColumnCount(10);
            createdOrderNumber.setDisable(true);
            DatePicker createdInvoiceLineDate = new DatePicker(invoiceLineDate.getValue());
            createdInvoiceLineDate.setDisable(true);
            TextField createdAmount = new TextField(amount.getText());
            createdAmount.setPrefColumnCount(3);
            createdAmount.setDisable(true);
            TextField createdInvoiceLineBtw = new TextField(invoiceLineBtw.getText());
            createdInvoiceLineBtw.setPrefColumnCount(3);
            createdInvoiceLineBtw.setDisable(true);
            TextField createdPrice = new TextField(alternativePlantPrice.getText());
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
            invoiceLine.getChildren().add(createdOrderNumber);
            invoiceLine.getChildren().add(createdInvoiceLineDate);
            invoiceLine.getChildren().add(createdAmount);
            invoiceLine.getChildren().add(createdInvoiceLineBtw);
            invoiceLine.getChildren().add(createdPrice);
            invoiceLine.getChildren().add(deleteRow);
            createdInvoiceLines.getChildren().add(invoiceLine);

            resetInvoiceLine();
        }
    }

    private void resetInvoiceLine() {
        chosenPlant.setText("");
        plantSearchField.setText("");
        orderNumber.setText("");
        amount.setText("");
        alternativePlantPrice.setText("");
        invoiceLineDate.setValue(LocalDate.of(DateTime.now().getYear(), DateTime.now().getMonthOfYear(), DateTime.now().getDayOfMonth()));
        invoiceLineBtw.setText(Double.toString(newInvoice.getDefaultBtw()));
        plantSearchField.setDisable(false);
    }

    public void createInvoice(Event event) {
        try {
            CustomerViewModel selectedCustomer = customerList.getSelectionModel().getSelectedItem();
            CustomerDTO selectedCustomerDTO = customerViewMapper.mapViewModelToDTO(selectedCustomer);

            InvoiceDTO invoiceDTO = new InvoiceDTO();
            invoiceDTO.setCustomer(selectedCustomerDTO);
            invoiceDTO.setInvoiceNumber(invoiceNumber.getText());
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
                TextField createdOrderNumber = (TextField) children.get(2);
                DatePicker createdInvoiceLineDate = (DatePicker) children.get(3);
                TextField createdAmount = (TextField) children.get(4);
                TextField createdInvoiceLineBtw = (TextField) children.get(5);
                TextField createdPrice = (TextField) children.get(6);

                InvoiceLineDTO invoiceLineDTO = new InvoiceLineDTO();
                invoiceLineDTO.setOrderNumber(createdOrderNumber.getText());
                LocalDate invoiceLineDate = createdInvoiceLineDate.getValue();
                invoiceLineDTO.setDate(new DateTime(invoiceLineDate.getYear(), invoiceLineDate.getMonthValue(), invoiceLineDate.getDayOfMonth(), 0, 0, 0, 0));
                invoiceLineDTO.setAmount(Integer.parseInt(createdAmount.getText()));
                SearchResult<PlantDTO> plantSearchResult = plantController.getPlant(createdChosenPlant.getText());
                if (plantSearchResult.isSuccess()) {
                    PlantDTO selectedPlant = plantSearchResult.getFirst();
                    invoiceLineDTO.setPlantId(selectedPlant.getId());
                    invoiceLineDTO.setPlantName(selectedPlant.getName());
                    invoiceLineDTO.setPlantAge(selectedPlant.getAge());
                    invoiceLineDTO.setPlantMeasure(selectedPlant.getMeasure());
                } else {
                    throw new ItemNotFoundException("Kon plant " + createdPlant.getText() + " niet vinden!");
                }
                invoiceLineDTO.setBtw(Double.parseDouble(createdInvoiceLineBtw.getText()));
                invoiceLineDTO.setPlantPrice(Double.parseDouble(createdPrice.getText()));
                invoiceDTO.getInvoiceLines().add(invoiceLineDTO);
            }
            invoiceDTO.setPayed(false);

            CrudsResult invoiceCreateResult = invoiceController.createInvoice(invoiceDTO);
            if (invoiceCreateResult.isSuccess()) {
                handleCreateSuccess();
            } else {
                handleCreateError(invoiceCreateResult.getMessages());
            }
        } catch (ItemNotFoundException e) {
            handleCreateError(Arrays.asList(e.getMessage()));
        } catch (Exception e) {
            handleCreateException(e);
        }
    }

    private void handleCreateSuccess() {
        customerList.setDisable(false);
        customerList.getSelectionModel().clearSelection();
        customerSearchField.setDisable(false);
        createInvoicePane.setVisible(false);
        createdInvoiceLines.getChildren().setAll(Collections.emptyList());
        resetInvoiceLine();
        AlertController.alertSuccess("Factuur aangemaakt!");
    }

    private void handleCreateError(List<String> errorMessages) {
        StringBuilder errorBuilder = new StringBuilder("Gefaald wegens volgende fout(en): ");
        for (int i = 0; i < errorMessages.size(); i++) {
            String errorMessage = errorMessages.get(i);
            errorBuilder.append("\n").append(i+1).append(") ").append(errorMessage);
        }
        AlertController.alertError("Factuur aanmaken gefaald!", errorBuilder.toString());
    }

    private void handleCreateException(Exception e) {
        AlertController.alertException("Factuur aanmaken gefaald!", e);
    }
}
