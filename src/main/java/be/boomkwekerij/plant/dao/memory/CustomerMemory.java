package be.boomkwekerij.plant.dao.memory;

import be.boomkwekerij.plant.model.repository.Customer;
import be.boomkwekerij.plant.util.SearchResult;

import java.util.List;

public interface CustomerMemory {

    void addCustomerToMemory(Customer customer);

    void setCustomersInMemory(List<Customer> customers);

    SearchResult<Customer> getCustomersWithName(String name);
}
