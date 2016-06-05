package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.dao.memory.CustomerMemory;
import be.boomkwekerij.plant.dao.memory.CustomerMemoryImpl;
import be.boomkwekerij.plant.dao.repository.CustomerDAO;
import be.boomkwekerij.plant.dao.repository.CustomerDAOImpl;
import be.boomkwekerij.plant.mapper.CustomerMapper;
import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.model.repository.Customer;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;

public class CustomerServiceImpl implements CustomerService {

    private CustomerDAO customerDAO = new CustomerDAOImpl();
    private CustomerMemory customerMemory = new CustomerMemoryImpl();

    private CustomerMapper customerMapper = new CustomerMapper();

    public CrudsResult createCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.mapDTOToDAO(customerDTO);
        CrudsResult crudsResult = customerDAO.persist(customer);

        if(crudsResult.isSuccess()) {
            customerMemory.addCustomerToMemory(customer);
        }

        return crudsResult;
    }

    public SearchResult<Customer> getAllCustomers() {
        SearchResult<Customer> searchResult = customerDAO.findAll();

        if(searchResult.isSuccess()) {
            customerMemory.setCustomersInMemory(searchResult.getResults());
        }

        return searchResult;
    }

    public SearchResult<Customer> getAllCustomers(String name) {
        return customerMemory.getCustomersWithName(name);
    }
}
