package be.boomkwekerij.plant.dao.memory;

import be.boomkwekerij.plant.model.repository.Invoice;
import be.boomkwekerij.plant.util.SearchResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InvoiceMemoryImpl implements InvoiceMemory {

    private HashMap<String, Invoice> invoices = new HashMap<String, Invoice>();

    public void createInvoice(Invoice invoice) {
        invoices.put(invoice.getId(), invoice);
    }

    public void createInvoices(List<Invoice> invoiceList) {
        for (Invoice invoice : invoiceList) {
            invoices.put(invoice.getId(), invoice);
        }
    }

    public SearchResult<Invoice> getInvoice(String id) {
        SearchResult<Invoice> searchResult = new SearchResult<Invoice>();
        searchResult.setSuccess(true);
        searchResult.addResult(invoices.get(id));
        return searchResult;
    }

    public SearchResult<Invoice> getInvoices() {
        SearchResult<Invoice> searchResult = new SearchResult<Invoice>();
        searchResult.setSuccess(true);
        searchResult.setResults(new ArrayList<Invoice>(invoices.values()));
        return searchResult;
    }

    public SearchResult<Invoice> getInvoices(String name) {
        SearchResult<Invoice> invoicesWithName = new SearchResult<Invoice>();

        for (Invoice invoice : invoices.values()) {
            if (invoiceNameStartsWith(invoice, name)) {
                invoicesWithName.addResult(invoice);
            }
        }

        invoicesWithName.setSuccess(true);
        return invoicesWithName;
    }

    private boolean invoiceNameStartsWith(Invoice invoice, String invoiceNumber) {
        return invoice.getInvoiceNumber() != null && invoice.getInvoiceNumber().toUpperCase().startsWith(invoiceNumber.toUpperCase());
    }

    public void updateInvoice(Invoice invoice) {
        if (invoices.get(invoice.getId()) != null) {
            invoices.put(invoice.getId(), invoice);
        }
    }

    public void deleteInvoice(String id) {
        invoices.remove(id);
    }
}
