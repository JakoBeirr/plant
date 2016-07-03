package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.model.dto.InvoiceDTO;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;

public interface InvoiceService {

    CrudsResult createInvoice(InvoiceDTO invoice);

    SearchResult<InvoiceDTO> getInvoice(String id);

    SearchResult<InvoiceDTO> getAllInvoices();

    SearchResult<InvoiceDTO> getAllInvoices(String invoiceNumber);

    CrudsResult updateInvoice(InvoiceDTO invoice);

    CrudsResult deleteInvoice(String id);

    InvoiceDTO getNewInvoiceForCustomer(String customerId);
}
