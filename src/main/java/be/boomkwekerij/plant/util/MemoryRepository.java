package be.boomkwekerij.plant.util;

import be.boomkwekerij.plant.service.CustomerService;
import be.boomkwekerij.plant.service.CustomerServiceImpl;

public class MemoryRepository {

    private static CustomerService customerService = new CustomerServiceImpl();

    public static void init() {
        customerService.getAllCustomers();
    }
}
