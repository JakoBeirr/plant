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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

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
    private Button fustCreateButton;

    private FustDTO newFust = null;

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

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            customerList.getItems().setAll(customers);
                        }
                    });

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

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            customerList.getItems().setAll(customers);
                        }
                    });

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
                    newFust = fustController.makeNewFust(selectedCustomer.getId());

                    String fustCustomer = newFust.getCustomer().getName1();

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            customer.setText(fustCustomer);
                        }
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
                    updateTitle("Aanmaken fust");

                    CustomerViewModel selectedCustomer = customerList.getSelectionModel().getSelectedItem();
                    CustomerDTO selectedCustomerDTO = customerViewMapper.mapViewModelToDTO(selectedCustomer);

                    FustDTO fustDTO = new FustDTO();
                    fustDTO.setCustomer(selectedCustomerDTO);

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
