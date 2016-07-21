package be.boomkwekerij.plant;

import be.boomkwekerij.plant.controller.CompanyController;
import be.boomkwekerij.plant.controller.CustomerController;
import be.boomkwekerij.plant.controller.InvoiceController;
import be.boomkwekerij.plant.controller.PlantController;
import be.boomkwekerij.plant.controller.SystemController;
import be.boomkwekerij.plant.model.dto.CompanyDTO;
import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.model.dto.InvoiceDTO;
import be.boomkwekerij.plant.model.dto.InvoiceLineDTO;
import be.boomkwekerij.plant.model.dto.PlantDTO;
import be.boomkwekerij.plant.model.dto.SystemDTO;
import be.boomkwekerij.plant.service.InvoiceDocumentServiceImpl;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.Initializer;
import org.joda.time.DateTime;

import java.awt.print.PrinterException;
import java.io.IOException;

public class PlantClientBackup {

    public static void main(String[] args) throws IOException, PrinterException {
        //if (args.length != 1) {
        //    throw new IllegalArgumentException("Data uri not specified!");
        //}

        //String dataUri = args[0];
        String dataUri = "C:\\Users\\Janse\\Desktop\\data";

        Initializer.init(dataUri);

        SystemDTO system = new SystemDTO();
        system.setLastInvoiceNumber("20160202");
        new SystemController().createSystem(system);

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

        new CompanyController().createCompany(company);
        CustomerDTO customer = new CustomerDTO();
        customer.setName1("BVBA BOOMKWEKERIJ P.WUYTS");
        customer.setName2("CONNIE ROOS");
        customer.setAddress1("MEERSTRAAT 67");
        customer.setPostalCode("1840");
        customer.setResidence("Londerzeel");
        customer.setBtwNumber("BE:455 427 668");
        customer.setCountry("BE");
        CrudsResult customerResult = new CustomerController().createCustomer(customer);
        customer.setId(customerResult.getValue());

        PlantDTO plant = new PlantDTO();
        plant.setName("Fagus sylvatica");
        plant.setAge("150");
        plant.setMeasure("175");
        plant.setPrice(1.8);
        CrudsResult plantResult = new PlantController().createPlant(plant);
        plant.setId(plantResult.getValue());

        PlantDTO plant2 = new PlantDTO();
        plant2.setName("Lonicera nit. 'Maigruen' p9");
        plant2.setAge("");
        plant2.setMeasure("");
        plant2.setPrice(0.5);
        CrudsResult plant2Result = new PlantController().createPlant(plant2);
        plant2.setId(plant2Result.getValue());

        InvoiceDTO invoice = new InvoiceController().makeNewInvoice(customer.getId());
        InvoiceLineDTO invoiceLine = new InvoiceLineDTO();
        invoiceLine.setDate(new DateTime().withDayOfYear(7).withMonthOfYear(4).withYear(2016));
        invoiceLine.setAmount(150);
        invoiceLine.setPlant(plant);
        invoiceLine.setPrice(1.6);
        invoice.getInvoiceLines().add(invoiceLine);
        invoice.getInvoiceLines().add(invoiceLine);
        invoice.getInvoiceLines().add(invoiceLine);
        invoice.getInvoiceLines().add(invoiceLine);
        invoice.getInvoiceLines().add(invoiceLine);
        invoice.getInvoiceLines().add(invoiceLine);
        invoice.getInvoiceLines().add(invoiceLine);
        invoice.getInvoiceLines().add(invoiceLine);
        invoice.getInvoiceLines().add(invoiceLine);
        invoice.getInvoiceLines().add(invoiceLine);
        invoice.getInvoiceLines().add(invoiceLine);
        invoice.getInvoiceLines().add(invoiceLine);
        invoice.getInvoiceLines().add(invoiceLine);
        InvoiceLineDTO invoiceLine2 = new InvoiceLineDTO();
        invoiceLine2.setDate(new DateTime().withDayOfYear(12).withMonthOfYear(4).withYear(2016));
        invoiceLine2.setAmount(870);
        invoiceLine2.setPlant(plant2);
        invoiceLine2.setPrice(0.38);
        invoice.getInvoiceLines().add(invoiceLine2);
        invoice.getInvoiceLines().add(invoiceLine2);
        invoice.getInvoiceLines().add(invoiceLine2);
        invoice.getInvoiceLines().add(invoiceLine2);
        invoice.getInvoiceLines().add(invoiceLine2);
        invoice.getInvoiceLines().add(invoiceLine2);
        invoice.getInvoiceLines().add(invoiceLine2);
        invoice.getInvoiceLines().add(invoiceLine2);
        invoice.getInvoiceLines().add(invoiceLine2);
        invoice.getInvoiceLines().add(invoiceLine2);
        invoice.getInvoiceLines().add(invoiceLine2);
        invoice.getInvoiceLines().add(invoiceLine2);
        CrudsResult invoiceResult = new InvoiceController().createInvoice(invoice);
        //new InvoiceDocumentServiceImpl().createInvoiceDocument(invoiceResult.getValue());
        new InvoiceController().printInvoiceDocument(invoiceResult.getValue());

        /*customer.setCountry("NL");
        new CustomerController().updateCustomer(customer);
        InvoiceDTO invoice2 = new InvoiceController().makeNewInvoice(customer.getId());
        InvoiceLineDTO invoiceLine3 = new InvoiceLineDTO();
        invoiceLine3.setDate(new DateTime().withDayOfYear(7).withMonthOfYear(4).withYear(2016));
        invoiceLine3.setAmount(100);
        invoiceLine3.setPlant(plant);
        invoiceLine3.setPrice(1.7);
        invoice2.getInvoiceLines().add(invoiceLine3);
        InvoiceLineDTO invoiceLine4 = new InvoiceLineDTO();
        invoiceLine4.setDate(new DateTime().withDayOfYear(12).withMonthOfYear(4).withYear(2016));
        invoiceLine4.setAmount(670);
        invoiceLine4.setPlant(plant2);
        invoiceLine4.setPrice(0.58);
        invoice2.getInvoiceLines().add(invoiceLine4);
        CrudsResult invoiceResult2 = new InvoiceController().createInvoice(invoice2);
        new InvoiceDocumentServiceImpl().createInvoiceDocument(invoiceResult2.getValue());*/
    }
}
