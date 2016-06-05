package be.boomkwekerij.plant;

import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.model.repository.Customer;
import be.boomkwekerij.plant.service.CustomerService;
import be.boomkwekerij.plant.service.CustomerServiceImpl;
import be.boomkwekerij.plant.util.SearchResult;
import be.boomkwekerij.plant.util.SystemRepository;

public class PlantClient {

    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Data uri not specified!");
        }

        String dataUri = args[0];
        SystemRepository.init(dataUri);

        CustomerService customerService = new CustomerServiceImpl();

        //for (int i = 1; i < 1000; i++) {
        //    CustomerDTO customerDTO = new CustomerDTO();
        //    customerDTO.setName1(i + "name");
        //    customerDTO.setAddress1(i + "adres");
        //    customerDTO.setTelephone(i + "telefoon");
        //    customerDTO.setBtwNumber(i + "btwnummer");
        //    customerService.createCustomer(customerDTO);
        //}

        SearchResult<Customer> allCustomers = customerService.getAllCustomers();
        System.out.println("Gevonden: " + allCustomers.isSuccess() + ", aantal: " + allCustomers.getResults().size());

        SearchResult<Customer> customersWithName = customerService.getAllCustomers("1na");
        System.out.println("Gevonden: " + customersWithName.isSuccess() + ", aantal: " + customersWithName.getResults().size());

        SearchResult<Customer> customersWitUnknownName = customerService.getAllCustomers("te");
        System.out.println("Gevonden: " + customersWitUnknownName.isSuccess() + ", aantal: " + customersWitUnknownName.getResults().size());
    }
}
