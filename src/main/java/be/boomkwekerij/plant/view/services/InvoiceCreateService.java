package be.boomkwekerij.plant.view.services;

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
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.joda.time.DateTime;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InvoiceCreateService {

    private CustomerController customerController = new CustomerController();
    private PlantController plantController = new PlantController();
    private InvoiceController invoiceController = new InvoiceController();

    private CustomerViewMapper customerViewMapper = new CustomerViewMapper();
    private PlantViewMapper plantViewMapper = new PlantViewMapper();

    private TextField customerSearchField;
    private TableView<CustomerViewModel> customerList;
    private Button showCreateInvoiceButton;
    private GridPane createInvoicePane;
    private TextField customer;
    private TextField invoiceNumber;
    private DatePicker invoiceDate;
    private VBox invoiceLines;
    private TextField plantSearchField;
    private TableView<PlantViewModel> plantList;
    private Label chosenPlant;
    private Button choosePlantButton;
    private TextField orderNumber;
    private DatePicker invoiceLineDate;
    private TextField invoiceLineBtw;
    private TextField amount;
    private TextField alternativePlantPrice;
    private Button invoiceCreateButton;

    private InvoiceDTO newInvoice = null;

    public final Service loadAllCustomersService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Inladen alle klanten");

                    SearchResult<CustomerDTO> searchResult = customerController.getAllCustomers();
                    if (searchResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(searchResult.getMessages().toArray()));
                    }

                    List<CustomerViewModel> customers = new ArrayList<>();
                    for (CustomerDTO customerDTO : searchResult.getResults()) {
                        CustomerViewModel customerViewModel = customerViewMapper.mapDTOToViewModel(customerDTO);
                        customers.add(customerViewModel);
                    }
                    customerList.getItems().setAll(customers);

                    return null;
                }
            };
        }
    };

    public final Service loadAllCustomersWithNameService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Inladen alle klanten met naam");

                    String name = customerSearchField.getText();
                    SearchResult<CustomerDTO> searchResult = customerController.getAllCustomersWithName(name);
                    if (searchResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(searchResult.getMessages().toArray()));
                    }

                    List<CustomerViewModel> customers = new ArrayList<>();
                    for (CustomerDTO customerDTO : searchResult.getResults()) {
                        CustomerViewModel customerViewModel = customerViewMapper.mapDTOToViewModel(customerDTO);
                        customers.add(customerViewModel);
                    }
                    customerList.getItems().setAll(customers);

                    return null;
                }
            };
        }
    };

    public final Service loadAllPlantsWithNameService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Inladen alle planten met naam");

                    String name = plantSearchField.getText();
                    SearchResult<PlantDTO> searchResult = plantController.getAllPlantsWithName(name);
                    if (searchResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(searchResult.getMessages().toArray()));
                    }

                    List<PlantViewModel> plants = new ArrayList<>();
                    for (PlantDTO plantDTO : searchResult.getResults()) {
                        PlantViewModel plantViewModel = plantViewMapper.mapDTOToViewModel(plantDTO);
                        plants.add(plantViewModel);
                    }
                    plantList.getItems().setAll(plants);

                    return null;
                }
            };
        }
    };

    public final Service initInvoiceCreateService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Initialiseren gegevens voor bewerking");

                    CustomerViewModel selectedCustomer = customerList.getSelectionModel().getSelectedItem();
                    newInvoice = invoiceController.makeNewInvoice(selectedCustomer.getId());

                    customer.setText(newInvoice.getCustomer().getName1());
                    invoiceNumber.setText(newInvoice.getInvoiceNumber());
                    DateTime invoiceDTODate = newInvoice.getDate();
                    invoiceDate.setValue(LocalDate.of(invoiceDTODate.getYear(), invoiceDTODate.getMonthOfYear(), invoiceDTODate.getDayOfMonth()));

                    return null;
                }
            };
        }
    };

    public final Service choosePlantService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Plant selecteren");

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            PlantViewModel selectedPlant = plantList.getSelectionModel().getSelectedItem();
                            chosenPlant.setText(selectedPlant.getId());
                            plantSearchField.setText(selectedPlant.getName() + "    (" + selectedPlant.getAge() + " - " + selectedPlant.getMeasure() + ")");
                            alternativePlantPrice.setText(selectedPlant.getPrice().replaceAll(",", "."));
                        }
                    });

                    return null;
                }
            };
        }
    };

    public final Service clearInvoiceLineService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Factuurlijn legen");

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            chosenPlant.setText("");
                            plantSearchField.setText("");
                            orderNumber.setText("");
                            amount.setText("");
                            alternativePlantPrice.setText("");
                            invoiceLineDate.setValue(LocalDate.of(DateTime.now().getYear(), DateTime.now().getMonthOfYear(), DateTime.now().getDayOfMonth()));
                            invoiceLineBtw.setText(Double.toString(newInvoice.getDefaultBtw()));
                            plantSearchField.setDisable(false);
                        }
                    });

                    return null;
                }
            };
        }
    };

    public final Service createInvoiceLineService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Aanmaken factuurlijn");

                    if (chosenPlant.getText().trim().isEmpty() || plantSearchField.getText().trim().isEmpty() || invoiceLineDate.getValue() == null || amount.getText().trim().isEmpty() || alternativePlantPrice.getText().trim().isEmpty()) {
                        throw new IllegalArgumentException("Verplichte velden niet ingevuld");
                    }

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            addInvoiceLine(chosenPlant.getText(), plantSearchField.getText(), orderNumber.getText(), invoiceLineDate.getValue(), amount.getText(), invoiceLineBtw.getText(), alternativePlantPrice.getText());
                        }
                    });

                    return null;
                }
            };
        }
    };

    public final Service createInvoiceService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Aanmaken factuur");

                    CustomerViewModel selectedCustomer = customerList.getSelectionModel().getSelectedItem();
                    CustomerDTO selectedCustomerDTO = customerViewMapper.mapViewModelToDTO(selectedCustomer);

                    InvoiceDTO invoiceDTO = new InvoiceDTO();
                    invoiceDTO.setCustomer(selectedCustomerDTO);
                    invoiceDTO.setInvoiceNumber(invoiceNumber.getText());
                    LocalDate invoiceLocalDate = invoiceDate.getValue();
                    if (invoiceLocalDate != null) {
                        invoiceDTO.setDate(new DateTime(invoiceLocalDate.getYear(), invoiceLocalDate.getMonthValue(), invoiceLocalDate.getDayOfMonth(), 0, 0, 0, 0));
                    } else {
                        invoiceDTO.setDate(null);
                    }
                    for (Node node : invoiceLines.getChildren()) {
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

                    CrudsResult createResult = invoiceController.createInvoice(invoiceDTO);
                    if (createResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(createResult.getMessages().toArray()));
                    }

                    return null;
                }
            };
        }
    };

    public void setCustomerSearchField(TextField customerSearchField) {
        this.customerSearchField = customerSearchField;
    }

    public void setCustomerList(TableView<CustomerViewModel> customerList) {
        this.customerList = customerList;
    }

    public void setShowCreateInvoiceButton(Button showCreateInvoiceButton) {
        this.showCreateInvoiceButton = showCreateInvoiceButton;
    }

    public void setCreateInvoicePane(GridPane createInvoicePane) {
        this.createInvoicePane = createInvoicePane;
    }

    public void setCustomer(TextField customer) {
        this.customer = customer;
    }

    public void setInvoiceNumber(TextField invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public void setInvoiceDate(DatePicker invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public void setInvoiceLines(VBox invoiceLines) {
        this.invoiceLines = invoiceLines;
    }

    public void setPlantSearchField(TextField plantSearchField) {
        this.plantSearchField = plantSearchField;
    }

    public void setPlantList(TableView<PlantViewModel> plantList) {
        this.plantList = plantList;
    }

    public void setChosenPlant(Label chosenPlant) {
        this.chosenPlant = chosenPlant;
    }

    public void setChoosePlantButton(Button choosePlantButton) {
        this.choosePlantButton = choosePlantButton;
    }

    public void setOrderNumber(TextField orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setInvoiceLineDate(DatePicker invoiceLineDate) {
        this.invoiceLineDate = invoiceLineDate;
    }

    public void setInvoiceLineBtw(TextField invoiceLineBtw) {
        this.invoiceLineBtw = invoiceLineBtw;
    }

    public void setAmount(TextField amount) {
        this.amount = amount;
    }

    public void setAlternativePlantPrice(TextField alternativePlantPrice) {
        this.alternativePlantPrice = alternativePlantPrice;
    }

    public void setInvoiceCreateButton(Button invoiceCreateButton) {
        this.invoiceCreateButton = invoiceCreateButton;
    }

    public void init(Pane root) {
        root.cursorProperty()
                .bind(Bindings.when(loadAllCustomersService.runningProperty()
                            .or(loadAllCustomersWithNameService.runningProperty()
                            .or(loadAllPlantsWithNameService.runningProperty()
                            .or(initInvoiceCreateService.runningProperty()
                            .or(choosePlantService.runningProperty()
                            .or(clearInvoiceLineService.runningProperty()
                            .or(createInvoiceLineService.runningProperty()
                            .or(createInvoiceService.runningProperty()))))))))
                        .then(Cursor.WAIT)
                        .otherwise(Cursor.DEFAULT)
                );
        invoiceCreateButton.disableProperty()
                .bind(createInvoiceService.runningProperty());

        initInvoiceCreateService.setOnSucceeded(serviceEvent -> {
            showCreateInvoiceButton.setDisable(true);
            customerList.setDisable(true);
            customerSearchField.setDisable(true);
            createInvoicePane.setVisible(true);
            clearInvoiceLineService.restart();
        });
        choosePlantService.setOnSucceeded(serviceEvent -> {
            plantList.setVisible(false);
            plantList.setManaged(false);
            plantSearchField.setDisable(true);
            choosePlantButton.setVisible(false);
            choosePlantButton.setManaged(false);
        });
        createInvoiceLineService.setOnSucceeded(serviceEvent -> {
            clearInvoiceLineService.restart();
        });
        createInvoiceLineService.setOnFailed(serviceEvent -> {
            ServiceHandler.error(createInvoiceLineService);
        });
        createInvoiceService.setOnSucceeded(serviceEvent -> {
            showCreateInvoiceButton.setDisable(false);
            customerList.setDisable(false);
            customerList.getSelectionModel().clearSelection();
            customerSearchField.setDisable(false);
            createInvoicePane.setVisible(false);
            invoiceLines.getChildren().clear();
            clearInvoiceLineService.restart();

            ServiceHandler.success(createInvoiceService);
        });
        createInvoiceService.setOnFailed(serviceEvent -> {
            ServiceHandler.error(createInvoiceService);
        });
    }

    private void addInvoiceLine(String chosenPlant, String plantSearchField, String orderNumber, LocalDate invoiceLineDate, String amount, String invoiceLineBtw, String alternativePlantPrice) {
        HBox invoiceLine = new HBox(5);

        Label createdChosenPlant = new Label(chosenPlant);
        createdChosenPlant.setManaged(false);
        createdChosenPlant.setVisible(false);
        TextField createdPlant = new TextField(plantSearchField);
        createdPlant.setPrefColumnCount(37);
        createdPlant.setDisable(true);
        TextField createdOrderNumber = new TextField(orderNumber);
        createdOrderNumber.setPrefColumnCount(10);
        createdOrderNumber.setDisable(true);
        DatePicker createdInvoiceLineDate = new DatePicker(invoiceLineDate);
        createdInvoiceLineDate.setDisable(true);
        TextField createdAmount = new TextField(amount);
        createdAmount.setPrefColumnCount(3);
        createdAmount.setDisable(true);
        TextField createdInvoiceLineBtw = new TextField(invoiceLineBtw);
        createdInvoiceLineBtw.setPrefColumnCount(3);
        createdInvoiceLineBtw.setDisable(true);
        TextField createdPrice = new TextField(alternativePlantPrice);
        createdPrice.setPrefColumnCount(5);
        createdPrice.setDisable(true);
        Button deleteRow = new Button("Verwijder rij");
        deleteRow.getStyleClass().add("red-button");
        deleteRow.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                invoiceLines.getChildren().remove(invoiceLine);
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
        invoiceLines.getChildren().add(invoiceLine);
    }
}
