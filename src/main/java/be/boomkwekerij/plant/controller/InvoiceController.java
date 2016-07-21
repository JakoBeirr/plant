package be.boomkwekerij.plant.controller;

import be.boomkwekerij.plant.model.dto.InvoiceDTO;
import be.boomkwekerij.plant.service.InvoiceDocumentService;
import be.boomkwekerij.plant.service.InvoiceDocumentServiceImpl;
import be.boomkwekerij.plant.service.InvoiceService;
import be.boomkwekerij.plant.service.InvoiceServiceImpl;
import be.boomkwekerij.plant.service.PrinterService;
import be.boomkwekerij.plant.service.PrinterServiceImpl;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.ExceptionUtil;
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

    private SearchResult<InvoiceDTO> createSearchError(Exception e) {
        SearchResult<InvoiceDTO> failure = new SearchResult<InvoiceDTO>();
        failure.setSuccess(false);
        failure.getMessages().add(ExceptionUtil.getStackTrace(e));
        return failure;
    }

    private CrudsResult createCrudsError(Exception e) {
        CrudsResult failure = new CrudsResult();
        failure.setSuccess(false);
        failure.getMessages().add(ExceptionUtil.getStackTrace(e));
        return failure;
    }

    public CrudsResult printInvoiceDocument(String invoiceId) {
        CrudsResult printResult = new CrudsResult();
        printResult.setValue(invoiceId);

        try {
            byte[] invoiceDocument = invoiceDocumentService.createInvoiceDocument(invoiceId);
            printerService.printDocument(invoiceDocument);
            printResult.setSuccess(true);
        } catch (Exception e) {
            printResult.setSuccess(false);
            printResult.getMessages().add(ExceptionUtil.getStackTrace(e));
        }

        return printResult;
    }
}