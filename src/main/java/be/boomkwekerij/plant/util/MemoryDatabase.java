package be.boomkwekerij.plant.util;

import be.boomkwekerij.plant.dao.memory.*;

public class MemoryDatabase {

    private static CompanyMemory companyMemory = new CompanyMemoryImpl();
    private static CustomerMemory customerMemory = new CustomerMemoryImpl();
    private static InvoiceMemory invoiceMemory = new InvoiceMemoryImpl();
    private static PlantMemory plantMemory = new PlantMemoryImpl();
    private static SystemMemory systemMemory = new SystemMemoryImpl();

    public static CompanyMemory getCompanyMemory() {
        return companyMemory;
    }

    public static CustomerMemory getCustomerMemory() {
        return customerMemory;
    }

    public static InvoiceMemory getInvoiceMemory() {
        return invoiceMemory;
    }

    public static PlantMemory getPlantMemory() {
        return plantMemory;
    }

    public static SystemMemory getSystemMemory() {
        return systemMemory;
    }
}
