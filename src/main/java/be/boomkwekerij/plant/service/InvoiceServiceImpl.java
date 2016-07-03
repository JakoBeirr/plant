package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.dao.memory.InvoiceMemory;
import be.boomkwekerij.plant.dao.repository.InvoiceDAO;
import be.boomkwekerij.plant.dao.repository.InvoiceDAOImpl;
import be.boomkwekerij.plant.mapper.InvoiceMapper;
import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.model.dto.InvoiceDTO;
import be.boomkwekerij.plant.model.repository.Invoice;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.MemoryDatabase;
import be.boomkwekerij.plant.util.SearchResult;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class InvoiceServiceImpl implements InvoiceService {

    private InvoiceDAO invoiceDAO = new InvoiceDAOImpl();
    private InvoiceMemory invoiceMemory = MemoryDatabase.getInvoiceMemory();

    private InvoiceMapper invoiceMapper = new InvoiceMapper();

    private SystemService systemService = new SystemServiceImpl();
    private CustomerService customerService = new CustomerServiceImpl();

    public CrudsResult createInvoice(InvoiceDTO invoiceDTO) {
        Invoice invoice = invoiceMapper.mapDTOToDAO(invoiceDTO);
        CrudsResult crudsResult = invoiceDAO.persist(invoice);

        if (crudsResult.isSuccess()) {
            invoiceMemory.createInvoice(invoice);

            systemService.setNextInvoiceNumber(invoiceDTO.getInvoiceNumber());
        }

        return crudsResult;
    }

    public SearchResult<InvoiceDTO> getInvoice(String id) {
        SearchResult<Invoice> searchResult = invoiceMemory.getInvoice(id);

        SearchResult<InvoiceDTO> invoiceSearchResult = new SearchResult<InvoiceDTO>();
        invoiceSearchResult.setSuccess(searchResult.isSuccess());
        invoiceSearchResult.setMessages(searchResult.getMessages());

        if (searchResult.isSuccess()) {
            Invoice invoice = searchResult.getFirst();
            InvoiceDTO invoiceDTO = invoiceMapper.mapDAOToDTO(invoice);
            invoiceSearchResult.addResult(invoiceDTO);
        }

        return invoiceSearchResult;
    }

    public SearchResult<InvoiceDTO> getAllInvoices() {
        SearchResult<Invoice> searchResult = invoiceMemory.getInvoices();

        List<InvoiceDTO> allInvoices = new ArrayList<InvoiceDTO>();
        if (searchResult.isSuccess()) {
            for (Invoice invoice : searchResult.getResults()) {
                InvoiceDTO invoiceDTO = invoiceMapper.mapDAOToDTO(invoice);
                allInvoices.add(invoiceDTO);
            }
        }

        SearchResult<InvoiceDTO> allInvoicesSearchResult = new SearchResult<InvoiceDTO>();
        allInvoicesSearchResult.setSuccess(searchResult.isSuccess());
        allInvoicesSearchResult.setMessages(searchResult.getMessages());
        allInvoicesSearchResult.setResults(allInvoices);
        return allInvoicesSearchResult;
    }

    public SearchResult<InvoiceDTO> getAllInvoices(String invoiceNumber) {
        SearchResult<Invoice> searchResult = invoiceMemory.getInvoices(invoiceNumber);

        List<InvoiceDTO> allInvoicesWithInvoiceNumber = new ArrayList<InvoiceDTO>();
        if (searchResult.isSuccess()) {
            for (Invoice invoice : searchResult.getResults()) {
                InvoiceDTO invoiceDTO = invoiceMapper.mapDAOToDTO(invoice);
                allInvoicesWithInvoiceNumber.add(invoiceDTO);
            }
        }

        SearchResult<InvoiceDTO> allInvoicesWithNameSearchResult = new SearchResult<InvoiceDTO>();
        allInvoicesWithNameSearchResult.setSuccess(searchResult.isSuccess());
        allInvoicesWithNameSearchResult.setMessages(searchResult.getMessages());
        allInvoicesWithNameSearchResult.setResults(allInvoicesWithInvoiceNumber);
        return allInvoicesWithNameSearchResult;
    }

    public CrudsResult updateInvoice(InvoiceDTO invoiceDTO) {
        Invoice invoice = invoiceMapper.mapDTOToDAO(invoiceDTO);
        CrudsResult crudsResult = invoiceDAO.update(invoice);

        if (crudsResult.isSuccess()) {
            invoiceMemory.updateInvoice(invoice);
        }

        return crudsResult;
    }

    public CrudsResult deleteInvoice(String id) {
        CrudsResult crudsResult = invoiceDAO.delete(id);

        if (crudsResult.isSuccess()) {
            invoiceMemory.deleteInvoice(id);
        }

        return crudsResult;
    }

    public InvoiceDTO getNewInvoiceForCustomer(String customerId) {
        CustomerDTO customerDTO = getCustomer(customerId);

        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setInvoiceNumber(systemService.getNextInvoiceNumber());
        invoiceDTO.setCustomer(customerDTO);
        invoiceDTO.setDate(new DateTime());
        invoiceDTO.setBtw(getBtw(customerDTO));
        return invoiceDTO;
    }

    private CustomerDTO getCustomer(String customerId) {
        SearchResult<CustomerDTO> customerSearchResult = customerService.getCustomer(customerId);
        if (customerSearchResult.isSuccess()) {
            if (customerSearchResult.getResults().size() == 1) {
                return customerSearchResult.getFirst();
            }
        }
        throw new IllegalArgumentException("Could not find customer");
    }

    private double getBtw(CustomerDTO customerDTO) {
        String country = customerDTO.getCountry();
        if (country != null) {
            if (country.equals("NL")) {
                return 0.0;
            } else if (country.equals("BE")) {
                return 0.06;
            }
        }
        return 0.21;
    }
}
