package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.exception.ReportException;
import be.boomkwekerij.plant.mapper.CustomerMapper;
import be.boomkwekerij.plant.model.dto.BestandDTO;
import be.boomkwekerij.plant.model.dto.CompanyDTO;
import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.model.report.CustomerFileReportObject;
import be.boomkwekerij.plant.util.Month;
import be.boomkwekerij.plant.util.ReportPDFCreator;
import be.boomkwekerij.plant.util.SearchResult;
import org.joda.time.DateTime;

import java.util.List;

public class ReportingServiceImpl implements ReportingService {

    private CustomerService customerService = new CustomerServiceImpl();
    private CompanyService companyService = new CompanyServiceImpl();

    private CustomerMapper customerMapper = new CustomerMapper();

    private ReportPDFCreator reportPDFCreator = new ReportPDFCreator();

    @Override
    public BestandDTO createCustomerFileReport() throws ReportException {
        List<CustomerDTO> customers = findAllCustomers();
        CompanyDTO company = findCompany();
        DateTime reportDate = new DateTime();

        CustomerFileReportObject customerFileReportObject = customerMapper.mapToCustomerFileReportObject(company, reportDate, customers);
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
    public BestandDTO createUnpayedInvoicesReport() {
        return new BestandDTO();
    }

    @Override
    public BestandDTO createInvoicesReportForMonth(Month month) {
        return new BestandDTO();
    }
}
