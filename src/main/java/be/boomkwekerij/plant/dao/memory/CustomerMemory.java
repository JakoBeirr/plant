package be.boomkwekerij.plant.dao.memory;

import be.boomkwekerij.plant.model.repository.Customer;
import be.boomkwekerij.plant.util.SearchResult;

import java.util.List;

public interface CustomerMemory {

    void setCustomersInMemory(List<Customer> customers);

    void addCustomerToMemory(Customer customer);

    SearchResult<Customer> getCustomer(String id);

    SearchResult<Customer> getCustomers();

    SearchResult<Customer> getCustomersWithName(String name);
}
