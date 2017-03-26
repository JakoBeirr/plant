package be.boomkwekerij.plant.controller;

import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.model.dto.FustDTO;
import be.boomkwekerij.plant.model.dto.InvoiceDTO;
import be.boomkwekerij.plant.service.*;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;

import java.util.Collections;

public class CustomerController {

    private CustomerService customerService = new CustomerServiceImpl();
    private InvoiceService invoiceService = new InvoiceServiceImpl();
    private FustService fustService = new FustServiceImpl();

    public CrudsResult createCustomer(CustomerDTO customerDTO) {
        try {
            return customerService.createCustomer(customerDTO);
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    public SearchResult<CustomerDTO> getCustomer(String id) {
        try {
            return customerService.getCustomer(id);
        } catch (Exception e) {
            return new SearchResult<CustomerDTO>().error(Collections.singletonList(e.getMessage()));
        }
    }

    public SearchResult<CustomerDTO> getAllCustomers() {
        try {
            return customerService.getAllCustomers();
        } catch (Exception e) {
            return new SearchResult<CustomerDTO>().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult updateCustomer(CustomerDTO customerDTO) {
        try {
            return customerService.updateCustomer(customerDTO);
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult deleteCustomer(String id) {
        try {
            if (hasInvoices(id)) {
                return new CrudsResult().error(Collections.singletonList("Er zijn nog facturen gelinkt aan deze klant!"));
            }
            if (hasFust(id)) {
                return new CrudsResult().error(Collections.singletonList("Er is nog fust geregistreerd voor deze klant!"));
            }
            return customerService.deleteCustomer(id);
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    private boolean hasInvoices(String id) {
        boolean hasInvoices = false;
        SearchResult<InvoiceDTO> allInvoicesFromCustomer = invoiceService.getAllInvoicesFromCustomer(id);
        if (allInvoicesFromCustomer.isSuccess()) {
            if (allInvoicesFromCustomer.getResults().size() > 0) {
                hasInvoices = true;
            }
        }
        return hasInvoices;
    }

    private boolean hasFust(String id) {
        boolean hasFust = false;
        SearchResult<FustDTO> fustFromCustomerSearchResult = fustService.getFustFromCustomer(id);
        if (fustFromCustomerSearchResult.isSuccess()) {
            if (fustFromCustomerSearchResult.getResults().size() > 0) {
                hasFust = true;
            }
        }
        return hasFust;
    }

    public SearchResult<CustomerDTO> getAllCustomersWithName(String name) {
        try {
            return customerService.getAllCustomers(name);
        } catch (Exception e) {
            return new SearchResult<CustomerDTO>().error(Collections.singletonList(e.getMessage()));
        }
    }
}
