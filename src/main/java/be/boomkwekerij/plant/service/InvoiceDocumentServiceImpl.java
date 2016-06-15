package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.model.dto.InvoiceDTO;
import be.boomkwekerij.plant.util.SearchResult;

public class InvoiceDocumentServiceImpl implements InvoiceDocumentService {

    private InvoiceService invoiceService = new InvoiceServiceImpl();

    public byte[] createInvoiceDocument(String id) {
        SearchResult<InvoiceDTO> invoiceSearchResult = invoiceService.getInvoice(id);

        if (invoiceSearchResult.isError()) {
            throw new IllegalArgumentException("Invoice not found!");
        }

        InvoiceDTO invoice = invoiceSearchResult.getFirst();
        return null;
    }
}
