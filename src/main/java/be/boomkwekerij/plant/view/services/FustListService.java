package be.boomkwekerij.plant.view.services;

import be.boomkwekerij.plant.controller.FustController;
import be.boomkwekerij.plant.model.dto.FustDTO;
import be.boomkwekerij.plant.model.dto.FustOverviewDTO;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;
import be.boomkwekerij.plant.view.mapper.FustViewMapper;
import be.boomkwekerij.plant.view.model.FustViewModel;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FustListService {

    private FustController fustController = new FustController();

    private FustViewMapper fustViewMapper = new FustViewMapper();

    private TextField fustSearchField;
    private TableView<FustViewModel> fustList;
    private Button printFustButton;
    private Button printFustsButton;

    public final Service loadAllFustsService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Inladen alle fust");

                    SearchResult<FustOverviewDTO> searchResult = fustController.getAllFustOverviews();
                    if (searchResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(searchResult.getMessages().toArray()));
                    }

                    List<FustViewModel> fusts = new ArrayList<>();
                    for (FustOverviewDTO fust : searchResult.getResults()) {
                        FustViewModel fustViewModel = fustViewMapper.mapDTOToViewModel(fust);
                        fusts.add(fustViewModel);
                    }

                    Platform.runLater(() -> fustList.getItems().setAll(fusts));

                    return null;
                }
            };
        }
    };

    public final Service loadAllFustFromCustomerWithName = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Inladen alle fust voor klant met naam");

                    String customerName = fustSearchField.getText();
                    SearchResult<FustOverviewDTO> searchResult = fustController.getFustOverviewFromCustomerWithName(customerName);
                    if (searchResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(searchResult.getMessages().toArray()));
                    }

                    List<FustViewModel> fusts = new ArrayList<>();
                    for (FustOverviewDTO fust : searchResult.getResults()) {
                        FustViewModel fustViewModel = fustViewMapper.mapDTOToViewModel(fust);
                        fusts.add(fustViewModel);
                    }

                    Platform.runLater(() -> fustList.getItems().setAll(fusts));

                    return null;
                }
            };
        }
    };

    public final Service printFustService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Fust printen");

                    ObservableList<FustViewModel> selectedFusts = fustList.getSelectionModel().getSelectedItems();
                    for (FustViewModel selectedFust : selectedFusts) {
                        CrudsResult printResult = fustController.printFustFromCustomerReport(selectedFust.getCustomerId());
                        if (printResult.isError()) {
                            throw new IllegalArgumentException(Arrays.toString(printResult.getMessages().toArray()));
                        }
                    }

                    return null;
                }
            };
        }
    };

    public final Service printFustsService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Totaaloverzicht fust printen");

                    CrudsResult printResult = fustController.printFustFromAllCustomersReport();
                    if (printResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(printResult.getMessages().toArray()));
                    }

                    return null;
                }
            };
        }
    };

    public void setFustSearchField(TextField fustSearchField) {
        this.fustSearchField = fustSearchField;
    }

    public void setFustList(TableView<FustViewModel> fustList) {
        this.fustList = fustList;
    }

    public void setPrintFustButton(Button printFustButton) {
        this.printFustButton = printFustButton;
    }

    public void setPrintFustsButton(Button printFustsButton) {
        this.printFustsButton = printFustsButton;
    }

    public void init(Pane root) {
        root.cursorProperty()
                .bind(Bindings.when(loadAllFustsService.runningProperty()
                            .or(loadAllFustFromCustomerWithName.runningProperty()
                            .or(printFustService.runningProperty()
                            .or(printFustsService.runningProperty()))))
                        .then(Cursor.WAIT)
                        .otherwise(Cursor.DEFAULT)
                );
        printFustButton.disableProperty()
                .bind(printFustService.runningProperty());
        printFustsButton.disableProperty()
                .bind(printFustsService.runningProperty());

        printFustService.setOnSucceeded(serviceEvent -> {
            ServiceHandler.success(printFustService);
        });
        printFustService.setOnFailed(serviceEvent -> {
            ServiceHandler.error(printFustService);
        });
        printFustsService.setOnSucceeded(serviceEvent -> {
            ServiceHandler.success(printFustsService);
        });
        printFustsService.setOnFailed(serviceEvent -> {
            ServiceHandler.error(printFustsService);
        });

        loadAllFustsService.restart();
    }
}
