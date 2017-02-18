package be.boomkwekerij.plant.rapportage;

import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.model.report.CustomerReportObject;
import be.boomkwekerij.plant.util.CountryCode;

public class CustomerReportObjectCreator {

    public CustomerReportObject create(CustomerDTO customerDTO) {
        CustomerReportObject customerReportObject = new CustomerReportObject();
        customerReportObject.setName1(getReportObjectValue(customerDTO.getName1()).toUpperCase());
        customerReportObject.setName2(getReportObjectValue(customerDTO.getName2()).toUpperCase());
        customerReportObject.setAddress1(getReportObjectValue(customerDTO.getAddress1()).toUpperCase());
        customerReportObject.setAddress2(getReportObjectValue(customerDTO.getAddress2()).toUpperCase());
        customerReportObject.setPostalCode(getReportObjectValue(customerDTO.getPostalCode()).toUpperCase());
        customerReportObject.setResidence(getReportObjectValue(customerDTO.getResidence()).toUpperCase());
        customerReportObject.setAbroad(isAbroad(customerDTO));
        customerReportObject.setCountry(getCountry(customerDTO));
        customerReportObject.setBtwNumber(getReportObjectValue(customerDTO.getBtwNumber()).toUpperCase());
        return customerReportObject;
    }

    private String getCountry(CustomerDTO customerDTO) {
        CountryCode countryCode = CountryCode.fromCountryCode(customerDTO.getCountry());
        if (isAbroad(customerDTO) && countryCode != null) {
            return countryCode.fullname();
        }
        return "";
    }

    private boolean isAbroad(CustomerDTO customerDTO) {
        return !customerDTO.getCountry().equals("BE");
    }

    private String getReportObjectValue(String value) {
        if (value == null) {
            return "";
        }
        return value;
    }
}
