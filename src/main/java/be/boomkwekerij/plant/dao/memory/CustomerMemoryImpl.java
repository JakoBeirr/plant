package be.boomkwekerij.plant.dao.memory;

import be.boomkwekerij.plant.model.repository.Customer;
import be.boomkwekerij.plant.util.SearchResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CustomerMemoryImpl implements CustomerMemory {

    private HashMap<String, Customer> customers = new HashMap<String, Customer>();

    public void createCustomer(Customer customer) {
        customers.put(customer.getId(), customer);
    }

    public void createCustomers(List<Customer> customerList) {
        for (Customer customer : customerList) {
            customers.put(customer.getId(), customer);
        }
    }

    public SearchResult<Customer> getCustomer(String id) {
        SearchResult<Customer> searchResult = new SearchResult<Customer>();

        if (id == null) {
            searchResult.setSuccess(false);
            searchResult.addMessage("Kon geen klant vinden voor id null!");
        } else {
            searchResult.setSuccess(true);
            Customer customer = customers.get(id);
            if (customer != null) {
                searchResult.addResult(customer);
            }
        }
        return searchResult;
    }

    public SearchResult<Customer> getCustomers() {
        SearchResult<Customer> searchResult = new SearchResult<Customer>();
        searchResult.setSuccess(true);
        searchResult.setResults(new ArrayList<Customer>(customers.values()));
        return searchResult;
    }

    public SearchResult<Customer> getCustomers(String name) {
        SearchResult<Customer> customersWithName = new SearchResult<Customer>();

        if (name == null) {
            customersWithName.setSuccess(false);
            customersWithName.addMessage("Kon geen klant vinden voor naam null!");
        } else {
            customersWithName.setSuccess(true);
            for (Customer customer : customers.values()) {
                if (customerNameContains(customer, name)) {
                    customersWithName.addResult(customer);
                }
            }
        }

        return customersWithName;
    }

    @SuppressWarnings("all")
    private boolean customerNameContains(Customer customer, String name) {
        if (customer.getName1() != null && customer.getName1().toUpperCase().contains(name.toUpperCase())) {
            return true;
        }
        if (customer.getName2() != null && customer.getName2().toUpperCase().contains(name.toUpperCase())) {
            return true;
        }
        return false;
    }

    public void updateCustomer(Customer customer) {
        if (customers.get(customer.getId()) != null) {
            customers.put(customer.getId(), customer);
        }
    }

    public void deleteCustomer(String id) {
        customers.remove(id);
    }

    @Override
    public void deleteAllCustomers() {
        customers = new HashMap<>();
    }
}
