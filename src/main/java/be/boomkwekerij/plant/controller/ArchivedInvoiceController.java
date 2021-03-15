package be.boomkwekerij.plant.controller;

import be.boomkwekerij.plant.model.dto.BestandDTO;
import be.boomkwekerij.plant.model.dto.InvoiceDTO;
import be.boomkwekerij.plant.service.*;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;

import java.util.Collections;

public class ArchivedInvoiceController {

    private final ArchivedInvoiceService archivedInvoiceService = new ArchivedInvoiceServiceImpl();
    private final InvoiceDocumentService invoiceDocumentService = new InvoiceDocumentServiceImpl();
    private final PrinterService printerService = new PrinterServiceImpl();

    public SearchResult<InvoiceDTO> getAllInvoices() {
        try {
            return archivedInvoiceService.getAllInvoices();
        } catch (Exception e) {
            return new SearchResult<InvoiceDTO>().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult deleteInvoice(String id) {
        try {
            return archivedInvoiceService.deleteInvoice(id);
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult printInvoiceDocument(String invoiceId) {
        try {
            SearchResult<InvoiceDTO> invoiceSearchResult = archivedInvoiceService.getInvoice(invoiceId);
            if (invoiceSearchResult.isSuccess()) {
                InvoiceDTO invoiceDTO = invoiceSearchResult.getFirst();
                BestandDTO invoice = invoiceDocumentService.createInvoiceDocument(invoiceDTO);
                printerService.printDocumentInPortraitMode(invoice);
                return new CrudsResult().success(invoiceId);
            } else {
                return new CrudsResult().error(invoiceSearchResult.getMessages());
            }
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }
}
