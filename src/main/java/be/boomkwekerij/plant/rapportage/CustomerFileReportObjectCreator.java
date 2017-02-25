package be.boomkwekerij.plant.rapportage;

import be.boomkwekerij.plant.model.dto.CompanyDTO;
import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.model.report.CustomerFileCustomerReportObject;
import be.boomkwekerij.plant.model.report.CustomerFileReportObject;
import be.boomkwekerij.plant.util.DateFormatPattern;
import be.boomkwekerij.plant.util.DateUtils;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class CustomerFileReportObjectCreator {

    public CustomerFileReportObject create(CompanyDTO company, DateTime reportDate, List<CustomerDTO> customers) {
        CustomerFileReportObject customerFileReportObject = new CustomerFileReportObject();
        customerFileReportObject.setCompanyName(company.getName1() + " " + company.getName2());
        customerFileReportObject.setReportDate(DateUtils.formatDate(reportDate, DateFormatPattern.DATE_FORMAT));
        List<CustomerFileCustomerReportObject> customerReportObjects = new ArrayList<>();
        for (CustomerDTO customer : customers) {
            CustomerFileCustomerReportObject customerFileCustomerReportObject = createCustomerReportObject(customer);
            customerReportObjects.add(customerFileCustomerReportObject);
        }
        customerFileReportObject.setCustomers(customerReportObjects);
        return customerFileReportObject;
    }

    private CustomerFileCustomerReportObject createCustomerReportObject(CustomerDTO customer) {
        CustomerFileCustomerReportObject customerFileCustomerReportObject = new CustomerFileCustomerReportObject();
        customerFileCustomerReportObject.setName(customer.getName1());
        customerFileCustomerReportObject.setAddress(customer.getAddress1());
        customerFileCustomerReportObject.setPostalCode(customer.getPostalCode());
        customerFileCustomerReportObject.setResidence(customer.getResidence());
        customerFileCustomerReportObject.setTelephone(customer.getTelephone());
        customerFileCustomerReportObject.setGsm(customer.getGsm());
        customerFileCustomerReportObject.setFax(customer.getFax());
        customerFileCustomerReportObject.setBtwNumber(customer.getBtwNumber());
        customerFileCustomerReportObject.setEmailAddress(customer.getEmailAddress());
        return customerFileCustomerReportObject;
    }
}
