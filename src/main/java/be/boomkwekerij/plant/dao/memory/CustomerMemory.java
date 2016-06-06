package be.boomkwekerij.plant.dao.memory;

import be.boomkwekerij.plant.model.repository.Customer;
import be.boomkwekerij.plant.util.SearchResult;

import java.util.List;

public interface CustomerMemory {

    void createCustomer(Customer customer);

    void createCustomers(List<Customer> customers);

    SearchResult<Customer> getCustomer(String id);

    SearchResult<Customer> getCustomers();

    SearchResult<Customer> getCustomers(String name);

    void deleteCustomer(String id);
}
