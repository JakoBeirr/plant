package be.boomkwekerij.plant.view.services;

import be.boomkwekerij.plant.controller.CustomerController;
import be.boomkwekerij.plant.controller.FustController;
import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.model.dto.FustDTO;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;
import be.boomkwekerij.plant.view.mapper.CustomerViewMapper;
import be.boomkwekerij.plant.view.model.CustomerViewModel;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.joda.time.DateTime;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FustCreateService {

    private CustomerController customerController = new CustomerController();
    private FustController fustController = new FustController();

    private CustomerViewMapper customerViewMapper = new CustomerViewMapper();

    private TextField customerSearchField;
    private TableView<CustomerViewModel> customerList;
    private Button showCreateFustButton;
    private GridPane createFustPane;
    private TextField customer;
    private TextField lageKisten;
    private TextField hogeKisten;
    private TextField palletBodem;
    private TextField boxPallet;
    private TextField halveBox;
    private TextField ferroPalletKlein;
    private TextField ferroPalletGroot;
    private TextField karrenEnBorden;
    private TextField diverse;
    private DatePicker datum;
    private Button fustCreateButton;

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

                    Platform.runLater(() -> customerList.getItems().setAll(customers));

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

                    Platform.runLater(() -> customerList.getItems().setAll(customers));

                    return null;
                }
            };
        }
    };

    public final Service initFustCreateService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Initialiseren gegevens voor bewerking");

                    CustomerViewModel selectedCustomer = customerList.getSelectionModel().getSelectedItem();

                    String fustCustomer = selectedCustomer.getName1();

                    Platform.runLater(() -> {
                        customer.setText(fustCustomer);
                        lageKisten.setText("0");
                        hogeKisten.setText("0");
                        palletBodem.setText("0");
                        boxPallet.setText("0");
                        halveBox.setText("0");
                        ferroPalletKlein.setText("0");
                        ferroPalletGroot.setText("0");
                        karrenEnBorden.setText("0");
                        diverse.setText("0");
                        datum.setValue(LocalDate.now());
                    });

                    return null;
                }
            };
        }
    };

    public final Service createFustService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Toevoegen fust");

                    CustomerViewModel selectedCustomer = customerList.getSelectionModel().getSelectedItem();
                    CustomerDTO selectedCustomerDTO = customerViewMapper.mapViewModelToDTO(selectedCustomer);

                    FustDTO fustDTO = new FustDTO();
                    fustDTO.setCustomer(selectedCustomerDTO);
                    fustDTO.setLageKisten(Integer.valueOf(lageKisten.getText()));
                    fustDTO.setHogeKisten(Integer.valueOf(hogeKisten.getText()));
                    fustDTO.setPalletBodem(Integer.valueOf(palletBodem.getText()));
                    fustDTO.setBoxPallet(Integer.valueOf(boxPallet.getText()));
                    fustDTO.setHalveBox(Integer.valueOf(halveBox.getText()));
                    fustDTO.setFerroPalletKlein(Integer.valueOf(ferroPalletKlein.getText()));
                    fustDTO.setFerroPalletGroot(Integer.valueOf(ferroPalletGroot.getText()));
                    fustDTO.setKarrenEnBorden(Integer.valueOf(karrenEnBorden.getText()));
                    fustDTO.setDiverse(Integer.valueOf(diverse.getText()));
                    LocalDate invoiceLocalDate = datum.getValue();
                    if (invoiceLocalDate != null) {
                        fustDTO.setDatum(new DateTime(invoiceLocalDate.getYear(), invoiceLocalDate.getMonthValue(), invoiceLocalDate.getDayOfMonth(), 0, 0, 0, 0));
                    } else {
                        fustDTO.setDatum(null);
                    }

                    CrudsResult createResult = fustController.createFust(fustDTO);
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

    public void setShowCreateFustButton(Button showCreateFustButton) {
        this.showCreateFustButton = showCreateFustButton;
    }

    public void setCreateFustPane(GridPane createFustPane) {
        this.createFustPane = createFustPane;
    }

    public void setCustomer(TextField customer) {
        this.customer = customer;
    }

    public void setLageKisten(TextField lageKisten) {
        this.lageKisten = lageKisten;
    }

    public void setHogeKisten(TextField hogeKisten) {
        this.hogeKisten = hogeKisten;
    }

    public void setPalletBodem(TextField palletBodem) {
        this.palletBodem = palletBodem;
    }

    public void setBoxPallet(TextField boxPallet) {
        this.boxPallet = boxPallet;
    }

    public void setHalveBox(TextField halveBox) {
        this.halveBox = halveBox;
    }

    public void setFerroPalletKlein(TextField ferroPalletKlein) {
        this.ferroPalletKlein = ferroPalletKlein;
    }

    public void setFerroPalletGroot(TextField ferroPalletGroot) {
        this.ferroPalletGroot = ferroPalletGroot;
    }

    public void setKarrenEnBorden(TextField karrenEnBorden) {
        this.karrenEnBorden = karrenEnBorden;
    }

    public void setDiverse(TextField diverse) {
        this.diverse = diverse;
    }

    public void setDatum(DatePicker datum) {
        this.datum = datum;
    }

    public void setFustCreateButton(Button fustCreateButton) {
        this.fustCreateButton = fustCreateButton;
    }

    public void init(Pane root) {
        root.cursorProperty()
                .bind(Bindings.when(loadAllCustomersService.runningProperty()
                            .or(loadAllCustomersWithNameService.runningProperty()
                            .or(initFustCreateService.runningProperty()
                            .or(createFustService.runningProperty()))))
                        .then(Cursor.WAIT)
                        .otherwise(Cursor.DEFAULT)
                );
        fustCreateButton.disableProperty()
                .bind(createFustService.runningProperty());

        initFustCreateService.setOnSucceeded(serviceEvent -> {
            showCreateFustButton.setDisable(true);
            customerList.setDisable(true);
            customerSearchField.setDisable(true);
            createFustPane.setVisible(true);
        });
        createFustService.setOnSucceeded(serviceEvent -> {
            showCreateFustButton.setDisable(false);
            customerList.setDisable(false);
            customerList.getSelectionModel().clearSelection();
            customerSearchField.setDisable(false);
            createFustPane.setVisible(false);

            ServiceHandler.success(createFustService);
        });
        createFustService.setOnFailed(serviceEvent -> {
            ServiceHandler.error(createFustService);
        });

        loadAllCustomersService.restart();
    }
}
