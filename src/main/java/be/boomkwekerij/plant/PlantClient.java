package be.boomkwekerij.plant;

import be.boomkwekerij.plant.model.dto.CompanyDTO;
import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.model.dto.InvoiceDTO;
import be.boomkwekerij.plant.service.CompanyServiceImpl;
import be.boomkwekerij.plant.service.CustomerServiceImpl;
import be.boomkwekerij.plant.service.InvoiceDocumentServiceImpl;
import be.boomkwekerij.plant.service.InvoiceServiceImpl;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.Initializer;

public class PlantClient {

    public static void main(String[] args) {
        //if (args.length != 1) {
        //    throw new IllegalArgumentException("Data uri not specified!");
        //}

        //String dataUri = args[0];
        String dataUri = "C:\\Users\\Janse\\Desktop\\data";

        Initializer.init(dataUri);

        CompanyDTO company = new CompanyDTO();
        company.setAddress("Nieuwmoersesteenweg 117, 2990 Wuustwezel");
        company.setTelephone("03/669.61.42");
        company.setFax("03/669.53.60");
        company.setGsm("0498/92.23.19");
        company.setAccountNumberBelgium("733-1432505-39");
        company.setIbanBelgium("BE13 7331 4325 0539");
        company.setBicBelgium("KREDBEBB");
        company.setAccountNumberNetherlands("137954700");
        company.setIbanNetherlands("NL44 RABO 0137 9547 00");
        company.setBicNetherlands("RABONL2U");
        company.setBtwNumber("BE 767.130.636");

        new CompanyServiceImpl().createCompany(company);
        CustomerDTO customer = new CustomerDTO();
        customer.setName1("BVBA BOOMKWEKERIJ P.WUYTS");
        customer.setName2("CONNIE ROOS");
        customer.setAddress1("MEERSTRAAT 67");
        customer.setPostalCode("1840");
        customer.setResidence("Londerzeel");
        customer.setBtwNumber("BE:455 427 668");
        CrudsResult customerResult = new CustomerServiceImpl().createCustomer(customer);
        customer.setId(customerResult.getValue());

        InvoiceDTO invoice = new InvoiceDTO();
        invoice.setCustomer(customer);
        CrudsResult invoiceResult = new InvoiceServiceImpl().createInvoice(invoice);

        new InvoiceDocumentServiceImpl().createInvoiceDocument(invoiceResult.getValue());
    }
}
