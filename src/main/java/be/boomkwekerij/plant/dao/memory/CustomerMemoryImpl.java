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
        if (id == null) {
            return new SearchResult<Customer>().error(Collections.singletonList("Kon geen klant vinden voor id null!"));
        }
        Customer customer = customers.get(id);
        if (customer != null) {
            return new SearchResult<Customer>().success(Collections.singletonList(customer));
        }
        return new SearchResult<Customer>().error(Collections.singletonList("Onbekende klant"));
    }

    public SearchResult<Customer> getCustomers() {
        return new SearchResult<Customer>().success(new ArrayList<Customer>(customers.values()));
    }

    public SearchResult<Customer> getCustomers(String name) {
        if (name == null) {
            return new SearchResult<Customer>().error(Collections.singletonList("Kon geen klant vinden voor naam null!"));
        } else {
            List<Customer> customersWithName = new ArrayList<>();
            for (Customer customer : customers.values()) {
                if (customerNameContains(customer, name)) {
                    customersWithName.add(customer);
                }
            }
            return new SearchResult<Customer>().success(customersWithName);
        }
    }

    @SuppressWarnings("all")
    private boolean customerNameContains(Customer customer, String name) {
        String firstName = customer.getName1() != null ? customer.getName1() : "";
        String lastName = customer.getName2() != null ? customer.getName2() : "";
        String customerName = firstName + " " + lastName;

        return customerName.trim().toUpperCase().contains(name.toUpperCase());
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
