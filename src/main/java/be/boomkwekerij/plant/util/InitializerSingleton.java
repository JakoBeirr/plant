package be.boomkwekerij.plant.util;

import be.boomkwekerij.plant.dao.repository.*;
import be.boomkwekerij.plant.model.repository.*;
import be.boomkwekerij.plant.model.repository.System;

import java.io.File;
import java.util.Date;
import java.util.List;

public class InitializerSingleton {

    private static InitializerSingleton initializer = null;

    public static final int MAX_INVOICELINES = 18;
    private String dataDirectory;

    private CompanyDAO companyDAO;
    private CustomerDAO customerDAO;
    private InvoiceDAO invoiceDAO;
    private PlantDAO plantDAO;
    private FustDAO fustDAO;
    private SystemDAO systemDAO;

    private InitializerSingleton() {
    }

    public static InitializerSingleton getInitializer() {
        if (initializer == null) {
            initializer = new InitializerSingleton();
        }
        return initializer;
    }

    public void init() {
        companyDAO = new CompanyDAOImpl();
        customerDAO = new CustomerDAOImpl();
        invoiceDAO = new InvoiceDAOImpl();
        plantDAO = new PlantDAOImpl();
        fustDAO = new FustDAOImpl();
        systemDAO = new SystemDAOImpl();

        initDirectoryStructure();
        initInMemoryDatabase();
    }

    public String getDataDirectory() {
        return dataDirectory;
    }

    public void setDataDirectory(String dataDirectory) {
        this.dataDirectory = dataDirectory;
    }

    private void initDirectoryStructure() {
        createDataDirectoryWhenNotExists(dataDirectory);
        createDirectoryWhenNotExists(dataDirectory, "/company");
        createDirectoryWhenNotExists(dataDirectory, "/customers");
        createDirectoryWhenNotExists(dataDirectory, "/invoices");
        createDirectoryWhenNotExists(dataDirectory, "/plants");
        createDirectoryWhenNotExists(dataDirectory, "/plants");
        createDirectoryWhenNotExists(dataDirectory, "/fusts");
        createDirectoryWhenNotExists(dataDirectory, "/system");
        createDirectoryWhenNotExists(dataDirectory, "/files");
    }

    @SuppressWarnings("all")
    private void createDataDirectoryWhenNotExists(String dataUri) {
        File file = new File(dataUri);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    @SuppressWarnings("all")
    private void createDirectoryWhenNotExists(String dataUri, String directory) {
        File file = new File(dataUri + directory);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    private void initInMemoryDatabase() {
        Company company = companyDAO.get().getFirst();
        MemoryDatabase.getCompanyMemory().createCompany(company);

        List<Customer> customers = customerDAO.findAll().getResults();
        MemoryDatabase.getCustomerMemory().createCustomers(customers);

        List<Invoice> invoices = invoiceDAO.findAll().getResults();
        MemoryDatabase.getInvoiceMemory().createInvoices(invoices);

        List<Plant> plants = plantDAO.findAll().getResults();
        MemoryDatabase.getPlantMemory().createPlants(plants);

        List<Fust> fusts = fustDAO.findAll().getResults();
        MemoryDatabase.getFustMemory().createFusts(fusts);

        System system = systemDAO.get().getFirst();
        MemoryDatabase.getSystemMemory().createSystem(system);
    }

    public void reloadInMemoryDatabase() {
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
}
