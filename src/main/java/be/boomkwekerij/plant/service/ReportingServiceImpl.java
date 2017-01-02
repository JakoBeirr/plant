package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.exception.ReportException;
import be.boomkwekerij.plant.model.dto.BestandDTO;
import be.boomkwekerij.plant.model.dto.CompanyDTO;
import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.model.dto.InvoiceDTO;
import be.boomkwekerij.plant.model.report.CustomerFileReportObject;
import be.boomkwekerij.plant.model.report.InvoicesReportObject;
import be.boomkwekerij.plant.rapportage.CustomerFileReportObjectCreator;
import be.boomkwekerij.plant.rapportage.InvoicesReportObjectCreator;
import be.boomkwekerij.plant.util.Month;
import be.boomkwekerij.plant.util.ReportPDFCreator;
import be.boomkwekerij.plant.util.SearchResult;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class ReportingServiceImpl implements ReportingService {

    private InvoiceService invoiceService = new InvoiceServiceImpl();
    private CustomerService customerService = new CustomerServiceImpl();
    private CompanyService companyService = new CompanyServiceImpl();

    private CustomerFileReportObjectCreator customerFileReportObjectCreator = new CustomerFileReportObjectCreator();
    private InvoicesReportObjectCreator invoicesReportObjectCreator = new InvoicesReportObjectCreator();

    private ReportPDFCreator reportPDFCreator = new ReportPDFCreator();

    @Override
    public BestandDTO createCustomerFileReport() throws ReportException {
        List<CustomerDTO> customers = findAllCustomers();
        CompanyDTO company = findCompany();
        DateTime reportDate = new DateTime();

        CustomerFileReportObject customerFileReportObject = customerFileReportObjectCreator.create(company, reportDate, customers);
        return reportPDFCreator.createCustomerFileReport(customerFileReportObject);
    }

    private List<CustomerDTO> findAllCustomers() {
        SearchResult<CustomerDTO> searchResult = customerService.getAllCustomers();
        if (searchResult.isSuccess()) {
            return searchResult.getResults();
        }
        throw new IllegalArgumentException("Fout tijdens zoeken van klanten");
    }

    private CompanyDTO findCompany() {
        SearchResult<CompanyDTO> searchResult = companyService.getCompany();
        if (searchResult.isSuccess()) {
            return searchResult.getFirst();
        }
        throw new IllegalArgumentException("Fout tijdens zoeken van bedrijf");
    }

    @Override
    public BestandDTO createUnpayedInvoicesReport() throws ReportException {
        List<InvoiceDTO> allInvoices = findAllInvoices();
        List<InvoiceDTO> unpayedInvoices = filterUnpayed(allInvoices);
        CompanyDTO company = findCompany();
        DateTime reportDate = new DateTime();
        String period = "NVT";
        String title = "OVERZICHT ONBETAALDE FACTUREN";

        InvoicesReportObject invoicesReportObject = invoicesReportObjectCreator.create(unpayedInvoices, company, reportDate, period, title);
        return reportPDFCreator.createInvoiceReport(invoicesReportObject);
    }

    private List<InvoiceDTO> filterUnpayed(List<InvoiceDTO> allInvoices) {
        List<InvoiceDTO> unpayedInvoices = new ArrayList<>();
        for (InvoiceDTO invoice : allInvoices) {
            if (!invoice.isPayed()) {
                unpayedInvoices.add(invoice);
            }
        }
        return unpayedInvoices;
    }

    @Override
    public BestandDTO createInvoicesReportForMonth(Month month, int year) throws ReportException {
        List<InvoiceDTO> allInvoices = findAllInvoices();
        List<InvoiceDTO> invoicesInMonth = filterMonth(allInvoices, month, year);
        CompanyDTO company = findCompany();
        DateTime reportDate = new DateTime();
        String period = month.translation() + " " + Integer.toString(year);
        String title = "OVERZICHT ALLE FACTUREN";

        InvoicesReportObject invoicesReportObject = invoicesReportObjectCreator.create(invoicesInMonth, company, reportDate, period, title);
        return reportPDFCreator.createInvoiceReport(invoicesReportObject);
    }

    private List<InvoiceDTO> filterMonth(List<InvoiceDTO> allInvoices, Month month, int year) {
        List<InvoiceDTO> invoicesInMonth = new ArrayList<>();
        for (InvoiceDTO invoice : allInvoices) {
            DateTime invoiceDate = invoice.getDate();
            if (invoiceDate.getMonthOfYear() == month.code() && invoiceDate.getYear() == year) {
                invoicesInMonth.add(invoice);
            }
        }
        return invoicesInMonth;
    }

    private List<InvoiceDTO> findAllInvoices() {
        SearchResult<InvoiceDTO> searchResult = invoiceService.getAllInvoices();
        if (searchResult.isSuccess()) {
            return searchResult.getResults();
        }
        throw new IllegalArgumentException("Fout tijdens zoeken van facturen");
    }
}
