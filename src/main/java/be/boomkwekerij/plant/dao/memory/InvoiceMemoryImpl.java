package be.boomkwekerij.plant.dao.memory;

import be.boomkwekerij.plant.model.repository.Invoice;
import be.boomkwekerij.plant.util.SearchResult;

import java.util.ArrayList;
import java.util.Collections;
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
        if (id == null) {
            return new SearchResult<Invoice>().error(Collections.singletonList("Kon geen factuur vinden voor id null!"));
        } else {
            Invoice invoice = invoices.get(id);
            if (invoice != null) {
                return new SearchResult<Invoice>().success(Collections.singletonList(invoice));
            }
            return new SearchResult<Invoice>().error(Collections.singletonList("Onbekende factuur"));
        }
    }

    public SearchResult<Invoice> getInvoices() {
        return new SearchResult<Invoice>().success(new ArrayList<Invoice>(invoices.values()));
    }

    public SearchResult<Invoice> getInvoices(String number) {
        if (number == null) {
            return new SearchResult<Invoice>().error(Collections.singletonList("Kon geen factuur vinden voor number null!"));
        } else {
            List<Invoice> invoicesWithNumber = new ArrayList<>();
            for (Invoice invoice : invoices.values()) {
                if (invoiceNameStartsWith(invoice, number)) {
                    invoicesWithNumber.add(invoice);
                }
            }
            return new SearchResult<Invoice>().success(invoicesWithNumber);
        }
    }

    private boolean invoiceNameStartsWith(Invoice invoice, String invoiceNumber) {
        return invoice.getInvoiceNumber() != null && invoice.getInvoiceNumber().toUpperCase().startsWith(invoiceNumber.toUpperCase());
    }

    @Override
    public SearchResult<Invoice> getInvoicesFromCustomer(String customerId) {
        if (customerId == null) {
            return new SearchResult<Invoice>().error(Collections.singletonList("Kon geen factuur vinden voor klant met id null!"));
        } else {
            List<Invoice> invoicesFromCustomer = new ArrayList<>();
            for (Invoice invoice : invoices.values()) {
                if (invoiceFromCustomer(invoice, customerId)) {
                    invoicesFromCustomer.add(invoice);
                }
            }
            return new SearchResult<Invoice>().success(invoicesFromCustomer);
        }
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
