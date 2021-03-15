package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.dao.repository.ArchivedInvoiceDAO;
import be.boomkwekerij.plant.dao.repository.ArchivedInvoiceDAOImpl;
import be.boomkwekerij.plant.mapper.InvoiceMapper;
import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.model.dto.InvoiceDTO;
import be.boomkwekerij.plant.model.repository.Invoice;
import be.boomkwekerij.plant.util.CountryCode;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ArchivedInvoiceServiceImpl implements ArchivedInvoiceService {

    private static final String EMPTY_BTW_NUMBER = "/";

    private final ArchivedInvoiceDAO archivedInvoiceDAO = new ArchivedInvoiceDAOImpl();

    private final InvoiceMapper invoiceMapper = new InvoiceMapper();

    private final CustomerService customerService = new CustomerServiceImpl();

    public SearchResult<InvoiceDTO> getInvoice(String id) {
        SearchResult<Invoice> searchResult = archivedInvoiceDAO.get(id);
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
        SearchResult<Invoice> searchResult = archivedInvoiceDAO.findAll();
        if (searchResult.isSuccess()) {
            List<InvoiceDTO> allArchivedInvoices = new ArrayList<>();
            for (Invoice archivedInvoice : searchResult.getResults()) {
                SearchResult<CustomerDTO> customerSearchResult = customerService.getCustomer(archivedInvoice.getCustomerId());
                if (customerSearchResult.isError()) {
                    return new SearchResult<InvoiceDTO>().error(customerSearchResult.getMessages());
                }
                CustomerDTO customer = customerSearchResult.getFirst();

                InvoiceDTO invoiceDTO = invoiceMapper.mapDAOToDTO(archivedInvoice, customer);
                allArchivedInvoices.add(invoiceDTO);
            }
            sortInvoicesByDate(allArchivedInvoices);
            return new SearchResult<InvoiceDTO>().success(allArchivedInvoices);
        }
        return new SearchResult<InvoiceDTO>().error(searchResult.getMessages());
    }

    private void sortInvoicesByDate(List<InvoiceDTO> invoices) {
        invoices.sort(Comparator.comparing(InvoiceDTO::getDate));
    }

    public CrudsResult deleteInvoice(String id) {
        return archivedInvoiceDAO.delete(id);
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
}
