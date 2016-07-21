package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.controller.CustomerController;
import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.util.SearchResult;
import be.boomkwekerij.plant.view.mapper.CustomerViewMapper;
import be.boomkwekerij.plant.view.model.CustomerViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CustomerListController implements Initializable{

    private CustomerController customerController = new CustomerController();

    private CustomerViewMapper customerViewMapper = new CustomerViewMapper();

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<CustomerViewModel> customers = new ArrayList<>();

        SearchResult<CustomerDTO> customerSearchResult = customerController.getAllCustomers();
        if (customerSearchResult.isSuccess()) {
            for (CustomerDTO customerDTO : customerSearchResult.getResults()) {
                CustomerViewModel customerViewModel = customerViewMapper.mapDTOToViewModel(customerDTO);
                customers.add(customerViewModel);
            }
        }

        customerList.getItems().setAll(customers);
    }
}
