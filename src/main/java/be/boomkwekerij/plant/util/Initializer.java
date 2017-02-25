package be.boomkwekerij.plant.util;

import be.boomkwekerij.plant.dao.repository.*;
import be.boomkwekerij.plant.model.repository.*;
import be.boomkwekerij.plant.model.repository.System;

import java.io.File;
import java.util.List;

public class Initializer {

    private static String data_directory;

    private static CompanyDAO companyDAO;
    private static CustomerDAO customerDAO;
    private static InvoiceDAO invoiceDAO;
    private static PlantDAO plantDAO;
    private static SystemDAO systemDAO;

    public static final int MAX_INVOICELINES = 18;

    public static void launch(String dataUri) {
        data_directory = dataUri;
    }

    public static void init() {
        createDAOs();

        initDirectoryStructure(data_directory);
        initInMemoryDatabase();
    }

    private static void createDAOs() {
        companyDAO = new CompanyDAOImpl();
        customerDAO = new CustomerDAOImpl();
        invoiceDAO = new InvoiceDAOImpl();
        plantDAO = new PlantDAOImpl();
        systemDAO = new SystemDAOImpl();
    }

    private static void initDirectoryStructure(String dataUri) {
        createDataDirectoryWhenNotExists(dataUri);
        createDirectoryWhenNotExists(dataUri, "/company");
        createDirectoryWhenNotExists(dataUri, "/customers");
        createDirectoryWhenNotExists(dataUri, "/invoices");
        createDirectoryWhenNotExists(dataUri, "/plants");
        createDirectoryWhenNotExists(dataUri, "/system");
        createDirectoryWhenNotExists(dataUri, "/files");
    }

    @SuppressWarnings("all")
    private static void createDataDirectoryWhenNotExists(String dataUri) {
        File file = new File(dataUri);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    @SuppressWarnings("all")
    private static void createDirectoryWhenNotExists(String dataUri, String directory) {
        File file = new File(dataUri + directory);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    private static void initInMemoryDatabase() {
        Company company = companyDAO.get().getFirst();
        MemoryDatabase.getCompanyMemory().createCompany(company);

        List<Customer> customers = customerDAO.findAll().getResults();
        MemoryDatabase.getCustomerMemory().createCustomers(customers);

        List<Invoice> invoices = invoiceDAO.findAll().getResults();
        MemoryDatabase.getInvoiceMemory().createInvoices(invoices);

        List<Plant> plants = plantDAO.findAll().getResults();
        MemoryDatabase.getPlantMemory().createPlants(plants);

        System system = systemDAO.get().getFirst();
        MemoryDatabase.getSystemMemory().createSystem(system);
    }

    public static void reloadInMemoryDatabase() {
        MemoryDatabase.getCompanyMemory().deleteCompany();
        MemoryDatabase.getCustomerMemory().deleteAllCustomers();
        MemoryDatabase.getInvoiceMemory().deleteAllInvoices();
        MemoryDatabase.getPlantMemory().deleteAllPlants();
        MemoryDatabase.getSystemMemory().deleteSystem();

        Company company = companyDAO.get().getFirst();
        MemoryDatabase.getCompanyMemory().createCompany(company);

        List<Customer> customers = customerDAO.findAll().getResults();
        MemoryDatabase.getCustomerMemory().createCustomers(customers);

        List<Invoice> invoices = invoiceDAO.findAll().getResults();
        MemoryDatabase.getInvoiceMemory().createInvoices(invoices);

        List<Plant> plants = plantDAO.findAll().getResults();
        MemoryDatabase.getPlantMemory().createPlants(plants);

        System system = systemDAO.get().getFirst();
        MemoryDatabase.getSystemMemory().createSystem(system);
    }

    public static String getDataUri() {
        return data_directory;
    }
}
