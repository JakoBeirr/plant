package be.boomkwekerij.plant.view.services;

import be.boomkwekerij.plant.controller.FustController;
import be.boomkwekerij.plant.model.dto.DateDTO;
import be.boomkwekerij.plant.model.dto.FustDTO;
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
    private Button deleteFustButton;

    public final Service loadAllFustsService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Inladen alle fust");

                    SearchResult<FustDTO> searchResult = fustController.getAllFusts();
                    if (searchResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(searchResult.getMessages().toArray()));
                    }

                    List<FustViewModel> fusts = new ArrayList<>();
                    for (FustDTO fustDTO : searchResult.getResults()) {
                        FustViewModel fustViewModel = fustViewMapper.mapDTOToViewModel(fustDTO);
                        fusts.add(fustViewModel);
                    }

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            fustList.getItems().setAll(fusts);
                        }
                    });

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
                    SearchResult<FustDTO> searchResult = fustController.getFustFromCustomerWithName(customerName);
                    if (searchResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(searchResult.getMessages().toArray()));
                    }

                    List<FustViewModel> fusts = new ArrayList<>();
                    for (FustDTO fustDTO : searchResult.getResults()) {
                        FustViewModel fustViewModel = fustViewMapper.mapDTOToViewModel(fustDTO);
                        fusts.add(fustViewModel);
                    }

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            fustList.getItems().setAll(fusts);
                        }
                    });

                    return null;
                }
            };
        }
    };

    public final Service deleteFustService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Fust verwijderen");

                    ObservableList<FustViewModel> selectedFusts = fustList.getSelectionModel().getSelectedItems();
                    for (FustViewModel selectedFust : selectedFusts) {
                        CrudsResult deleteResult = fustController.deleteFust(selectedFust.getId());
                        if (deleteResult.isError()) {
                            throw new IllegalArgumentException(Arrays.toString(deleteResult.getMessages().toArray()));
                        }
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

    public void setDeleteFustButton(Button deleteFustButton) {
        this.deleteFustButton = deleteFustButton;
    }

    public void init(Pane root) {
        root.cursorProperty()
                .bind(Bindings.when(loadAllFustsService.runningProperty()
                            .or(loadAllFustFromCustomerWithName.runningProperty()
                            .or(deleteFustService.runningProperty())))
                        .then(Cursor.WAIT)
                        .otherwise(Cursor.DEFAULT)
                );
        deleteFustButton.disableProperty()
                .bind(deleteFustService.runningProperty());

        deleteFustService.setOnSucceeded(serviceEvent -> {
            if (fustSearchField.getText().length() > 2) {
                loadAllFustFromCustomerWithName.restart();
            } else {
                loadAllFustsService.restart();
            }
            ServiceHandler.success(deleteFustService);
        });
        deleteFustService.setOnFailed(serviceEvent -> {
            ServiceHandler.error(deleteFustService);
        });

        loadAllFustsService.restart();
    }
}
