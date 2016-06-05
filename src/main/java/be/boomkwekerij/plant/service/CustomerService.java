package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.model.repository.Customer;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;

public interface CustomerService {

    CrudsResult createCustomer(CustomerDTO customer);

    SearchResult<Customer> getAllCustomers();

    SearchResult<Customer> getAllCustomers(String name);
}
