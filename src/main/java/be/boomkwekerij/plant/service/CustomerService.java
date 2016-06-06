package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.model.repository.Customer;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;

public interface CustomerService {

    CrudsResult createCustomer(CustomerDTO customer);

    SearchResult<CustomerDTO> getCustomer(String id);

    SearchResult<CustomerDTO> getAllCustomers();

    SearchResult<CustomerDTO> getAllCustomers(String name);

    CrudsResult updateCustomer(CustomerDTO customer);
}
