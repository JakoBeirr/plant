package be.boomkwekerij.plant.util;

import be.boomkwekerij.plant.dao.repository.CustomerDAO;
import be.boomkwekerij.plant.dao.repository.CustomerDAOImpl;
import be.boomkwekerij.plant.model.repository.Customer;

import java.io.File;
import java.util.List;

public class Initializer {

    private static CustomerDAO customerDAO;

    private static String data_directory;

    public static void init(String dataUri) {
        data_directory = dataUri;

        customerDAO = new CustomerDAOImpl();

        initDirectoryStructure(dataUri);
        initInMemoryDatabase();
    }

    public static String getDataUri() {
        return data_directory;
    }

    private static void initDirectoryStructure(String dataUri) {
        createDirectoryWhenNotExists(dataUri, "/companies");
        createDirectoryWhenNotExists(dataUri, "/customers");
        createDirectoryWhenNotExists(dataUri, "/plants");
        createDirectoryWhenNotExists(dataUri, "/invoices");
        createDirectoryWhenNotExists(dataUri, "/system");
    }

    @SuppressWarnings("all")
    private static void createDirectoryWhenNotExists(String dataUri, String directory) {
        File file = new File(dataUri + directory);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    private static void initInMemoryDatabase() {
        List<Customer> customers = customerDAO.findAll().getResults();
        MemoryDatabase.getCustomerMemory().setCustomersInMemory(customers);
    }
}
