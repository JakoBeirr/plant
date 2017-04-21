package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.exception.ReportException;
import be.boomkwekerij.plant.model.dto.BestandDTO;
import be.boomkwekerij.plant.model.dto.CompanyDTO;
import be.boomkwekerij.plant.model.dto.InvoiceDTO;
import be.boomkwekerij.plant.model.dto.InvoiceLineDTO;
import be.boomkwekerij.plant.model.report.CompanyReportObject;
import be.boomkwekerij.plant.model.report.CustomerReportObject;
import be.boomkwekerij.plant.model.report.MultiplePagedInvoiceReportObject;
import be.boomkwekerij.plant.model.report.OnePagedInvoiceReportObject;
import be.boomkwekerij.plant.rapportage.CompanyReportObjectCreator;
import be.boomkwekerij.plant.rapportage.CustomerReportObjectCreator;
import be.boomkwekerij.plant.rapportage.InvoiceReportObjectCreator;
import be.boomkwekerij.plant.util.InitializerSingleton;
import be.boomkwekerij.plant.util.InvoicePDFCreator;
import be.boomkwekerij.plant.util.SearchResult;
import be.boomkwekerij.plant.util.SellingConditionsPDFCreator;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public class InvoiceDocumentServiceImpl implements InvoiceDocumentService {

    private CompanyService companyService = new CompanyServiceImpl();

    private InvoiceReportObjectCreator invoiceReportObjectCreator = new InvoiceReportObjectCreator();
    private CompanyReportObjectCreator companyReportObjectCreator = new CompanyReportObjectCreator();
    private CustomerReportObjectCreator customerReportObjectCreator = new CustomerReportObjectCreator();

    private InvoicePDFCreator invoicePDFCreator = new InvoicePDFCreator();
    private SellingConditionsPDFCreator sellingConditionsPDFCreator = new SellingConditionsPDFCreator();

    @Override
    public BestandDTO createInvoiceDocument(InvoiceDTO invoiceDTO) throws ReportException {
        CompanyReportObject company = getCompany();
        CustomerReportObject customer = customerReportObjectCreator.create(invoiceDTO.getCustomer());
        int amountOfPages = getAmountOfPages(invoiceDTO.getInvoiceLines());

        if (amountOfPages == 1) {
            return createOnePagedInvoice(company, invoiceDTO, customer);
        } else {
            return createMultiplePagedInvoice(company, invoiceDTO, customer, amountOfPages);
        }
    }

    private CompanyReportObject getCompany() {
        SearchResult<CompanyDTO> companySearchResult = companyService.getCompany();
        if (companySearchResult.isError()) {
            throw new IllegalArgumentException("Company not found!");
        }

        CompanyDTO companyDTO = companySearchResult.getFirst();
        return companyReportObjectCreator.create(companyDTO);
    }

    private int getAmountOfPages(List<InvoiceLineDTO> invoiceLines) {
        double size = 0.0;
        for (InvoiceLineDTO invoiceLine : invoiceLines) {
            size += StringUtils.isBlank(invoiceLine.getRemark()) ? 1 : 2;
        }
        double max = (double) InitializerSingleton.MAX_INVOICELINES;
        return (int) Math.ceil(size / max);
    }

    private BestandDTO createOnePagedInvoice(CompanyReportObject company, InvoiceDTO invoiceDTO, CustomerReportObject customer) throws ReportException {
        OnePagedInvoiceReportObject invoice = invoiceReportObjectCreator.createOnePageReportObject(invoiceDTO);
        return invoicePDFCreator.createOnePagedInvoiceDocument(company, customer, invoice);
    }

    private BestandDTO createMultiplePagedInvoice(CompanyReportObject company, InvoiceDTO invoiceDTO, CustomerReportObject customer, int amountOfPages) throws ReportException {
        List<MultiplePagedInvoiceReportObject> invoiceParts = invoiceReportObjectCreator.createMultiplePagedReportObject(invoiceDTO, amountOfPages);
        return invoicePDFCreator.createMultiplePagedInvoiceDocument(company, customer, invoiceParts);
    }

    @Override
    public BestandDTO createSellingConditionsDocument() throws ReportException {
        return sellingConditionsPDFCreator.createSellingConditionsDocument();
    }
}
