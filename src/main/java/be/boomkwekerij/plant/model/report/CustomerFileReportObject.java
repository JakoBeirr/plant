package be.boomkwekerij.plant.model.report;

import java.util.List;

public class CustomerFileReportObject {

    private String companyName;
    private String reportDate;
    private List<CustomerFileCustomerReportObject> customers;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public List<CustomerFileCustomerReportObject> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerFileCustomerReportObject> customers) {
        this.customers = customers;
    }
}
