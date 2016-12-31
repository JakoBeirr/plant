package be.boomkwekerij.plant.controller;

import be.boomkwekerij.plant.exception.ReportException;
import be.boomkwekerij.plant.model.dto.BestandDTO;
import be.boomkwekerij.plant.model.dto.DateDTO;
import be.boomkwekerij.plant.model.dto.InvoiceDTO;
import be.boomkwekerij.plant.service.*;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;

public class InvoiceController {

    private InvoiceService invoiceService = new InvoiceServiceImpl();
    private InvoiceDocumentService invoiceDocumentService = new InvoiceDocumentServiceImpl();
    private PrinterService printerService = new PrinterServiceImpl();

    public CrudsResult createInvoice(InvoiceDTO invoiceDTO) {
        try {
            return invoiceService.createInvoice(invoiceDTO);
        } catch (Exception e) {
            return createCrudsError(e);
        }
    }

    public InvoiceDTO makeNewInvoice(String customerId) {
        try {
            return invoiceService.getNewInvoiceForCustomer(customerId);
        } catch (Exception e) {
            return null;
        }
    }

    public SearchResult<InvoiceDTO> getInvoice(String id) {
        try {
            return invoiceService.getInvoice(id);
        } catch (Exception e) {
            return createSearchError(e);
        }
    }

    public SearchResult<InvoiceDTO> getAllInvoices() {
        try {
            return invoiceService.getAllInvoices();
        } catch (Exception e) {
            return createSearchError(e);
        }
    }

    public CrudsResult updateInvoice(InvoiceDTO invoiceDTO) {
        try {
            return invoiceService.updateInvoice(invoiceDTO);
        } catch (Exception e) {
            return createCrudsError(e);
        }
    }

    public CrudsResult deleteInvoice(String id) {
        try {
            return invoiceService.deleteInvoice(id);
        } catch (Exception e) {
            return createCrudsError(e);
        }
    }

    public SearchResult<InvoiceDTO> getAllInvoicesWithInvoiceNumber(String invoiceNumber) {
        try {
            return invoiceService.getAllInvoices(invoiceNumber);
        } catch (Exception e) {
            return createSearchError(e);
        }
    }

    public SearchResult<InvoiceDTO> getAllInvoicesFromCustomer(String customerId) {
        try {
            return invoiceService.getAllInvoicesFromCustomer(customerId);
        } catch (Exception e) {
            return createSearchError(e);
        }
    }

    public CrudsResult payInvoice(String invoiceId, DateDTO dateDTO) {
        try {
            return invoiceService.payInvoice(invoiceId, dateDTO);
        } catch (Exception e) {
            return createCrudsError(e);
        }
    }

    public CrudsResult unPayInvoice(String invoiceId) {
        try {
            return invoiceService.unPayInvoice(invoiceId);
        } catch (Exception e) {
            return createCrudsError(e);
        }
    }

    public CrudsResult printInvoiceDocument(String invoiceId) {
        CrudsResult printResult = new CrudsResult();
        printResult.setValue(invoiceId);

        try {
            SearchResult<InvoiceDTO> invoiceSearchResult = invoiceService.getInvoice(invoiceId);
            if (invoiceSearchResult.isSuccess()) {
                InvoiceDTO invoiceDTO = invoiceSearchResult.getFirst();
                BestandDTO invoice = invoiceDocumentService.createInvoiceDocument(invoiceDTO);
                printerService.printDocument_Portrait(invoice);
                printResult.setSuccess(true);
            } else {
                printResult.setSuccess(false);
            }
        } catch (Exception e) {
            return createCrudsError(e);
        }

        return printResult;
    }

    public CrudsResult printSellingConditions() {
        CrudsResult printResult = new CrudsResult();

        try {
            BestandDTO sellingConditions = invoiceDocumentService.createSellingConditionsDocument();
            printerService.printDocument_Portrait(sellingConditions);
            printResult.setSuccess(true);
        } catch (Exception e) {
            return createCrudsError(e);
        }

        return printResult;
    }

    private SearchResult<InvoiceDTO> createSearchError(Exception e) {
        SearchResult<InvoiceDTO> failure = new SearchResult<InvoiceDTO>();
        failure.setSuccess(false);
        failure.getMessages().add(e.getMessage());
        return failure;
    }

    private CrudsResult createCrudsError(Exception e) {
        CrudsResult failure = new CrudsResult();
        failure.setSuccess(false);
        failure.getMessages().add(e.getMessage());
        return failure;
    }
}
