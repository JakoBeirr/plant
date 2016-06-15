package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.dao.memory.InvoiceMemory;
import be.boomkwekerij.plant.dao.repository.InvoiceDAO;
import be.boomkwekerij.plant.dao.repository.InvoiceDAOImpl;
import be.boomkwekerij.plant.mapper.InvoiceMapper;
import be.boomkwekerij.plant.model.dto.InvoiceDTO;
import be.boomkwekerij.plant.model.repository.Invoice;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.MemoryDatabase;
import be.boomkwekerij.plant.util.SearchResult;

import java.util.ArrayList;
import java.util.List;

public class InvoiceServiceImpl implements InvoiceService {

    private InvoiceDAO invoiceDAO = new InvoiceDAOImpl();
    private InvoiceMemory invoiceMemory = MemoryDatabase.getInvoiceMemory();

    private InvoiceMapper invoiceMapper = new InvoiceMapper();

    public CrudsResult createInvoice(InvoiceDTO invoiceDTO) {
        Invoice invoice = invoiceMapper.mapDTOToDAO(invoiceDTO);
        CrudsResult crudsResult = invoiceDAO.persist(invoice);

        if (crudsResult.isSuccess()) {
            invoiceMemory.createInvoice(invoice);
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

    public SearchResult<InvoiceDTO> getAllInvoices(String name) {
        SearchResult<Invoice> searchResult = invoiceMemory.getInvoices(name);

        List<InvoiceDTO> allInvoicesWithName = new ArrayList<InvoiceDTO>();
        if (searchResult.isSuccess()) {
            for (Invoice invoice : searchResult.getResults()) {
                InvoiceDTO invoiceDTO = invoiceMapper.mapDAOToDTO(invoice);
                allInvoicesWithName.add(invoiceDTO);
            }
        }

        SearchResult<InvoiceDTO> allInvoicesWithNameSearchResult = new SearchResult<InvoiceDTO>();
        allInvoicesWithNameSearchResult.setSuccess(searchResult.isSuccess());
        allInvoicesWithNameSearchResult.setMessages(searchResult.getMessages());
        allInvoicesWithNameSearchResult.setResults(allInvoicesWithName);
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
}
