package be.boomkwekerij.plant.util;

import be.boomkwekerij.plant.dao.repository.CompanyDAO;
import be.boomkwekerij.plant.dao.repository.CompanyDAOImpl;
import be.boomkwekerij.plant.dao.repository.CustomerDAO;
import be.boomkwekerij.plant.dao.repository.CustomerDAOImpl;
import be.boomkwekerij.plant.dao.repository.InvoiceDAO;
import be.boomkwekerij.plant.dao.repository.InvoiceDAOImpl;
import be.boomkwekerij.plant.dao.repository.PlantDAO;
import be.boomkwekerij.plant.dao.repository.PlantDAOImpl;
import be.boomkwekerij.plant.dao.repository.SystemDAO;
import be.boomkwekerij.plant.dao.repository.SystemDAOImpl;
import be.boomkwekerij.plant.model.repository.Company;
import be.boomkwekerij.plant.model.repository.Customer;
import be.boomkwekerij.plant.model.repository.Invoice;
import be.boomkwekerij.plant.model.repository.Plant;
import be.boomkwekerij.plant.model.repository.System;
import org.joda.time.DateTime;

import java.io.File;
import java.util.Date;
import java.util.List;

public class Initializer {

    private static CompanyDAO companyDAO;
    private static CustomerDAO customerDAO;
    private static InvoiceDAO invoiceDAO;
    private static PlantDAO plantDAO;
    private static SystemDAO systemDAO;

    private static String data_directory;

    public static void init(String dataUri) {
        data_directory = dataUri;

        createDAOs();

        initDirectoryStructure(dataUri);
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

    public static String getDataUri() {
        return data_directory;
    }
}
