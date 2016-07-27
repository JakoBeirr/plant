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

    public CustomerDTO mapViewModelToDTO(CustomerViewModel customerViewModel) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customerViewModel.getId());
        customerDTO.setName1(customerViewModel.getName1());
        customerDTO.setName2(customerViewModel.getName2());
        customerDTO.setAddress1(customerViewModel.getAddress1());
        customerDTO.setAddress2(customerViewModel.getAddress2());
        customerDTO.setPostalCode(customerViewModel.getPostalCode());
        customerDTO.setResidence(customerViewModel.getResidence());
        customerDTO.setCountry(customerViewModel.getCountry());
        customerDTO.setTelephone(customerViewModel.getTelephone());
        customerDTO.setGsm(customerViewModel.getGsm());
        customerDTO.setFax(customerViewModel.getFax());
        customerDTO.setBtwNumber(customerViewModel.getBtwNumber());
        customerDTO.setEmailAddress(customerViewModel.getEmailAddress());
        return customerDTO;
    }
}
