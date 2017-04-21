package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.dao.memory.CustomerMemory;
import be.boomkwekerij.plant.helper.CustomerListOrganizer;
import be.boomkwekerij.plant.model.dto.InvoiceDTO;
import be.boomkwekerij.plant.util.MemoryDatabase;
import be.boomkwekerij.plant.dao.repository.CustomerDAO;
import be.boomkwekerij.plant.dao.repository.CustomerDAOImpl;
import be.boomkwekerij.plant.mapper.CustomerMapper;
import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.model.repository.Customer;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;
import be.boomkwekerij.plant.validator.CustomerValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    private CustomerDAO customerDAO = new CustomerDAOImpl();
    private CustomerMemory customerMemory = MemoryDatabase.getCustomerMemory();

    private CustomerMapper customerMapper = new CustomerMapper();
    private CustomerValidator customerValidator = new CustomerValidator();

    public CrudsResult createCustomer(CustomerDTO customerDTO) {
        CrudsResult validateResult = validateCustomer(customerDTO);
        if (validateResult.isError()) {
            return validateResult;
        }

        Customer customer = customerMapper.mapDTOToDAO(customerDTO);
        CrudsResult createResult = customerDAO.persist(customer);
        if (createResult.isSuccess()) {
            customerMemory.createCustomer(customer);
        }
        return createResult;
    }

    public SearchResult<CustomerDTO> getCustomer(String id) {
        SearchResult<Customer> searchResult = customerMemory.getCustomer(id);
        if (searchResult.isSuccess()) {
            Customer customer = searchResult.getFirst();
            if (customer != null) {
                CustomerDTO customerDTO = customerMapper.mapDAOToDTO(customer);
                return new SearchResult<CustomerDTO>().success(Collections.singletonList(customerDTO));
            }
        }
        return new SearchResult<CustomerDTO>().error(searchResult.getMessages());
    }

    public SearchResult<CustomerDTO> getAllCustomers() {
        SearchResult<Customer> searchResult = customerMemory.getCustomers();

        if (searchResult.isSuccess()) {
            List<CustomerDTO> allCustomers = new ArrayList<CustomerDTO>();
            for (Customer customer : searchResult.getResults()) {
                CustomerDTO customerDTO = customerMapper.mapDAOToDTO(customer);
                allCustomers.add(customerDTO);
            }
            return new SearchResult<CustomerDTO>().success(allCustomers);
        }
        return new SearchResult<CustomerDTO>().error(searchResult.getMessages());
    }

    public SearchResult<CustomerDTO> getAllCustomers(String name) {
        SearchResult<Customer> searchResult = customerMemory.getCustomers(name);
        if (searchResult.isSuccess()) {
            List<CustomerDTO> allCustomersWithName = new ArrayList<CustomerDTO>();
            for (Customer customer : searchResult.getResults()) {
                CustomerDTO customerDTO = customerMapper.mapDAOToDTO(customer);
                allCustomersWithName.add(customerDTO);
            }
            return new SearchResult<CustomerDTO>().success(CustomerListOrganizer.organize(allCustomersWithName, name));
        }
        return new SearchResult<CustomerDTO>().error(searchResult.getMessages());
    }

    public CrudsResult updateCustomer(CustomerDTO customerDTO) {
        CrudsResult validateResult = validateCustomer(customerDTO);
        if (validateResult.isError()) {
            return validateResult;
        }

        Customer customer = customerMapper.mapDTOToDAO(customerDTO);
        CrudsResult updateResult = customerDAO.update(customer);
        if (updateResult.isSuccess()) {
            customerMemory.updateCustomer(customer);
        }
        return updateResult;
    }

    public CrudsResult deleteCustomer(String id) {
        CrudsResult deleteResult = customerDAO.delete(id);
        if (deleteResult.isSuccess()) {
            customerMemory.deleteCustomer(id);
        }
        return deleteResult;
    }

    private CrudsResult validateCustomer(CustomerDTO customerDTO) {
        List<String> validationResult = customerValidator.validate(customerDTO);
        if (validationResult.size() > 0) {
            return new CrudsResult().error(validationResult);
        }
        return new CrudsResult().success(customerDTO.getId());
    }
}
