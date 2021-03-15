package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.model.dto.InvoiceDTO;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;

public interface ArchivedInvoiceService {

    SearchResult<InvoiceDTO> getInvoice(String id);

    SearchResult<InvoiceDTO> getAllInvoices();

    CrudsResult deleteInvoice(String id);
}
