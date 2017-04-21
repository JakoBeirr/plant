package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.dao.memory.InvoiceMemory;
import be.boomkwekerij.plant.dao.repository.InvoiceDAO;
import be.boomkwekerij.plant.dao.repository.InvoiceDAOImpl;
import be.boomkwekerij.plant.helper.InvoiceListOrganizer;
import be.boomkwekerij.plant.mapper.InvoiceMapper;
import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.model.dto.DateDTO;
import be.boomkwekerij.plant.model.dto.InvoiceDTO;
import be.boomkwekerij.plant.model.repository.Invoice;
import be.boomkwekerij.plant.util.CountryCode;
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
        CrudsResult validateResult = validateInvoice(invoiceDTO);
        if (validateResult.isError()) {
            return validateResult;
        }

        Invoice invoice = invoiceMapper.mapDTOToDAO(invoiceDTO);
        CrudsResult createResult = invoiceDAO.persist(invoice);
        if (createResult.isSuccess()) {
            invoiceMemory.createInvoice(invoice);
            systemService.setNextInvoiceNumber(invoiceDTO.getInvoiceNumber());
        }
        return createResult;
    }

    public SearchResult<InvoiceDTO> getInvoice(String id) {
        SearchResult<Invoice> searchResult = invoiceMemory.getInvoice(id);
        if (searchResult.isSuccess()) {
            Invoice invoice = searchResult.getFirst();
            if (invoice != null) {
                SearchResult<CustomerDTO> customerSearchResult = customerService.getCustomer(invoice.getCustomerId());
                if (customerSearchResult.isError()) {
                    return new SearchResult<InvoiceDTO>().error(customerSearchResult.getMessages());
                }
                CustomerDTO customer = customerSearchResult.getFirst();

                InvoiceDTO invoiceDTO = invoiceMapper.mapDAOToDTO(invoice, customer);
                invoiceDTO.setDefaultBtw(determineBtw(invoiceDTO.getCustomer()));
                return new SearchResult<InvoiceDTO>().success(Collections.singletonList(invoiceDTO));
            }
        }
        return new SearchResult<InvoiceDTO>().error(searchResult.getMessages());
    }

    public SearchResult<InvoiceDTO> getAllInvoices() {
        SearchResult<Invoice> searchResult = invoiceMemory.getInvoices();
        if (searchResult.isSuccess()) {
            List<InvoiceDTO> allInvoices = new ArrayList<InvoiceDTO>();
            for (Invoice invoice : searchResult.getResults()) {
                SearchResult<CustomerDTO> customerSearchResult = customerService.getCustomer(invoice.getCustomerId());
                if (customerSearchResult.isError()) {
                    return new SearchResult<InvoiceDTO>().error(customerSearchResult.getMessages());
                }
                CustomerDTO customer = customerSearchResult.getFirst();

                InvoiceDTO invoiceDTO = invoiceMapper.mapDAOToDTO(invoice, customer);
                allInvoices.add(invoiceDTO);
            }
            sortInvoicesByDate(allInvoices);
            return new SearchResult<InvoiceDTO>().success(allInvoices);
        }
        return new SearchResult<InvoiceDTO>().error(searchResult.getMessages());
    }

    public SearchResult<InvoiceDTO> getAllInvoices(String invoiceNumber) {
        SearchResult<Invoice> searchResult = invoiceMemory.getInvoices(invoiceNumber);
        if (searchResult.isSuccess()) {
            List<InvoiceDTO> allInvoicesWithInvoiceNumber = new ArrayList<InvoiceDTO>();
            for (Invoice invoice : searchResult.getResults()) {
                SearchResult<CustomerDTO> customerSearchResult = customerService.getCustomer(invoice.getCustomerId());
                if (customerSearchResult.isError()) {
                    return new SearchResult<InvoiceDTO>().error(customerSearchResult.getMessages());
                }
                CustomerDTO customer = customerSearchResult.getFirst();

                InvoiceDTO invoiceDTO = invoiceMapper.mapDAOToDTO(invoice, customer);
                allInvoicesWithInvoiceNumber.add(invoiceDTO);
            }
            sortInvoicesByDate(allInvoicesWithInvoiceNumber);
            return new SearchResult<InvoiceDTO>().success(InvoiceListOrganizer.organize(allInvoicesWithInvoiceNumber, invoiceNumber));
        }
        return new SearchResult<InvoiceDTO>().error(searchResult.getMessages());
    }

    @Override
    public SearchResult<InvoiceDTO> getAllInvoicesFromCustomer(String customerId) {
        SearchResult<Invoice> searchResult = invoiceMemory.getInvoicesFromCustomer(customerId);
        if (searchResult.isSuccess()) {
            List<InvoiceDTO> allInvoicesFromCustomer = new ArrayList<InvoiceDTO>();
            for (Invoice invoice : searchResult.getResults()) {
                SearchResult<CustomerDTO> customerSearchResult = customerService.getCustomer(invoice.getCustomerId());
                if (customerSearchResult.isError()) {
                    return new SearchResult<InvoiceDTO>().error(customerSearchResult.getMessages());
                }
                CustomerDTO customer = customerSearchResult.getFirst();

                InvoiceDTO invoiceDTO = invoiceMapper.mapDAOToDTO(invoice, customer);
                allInvoicesFromCustomer.add(invoiceDTO);
            }
            sortInvoicesByDate(allInvoicesFromCustomer);
            return new SearchResult<InvoiceDTO>().success(allInvoicesFromCustomer);
        }
        return new SearchResult<InvoiceDTO>().error(searchResult.getMessages());
    }

    private void sortInvoicesByDate(List<InvoiceDTO> invoices) {
        Collections.sort(invoices, (o1, o2) -> o1.getDate().compareTo(o2.getDate()));
    }

    public CrudsResult updateInvoice(InvoiceDTO invoiceDTO) {
        CrudsResult validateResult = validateInvoice(invoiceDTO);
        if (validateResult.isError()) {
            return validateResult;
        }

        Invoice invoice = invoiceMapper.mapDTOToDAO(invoiceDTO);
        CrudsResult updateResult = invoiceDAO.update(invoice);
        if (updateResult.isSuccess()) {
            invoiceMemory.updateInvoice(invoice);
        }
        return updateResult;
    }

    public CrudsResult deleteInvoice(String id) {
        CrudsResult deleteResult = invoiceDAO.delete(id);
        if (deleteResult.isSuccess()) {
            invoiceMemory.deleteInvoice(id);
        }
        return deleteResult;
    }

    public InvoiceDTO getNewInvoiceForCustomer(String customerId) {
        CustomerDTO customerDTO = getCustomer(customerId);
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setInvoiceNumber(systemService.getNextInvoiceNumber());
        invoiceDTO.setCustomer(customerDTO);
        invoiceDTO.setDate(new DateTime());
        invoiceDTO.setDefaultBtw(determineBtw(customerDTO));
        return invoiceDTO;
    }

    private CustomerDTO getCustomer(String customerId) {
        SearchResult<CustomerDTO> searchResult = customerService.getCustomer(customerId);
        if (searchResult.isSuccess()) {
            if (searchResult.getResults().size() == 1) {
                return searchResult.getFirst();
            }
        }
        throw new IllegalArgumentException("Could not find customer");
    }

    private double determineBtw(CustomerDTO customerDTO) {
        if (isNoForeignerWithoutBtwNumber(customerDTO) || isForeignerWithBtwNumber(customerDTO)) {
            return 0.0;
        }
        if (isNoForeignerWithBtwNumber(customerDTO) || isForeignerWithoutBtwNumber(customerDTO)) {
            return 6.0;
        }
        return 21.0;
    }

    private boolean isNoForeignerWithoutBtwNumber(CustomerDTO customerDTO) {
        return !isForeigner(customerDTO) && noBtwNumber(customerDTO);
    }

    private boolean isForeignerWithBtwNumber(CustomerDTO customerDTO) {
        return isForeigner(customerDTO) && !noBtwNumber(customerDTO);
    }

    private boolean isNoForeignerWithBtwNumber(CustomerDTO customerDTO) {
        return !isForeigner(customerDTO) && !noBtwNumber(customerDTO);
    }

    private boolean isForeignerWithoutBtwNumber(CustomerDTO customerDTO) {
        return isForeigner(customerDTO) && noBtwNumber(customerDTO);
    }

    private boolean isForeigner(CustomerDTO customerDTO) {
        return !customerDTO.getCountry().equals(CountryCode.BELGIUM.code());
    }

    private boolean noBtwNumber(CustomerDTO customerDTO) {
        return StringUtils.isBlank(customerDTO.getBtwNumber()) || EMPTY_BTW_NUMBER.equals(customerDTO.getBtwNumber());
    }

    @Override
    public CrudsResult payInvoice(String id, DateDTO dateDTO) {
        SearchResult<InvoiceDTO> searchResult = getInvoice(id);
        if (searchResult.isSuccess()) {
            InvoiceDTO invoice = searchResult.getFirst();
            invoice.setPayed(true);
            invoice.setPayDate(dateDTO.getPayDate());
            CrudsResult updateResult = updateInvoice(invoice);
            if (updateResult.isSuccess()) {
                return new CrudsResult().success(id);
            }
            return new CrudsResult().error(updateResult.getMessages());
        } else {
            return new CrudsResult().error(searchResult.getMessages());
        }
    }

    @Override
    public CrudsResult unPayInvoice(String id) {
        SearchResult<InvoiceDTO> searchResult = getInvoice(id);
        if (searchResult.isSuccess()) {
            InvoiceDTO invoice = searchResult.getFirst();
            invoice.setPayed(false);
            invoice.setPayDate(null);
            CrudsResult updateResult = updateInvoice(invoice);
            if (updateResult.isSuccess()) {
                return new CrudsResult().success(id);
            }
            return new CrudsResult().error(updateResult.getMessages());
        } else {
            return new CrudsResult().error(searchResult.getMessages());
        }
    }

    private CrudsResult validateInvoice(InvoiceDTO invoiceDTO) {
        List<String> validationResult = invoiceValidator.validate(invoiceDTO);
        if (validationResult.size() > 0) {
            return new CrudsResult().error(validationResult);
        }
        return new CrudsResult().success(invoiceDTO.getId());
    }
}
