package be.boomkwekerij.plant.dao.memory;

import be.boomkwekerij.plant.model.repository.Invoice;
import be.boomkwekerij.plant.util.SearchResult;

import java.util.List;

public interface InvoiceMemory {

    void createInvoice(Invoice invoice);

    void createInvoices(List<Invoice> invoices);

    SearchResult<Invoice> getInvoice(String id);

    SearchResult<Invoice> getInvoices();

    SearchResult<Invoice> getInvoices(String invoiceNumber);

    void updateInvoice(Invoice invoice);

    void deleteInvoice(String id);

    void deleteAllInvoices();
}
