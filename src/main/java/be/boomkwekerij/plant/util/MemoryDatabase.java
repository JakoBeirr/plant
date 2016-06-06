package be.boomkwekerij.plant.util;

import be.boomkwekerij.plant.dao.memory.CustomerMemory;
import be.boomkwekerij.plant.dao.memory.CustomerMemoryImpl;

public class MemoryDatabase {

    private static CustomerMemory customerMemory = new CustomerMemoryImpl();

    public static CustomerMemory getCustomerMemory() {
        return customerMemory;
    }
}
