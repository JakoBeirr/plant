package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.model.dto.CompanyDTO;
import be.boomkwekerij.plant.model.dto.InvoiceDTO;
import be.boomkwekerij.plant.util.SearchResult;

public class InvoiceDocumentServiceImpl implements InvoiceDocumentService {

    private InvoiceService invoiceService = new InvoiceServiceImpl();
    private CompanyService companyService = new CompanyServiceImpl();

    public byte[] createInvoiceDocument(String id) {
        CompanyDTO company = getCompany();
        InvoiceDTO invoice = getInvoice(id);

        return createDocument(company, invoice);
    }

    private CompanyDTO getCompany() {
        SearchResult<CompanyDTO> companySearchResult = companyService.getCompany();

        if (companySearchResult.isError()) {
            throw new IllegalArgumentException("Company not found!");
        }

        return companySearchResult.getFirst();
    }

    private InvoiceDTO getInvoice(String id) {
        SearchResult<InvoiceDTO> invoiceSearchResult = invoiceService.getInvoice(id);

        if (invoiceSearchResult.isError()) {
            throw new IllegalArgumentException("Invoice not found!");
        }

        return invoiceSearchResult.getFirst();
    }

    private byte[] createDocument(CompanyDTO company, InvoiceDTO invoice) {
        return null;
    }
}
