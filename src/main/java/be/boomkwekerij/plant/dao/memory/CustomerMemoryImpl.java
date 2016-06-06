package be.boomkwekerij.plant.dao.memory;

import be.boomkwekerij.plant.model.repository.Customer;
import be.boomkwekerij.plant.util.SearchResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomerMemoryImpl implements CustomerMemory {

    private HashMap<String, Customer> customers = new HashMap<String, Customer>();

    public void setCustomersInMemory(List<Customer> customerList) {
        for (Customer customer : customerList) {
            customers.put(customer.getId(), customer);
        }
    }

    public void addCustomerToMemory(Customer customer) {
        customers.put(customer.getId(), customer);
    }

    public SearchResult<Customer> getCustomer(String id) {
        SearchResult<Customer> searchResult = new SearchResult<Customer>();
        searchResult.setSuccess(true);
        searchResult.addResult(customers.get(id));
        return searchResult;
    }

    public SearchResult<Customer> getCustomers() {
        SearchResult<Customer> searchResult = new SearchResult<Customer>();
        searchResult.setSuccess(true);
        searchResult.setResults(new ArrayList<Customer>(customers.values()));
        return searchResult;
    }

    public SearchResult<Customer> getCustomersWithName(String name) {
        SearchResult<Customer> customersWithName = new SearchResult<Customer>();

        for (Customer customer : customers.values()) {
            if (customerNameStartsWith(customer, name)) {
                customersWithName.addResult(customer);
            }
        }

        customersWithName.setSuccess(true);
        return customersWithName;
    }

    @SuppressWarnings("all")
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
