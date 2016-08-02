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

        if (id == null) {
            searchResult.setSuccess(false);
            searchResult.addMessage("Kon geen factuur vinden voor id null!");
        } else {
            searchResult.setSuccess(true);
            Invoice invoice = invoices.get(id);
            if (invoice != null) {
                searchResult.addResult(invoice);
            }
        }

        return searchResult;
    }

    public SearchResult<Invoice> getInvoices() {
        SearchResult<Invoice> searchResult = new SearchResult<Invoice>();
        searchResult.setSuccess(true);
        searchResult.setResults(new ArrayList<Invoice>(invoices.values()));
        return searchResult;
    }

    public SearchResult<Invoice> getInvoices(String name) {
        SearchResult<Invoice> invoicesWithInvoiceNumber = new SearchResult<Invoice>();

        if (name == null) {
            invoicesWithInvoiceNumber.setSuccess(false);
            invoicesWithInvoiceNumber.addMessage("Kon geen factuur vinden voor name null!");
        } else {
            invoicesWithInvoiceNumber.setSuccess(true);
            for (Invoice invoice : invoices.values()) {
                if (invoiceNameStartsWith(invoice, name)) {
                    invoicesWithInvoiceNumber.addResult(invoice);
                }
            }
        }

        return invoicesWithInvoiceNumber;
    }

    private boolean invoiceNameStartsWith(Invoice invoice, String invoiceNumber) {
        return invoice.getInvoiceNumber() != null && invoice.getInvoiceNumber().toUpperCase().startsWith(invoiceNumber.toUpperCase());
    }

    @Override
    public SearchResult<Invoice> getInvoicesFromCustomer(String customerId) {
        SearchResult<Invoice> invoicesFromCustomer = new SearchResult<Invoice>();

        if (customerId == null) {
            invoicesFromCustomer.setSuccess(false);
            invoicesFromCustomer.addMessage("Kon geen factuur vinden voor klant met id null!");
        } else {
            invoicesFromCustomer.setSuccess(true);
            for (Invoice invoice : invoices.values()) {
                if (invoiceFromCustomer(invoice, customerId)) {
                    invoicesFromCustomer.addResult(invoice);
                }
            }
        }

        return invoicesFromCustomer;
    }

    private boolean invoiceFromCustomer(Invoice invoice, String customerId) {
        return invoice.getCustomerId() != null && invoice.getCustomerId().equals(customerId);
    }

    public void updateInvoice(Invoice invoice) {
        if (invoices.get(invoice.getId()) != null) {
            invoices.put(invoice.getId(), invoice);
        }
    }

    public void deleteInvoice(String id) {
        invoices.remove(id);
    }

    @Override
    public void deleteAllInvoices() {
        invoices = new HashMap<>();
    }
}
