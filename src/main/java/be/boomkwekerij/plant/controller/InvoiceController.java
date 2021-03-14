package be.boomkwekerij.plant.controller;

import be.boomkwekerij.plant.model.dto.BestandDTO;
import be.boomkwekerij.plant.model.dto.DateDTO;
import be.boomkwekerij.plant.model.dto.InvoiceDTO;
import be.boomkwekerij.plant.service.*;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;

import java.util.Collections;

public class InvoiceController {

    private InvoiceService invoiceService = new InvoiceServiceImpl();
    private InvoiceDocumentService invoiceDocumentService = new InvoiceDocumentServiceImpl();
    private PrinterService printerService = new PrinterServiceImpl();

    public CrudsResult createInvoice(InvoiceDTO invoiceDTO) {
        try {
            return invoiceService.createInvoice(invoiceDTO);
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    public InvoiceDTO makeNewInvoice(String customerId) {
        return invoiceService.getNewInvoiceForCustomer(customerId);
    }

    public SearchResult<InvoiceDTO> getInvoice(String id) {
        try {
            return invoiceService.getInvoice(id);
        } catch (Exception e) {
            return new SearchResult<InvoiceDTO>().error(Collections.singletonList(e.getMessage()));
        }
    }

    public SearchResult<InvoiceDTO> getAllInvoices() {
        try {
            return invoiceService.getAllInvoices();
        } catch (Exception e) {
            return new SearchResult<InvoiceDTO>().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult updateInvoice(InvoiceDTO invoiceDTO) {
        try {
            return invoiceService.updateInvoice(invoiceDTO);
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult deleteInvoice(String id) {
        try {
            return invoiceService.deleteInvoice(id);
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    public SearchResult<InvoiceDTO> getAllInvoicesWithInvoiceNumber(String invoiceNumber) {
        try {
            return invoiceService.getAllInvoices(invoiceNumber);
        } catch (Exception e) {
            return new SearchResult<InvoiceDTO>().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult payInvoice(String invoiceId, DateDTO dateDTO) {
        try {
            return invoiceService.payInvoice(invoiceId, dateDTO);
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult unPayInvoice(String invoiceId) {
        try {
            return invoiceService.unPayInvoice(invoiceId);
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult printInvoiceDocument(String invoiceId) {
        try {
            SearchResult<InvoiceDTO> invoiceSearchResult = invoiceService.getInvoice(invoiceId);
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

    public CrudsResult printSellingConditions() {
        try {
            BestandDTO sellingConditions = invoiceDocumentService.createSellingConditionsDocument();
            printerService.printDocumentInPortraitMode(sellingConditions);
            return new CrudsResult().success(null);
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }
}
