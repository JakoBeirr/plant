package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.dao.memory.CustomerMemory;
import be.boomkwekerij.plant.util.MemoryDatabase;
import be.boomkwekerij.plant.dao.repository.CustomerDAO;
import be.boomkwekerij.plant.dao.repository.CustomerDAOImpl;
import be.boomkwekerij.plant.mapper.CustomerMapper;
import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.model.repository.Customer;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;

import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    private CustomerDAO customerDAO = new CustomerDAOImpl();
    private CustomerMemory customerMemory = MemoryDatabase.getCustomerMemory();

    private CustomerMapper customerMapper = new CustomerMapper();

    public CrudsResult createCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.mapDTOToDAO(customerDTO);
        CrudsResult crudsResult = customerDAO.persist(customer);

        if (crudsResult.isSuccess()) {
            customerMemory.createCustomer(customer);
        }

        return crudsResult;
    }

    public SearchResult<CustomerDTO> getCustomer(String id) {
        SearchResult<Customer> searchResult = customerMemory.getCustomer(id);

        SearchResult<CustomerDTO> customerSearchResult = new SearchResult<CustomerDTO>();
        customerSearchResult.setSuccess(searchResult.isSuccess());
        customerSearchResult.setMessages(searchResult.getMessages());

        if (searchResult.isSuccess()) {
            Customer customer = searchResult.getFirst();
            CustomerDTO customerDTO = customerMapper.mapDAOToDTO(customer);
            customerSearchResult.addResult(customerDTO);
        }

        return customerSearchResult;
    }

    public SearchResult<CustomerDTO> getAllCustomers() {
        SearchResult<Customer> searchResult = customerMemory.getCustomers();

        List<CustomerDTO> allCustomers = new ArrayList<CustomerDTO>();
        if (searchResult.isSuccess()) {
            for (Customer customer : searchResult.getResults()) {
                CustomerDTO customerDTO = customerMapper.mapDAOToDTO(customer);
                allCustomers.add(customerDTO);
            }
        }

        SearchResult<CustomerDTO> allCustomersSearchResult = new SearchResult<CustomerDTO>();
        allCustomersSearchResult.setSuccess(searchResult.isSuccess());
        allCustomersSearchResult.setMessages(searchResult.getMessages());
        allCustomersSearchResult.setResults(allCustomers);
        return allCustomersSearchResult;
    }

    public SearchResult<CustomerDTO> getAllCustomers(String name) {
        SearchResult<Customer> searchResult = customerMemory.getCustomers(name);

        List<CustomerDTO> allCustomersWithName = new ArrayList<CustomerDTO>();
        if (searchResult.isSuccess()) {
            for (Customer customer : searchResult.getResults()) {
                CustomerDTO customerDTO = customerMapper.mapDAOToDTO(customer);
                allCustomersWithName.add(customerDTO);
            }
        }

        SearchResult<CustomerDTO> allCustomersWithNameSearchResult = new SearchResult<CustomerDTO>();
        allCustomersWithNameSearchResult.setSuccess(searchResult.isSuccess());
        allCustomersWithNameSearchResult.setMessages(searchResult.getMessages());
        allCustomersWithNameSearchResult.setResults(allCustomersWithName);
        return allCustomersWithNameSearchResult;
    }

    public CrudsResult updateCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.mapDTOToDAO(customerDTO);
        CrudsResult crudsResult = customerDAO.update(customer);

        if (crudsResult.isSuccess()) {
            customerMemory.updateCustomer(customer);
        }

        return crudsResult;
    }

    public CrudsResult deleteCustomer(String id) {
        CrudsResult crudsResult = customerDAO.delete(id);

        if (crudsResult.isSuccess()) {
            customerMemory.deleteCustomer(id);
        }

        return crudsResult;
    }
}
