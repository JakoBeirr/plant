package be.boomkwekerij.plant.util;

import be.boomkwekerij.plant.dao.repository.*;
import be.boomkwekerij.plant.model.repository.System;
import be.boomkwekerij.plant.model.repository.*;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class InitializerSingleton {

    private static InitializerSingleton initializer = null;

    public static final int MAX_INVOICE_LINES = 22;
    public static final int AMOUNT_CALENDAR_YEARS_TO_KEEP_INVOICES = 3;

    private String dataDirectory;

    private CompanyDAO companyDAO;
    private CustomerDAO customerDAO;
    private InvoiceDAO invoiceDAO;
    private ArchivedInvoiceDAO archivedInvoiceDAO;
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
        archivedInvoiceDAO = new ArchivedInvoiceDAOImpl();
        plantDAO = new PlantDAOImpl();
        fustDAO = new FustDAOImpl();
        systemDAO = new SystemDAOImpl();

        initDirectoryStructure();
        initInMemoryDatabase();
        new Thread(this::archiveInvoices).start();
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
        createDirectoryWhenNotExists(dataDirectory, "/archive");
        createDirectoryWhenNotExists(dataDirectory, "/archive/invoices");
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

    private void archiveInvoices() {
        LocalDate archiveDate = LocalDate.now()
                .minusYears(AMOUNT_CALENDAR_YEARS_TO_KEEP_INVOICES)
                .plusYears(1)
                .withDayOfYear(1);
        SearchResult<Invoice> invoiceSearchResult = MemoryDatabase.getInvoiceMemory().getInvoices();

        if (invoiceSearchResult.isSuccess()) {
            for (Invoice invoice : invoiceSearchResult.getResults()) {
                LocalDate invoiceDate = invoice.getDate().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
                if (invoice.isPayed() && invoiceDate.isBefore(archiveDate)) {
                    archiveInvoice(invoice.getId());
                }
            }
        }
    }

    private void archiveInvoice(String id) {
        SearchResult<Invoice> searchResult = invoiceDAO.get(id);
        if (searchResult.isSuccess()) {
            CrudsResult createResult = archivedInvoiceDAO.persist(searchResult.getFirst());
            if (createResult.isSuccess()) {
                deleteInvoice(id);
            }
        }
    }

    public void deleteInvoice(String id) {
        CrudsResult deleteResult = invoiceDAO.delete(id);
        if (deleteResult.isSuccess()) {
            MemoryDatabase.getInvoiceMemory().deleteInvoice(id);
        }
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
