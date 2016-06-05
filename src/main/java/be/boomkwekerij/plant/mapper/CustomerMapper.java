package be.boomkwekerij.plant.mapper;

import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.model.repository.Customer;

public class CustomerMapper {

    public Customer mapDTOToDAO(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setId(customerDTO.getId());
        customer.setName1(customerDTO.getName1());
        customer.setName2(customerDTO.getName2());
        customer.setAddress1(customerDTO.getAddress1());
        customer.setAddress2(customerDTO.getAddress2());
        customer.setPostalCode(customerDTO.getPostalCode());
        customer.setResidence(customerDTO.getResidence());
        customer.setTelephone(customerDTO.getTelephone());
        customer.setGsm(customerDTO.getGsm());
        customer.setFax(customerDTO.getFax());
        customer.setBtwNumber(customerDTO.getBtwNumber());
        customer.setEmailAddress(customerDTO.getEmailAddress());
        return customer;
    }

    public CustomerDTO mapDAOToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName1(customer.getName1());
        customerDTO.setName2(customer.getName2());
        customerDTO.setAddress1(customer.getAddress1());
        customerDTO.setAddress2(customer.getAddress2());
        customerDTO.setPostalCode(customer.getPostalCode());
        customerDTO.setResidence(customer.getResidence());
        customerDTO.setTelephone(customer.getTelephone());
        customerDTO.setGsm(customer.getGsm());
        customerDTO.setFax(customer.getFax());
        customerDTO.setBtwNumber(customer.getBtwNumber());
        customerDTO.setEmailAddress(customer.getEmailAddress());
        return customerDTO;
    }
}
