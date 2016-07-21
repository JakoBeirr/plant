package be.boomkwekerij.plant.view.mapper;

import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.view.model.CustomerViewModel;

public class CustomerViewMapper {

    public CustomerViewModel mapDTOToViewModel(CustomerDTO customerDTO) {
        CustomerViewModel customerViewModel = new CustomerViewModel();
        customerViewModel.setId(customerDTO.getId());
        customerViewModel.setName1(customerDTO.getName1());
        customerViewModel.setName2(customerDTO.getName2());
        customerViewModel.setAddress1(customerDTO.getAddress1());
        customerViewModel.setAddress2(customerDTO.getAddress2());
        customerViewModel.setPostalCode(customerDTO.getPostalCode());
        customerViewModel.setResidence(customerDTO.getResidence());
        customerViewModel.setCountry(customerDTO.getCountry());
        customerViewModel.setTelephone(customerDTO.getTelephone());
        customerViewModel.setGsm(customerDTO.getGsm());
        customerViewModel.setFax(customerDTO.getFax());
        customerViewModel.setBtwNumber(customerDTO.getBtwNumber());
        customerViewModel.setEmailAddress(customerDTO.getEmailAddress());
        return customerViewModel;
    }
}
