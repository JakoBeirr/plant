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
import be.boomkwekerij.plant.validator.InvoiceValidator;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class InvoiceServiceImpl implements InvoiceService {

    private static final String EMPTY_BTW_NUMBER = "/";

    private InvoiceDAO invoiceDAO = new InvoiceDAOImpl();
    private InvoiceMemory invoiceMemory = MemoryDatabase.getInvoiceMemory();

    private InvoiceMapper invoiceMapper = new InvoiceMapper();
    private InvoiceValidator invoiceValidator = new InvoiceValidator();

    private SystemService systemService = new SystemServiceImpl();
    private CustomerService customerService = new CustomerServiceImpl();

    public CrudsResult createInvoice(InvoiceDTO invoiceDTO) {
        CrudsResult crudsResult = validateInvoice(invoiceDTO);
        if (crudsResult != null) {
            return crudsResult;
        }

        Invoice invoice = invoiceMapper.mapDTOToDAO(invoiceDTO);
        crudsResult = invoiceDAO.persist(invoice);

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
            if (invoice != null) {
                InvoiceDTO invoiceDTO = invoiceMapper.mapDAOToDTO(invoice);
                invoiceDTO.setDefaultBtw(getBtw(invoiceDTO.getCustomer()));
                invoiceSearchResult.addResult(invoiceDTO);
            }
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

        sortInvoicesByDate(allInvoices);

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

        sortInvoicesByDate(allInvoicesWithInvoiceNumber);

        SearchResult<InvoiceDTO> allInvoicesWithNameSearchResult = new SearchResult<InvoiceDTO>();
        allInvoicesWithNameSearchResult.setSuccess(searchResult.isSuccess());
        allInvoicesWithNameSearchResult.setMessages(searchResult.getMessages());
        allInvoicesWithNameSearchResult.setResults(allInvoicesWithInvoiceNumber);
        return allInvoicesWithNameSearchResult;
    }

    @Override
    public SearchResult<InvoiceDTO> getAllInvoicesFromCustomer(String customerId) {
        SearchResult<Invoice> searchResult = invoiceMemory.getInvoicesFromCustomer(customerId);

        List<InvoiceDTO> allInvoicesFromCustomer = new ArrayList<InvoiceDTO>();
        if (searchResult.isSuccess()) {
            for (Invoice invoice : searchResult.getResults()) {
                InvoiceDTO invoiceDTO = invoiceMapper.mapDAOToDTO(invoice);
                allInvoicesFromCustomer.add(invoiceDTO);
            }
        }

        sortInvoicesByDate(allInvoicesFromCustomer);

        SearchResult<InvoiceDTO> allInvoicesFromCustomerSearchResult = new SearchResult<InvoiceDTO>();
        allInvoicesFromCustomerSearchResult.setSuccess(searchResult.isSuccess());
        allInvoicesFromCustomerSearchResult.setMessages(searchResult.getMessages());
        allInvoicesFromCustomerSearchResult.setResults(allInvoicesFromCustomer);
        return allInvoicesFromCustomerSearchResult;
    }

    private void sortInvoicesByDate(List<InvoiceDTO> invoices) {
        Collections.sort(invoices, new Comparator<InvoiceDTO>() {
            public int compare(InvoiceDTO o1, InvoiceDTO o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
    }

    public CrudsResult updateInvoice(InvoiceDTO invoiceDTO) {
        CrudsResult crudsResult = validateInvoice(invoiceDTO);
        if (crudsResult != null) {
            return crudsResult;
        }

        Invoice invoice = invoiceMapper.mapDTOToDAO(invoiceDTO);
        crudsResult = invoiceDAO.update(invoice);

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
        invoiceDTO.setDefaultBtw(getBtw(customerDTO));
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
        if (StringUtils.isBlank(customerDTO.getBtwNumber()) || EMPTY_BTW_NUMBER.equals(customerDTO.getBtwNumber())) {
            return 0.0;
        }

        String country = customerDTO.getCountry();
        if (country != null) {
            if (country.equals("NL")) {
                return 0.0;
            } else if (country.equals("BE")) {
                return 6.0;
            }
        }
        return 21.0;
    }

    @Override
    public CrudsResult payInvoice(String id) {
        CrudsResult crudsResult = new CrudsResult();

        SearchResult<InvoiceDTO> invoiceResult = getInvoice(id);
        if (invoiceResult.isSuccess()) {
            InvoiceDTO invoice = invoiceResult.getFirst();
            invoice.setPayed(true);
            CrudsResult updateInvoiceResult = updateInvoice(invoice);

            crudsResult.setSuccess(updateInvoiceResult.isSuccess());
            crudsResult.setMessages(updateInvoiceResult.getMessages());
            crudsResult.setValue(updateInvoiceResult.getValue());
        } else {
            crudsResult.setSuccess(false);
            crudsResult.setMessages(invoiceResult.getMessages());
        }

        return crudsResult;
    }

    @Override
    public CrudsResult unPayInvoice(String id) {
        CrudsResult crudsResult = new CrudsResult();

        SearchResult<InvoiceDTO> invoiceResult = getInvoice(id);
        if (invoiceResult.isSuccess()) {
            InvoiceDTO invoice = invoiceResult.getFirst();
            invoice.setPayed(false);
            CrudsResult updateInvoiceResult = updateInvoice(invoice);

            crudsResult.setSuccess(updateInvoiceResult.isSuccess());
            crudsResult.setMessages(updateInvoiceResult.getMessages());
            crudsResult.setValue(updateInvoiceResult.getValue());
        } else {
            crudsResult.setSuccess(false);
            crudsResult.setMessages(invoiceResult.getMessages());
        }

        return crudsResult;
    }

    private CrudsResult validateInvoice(InvoiceDTO invoiceDTO) {
        List<String> validationResult = invoiceValidator.validate(invoiceDTO);
        if (validationResult.size() > 0) {
            CrudsResult crudsResult = new CrudsResult();
            crudsResult.setSuccess(false);
            crudsResult.setMessages(validationResult);
            return crudsResult;
        }
        return null;
    }
}
