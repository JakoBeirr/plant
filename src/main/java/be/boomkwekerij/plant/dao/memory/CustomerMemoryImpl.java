package be.boomkwekerij.plant.dao.memory;

import be.boomkwekerij.plant.model.repository.Customer;
import be.boomkwekerij.plant.util.SearchResult;

import java.util.ArrayList;
import java.util.List;

public class CustomerMemoryImpl implements CustomerMemory {

    private List<Customer> customers = new ArrayList<Customer>();

    public void addCustomerToMemory(Customer customer) {
        customers.add(customer);
    }

    public void setCustomersInMemory(List<Customer> customers) {
        this.customers = customers;
    }

    public SearchResult<Customer> getCustomersWithName(String name) {
        SearchResult<Customer> customersWithName = new SearchResult<Customer>();

        for (Customer customer : customers) {
            if (customerNameStartsWith(customer, name)) {
                customersWithName.addResult(customer);
            }
        }

        customersWithName.setSuccess(true);
        return customersWithName;
    }

    private boolean customerNameStartsWith(Customer customer, String name) {
        if (customer.getName1() != null && customer.getName1().toUpperCase().startsWith(name.toUpperCase())) {
            return true;
        }
        if (customer.getName2() != null && customer.getName2().toUpperCase().startsWith(name.toUpperCase())) {
            return true;
        }
        return false;
    }
}
