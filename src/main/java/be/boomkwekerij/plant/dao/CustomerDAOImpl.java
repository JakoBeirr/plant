package be.boomkwekerij.plant.dao;

import be.boomkwekerij.plant.model.repository.Customer;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.ExceptionUtil;
import be.boomkwekerij.plant.util.SearchResult;
import be.boomkwekerij.plant.util.SystemRepository;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.UUID;

public class CustomerDAOImpl implements CustomerDAO {

    private static final String CUSTOMERS_DATA_URI = SystemRepository.getDataUri() + "/customers/";

    public SearchResult<Customer> get(String id) {
        SearchResult<Customer> searchResult = new SearchResult<Customer>();

        try {
            Unmarshaller unmarshaller = getUnMarshaller();
            Customer customer = (Customer) unmarshaller.unmarshal(new File(CUSTOMERS_DATA_URI + id + ".xml"));

            searchResult.setSuccess(true);
            searchResult.addResult(customer);
        } catch (Exception e) {
            searchResult.setSuccess(false);
            searchResult.addMessage(ExceptionUtil.getStackTrace(e));
        }

        return searchResult;
    }

    public SearchResult<Customer> findAll() {
        SearchResult<Customer> searchResult = new SearchResult<Customer>();

        try {
            Unmarshaller unmarshaller = getUnMarshaller();
            File customerDirectory = new File(CUSTOMERS_DATA_URI);
            File[] customerFiles = customerDirectory.listFiles();
            if (customerFiles != null) {
                for (File customerFile : customerFiles) {
                    Customer customer = (Customer) unmarshaller.unmarshal(customerFile);
                    searchResult.addResult(customer);
                }
            }

            searchResult.setSuccess(true);
        } catch (Exception e) {
            searchResult.setSuccess(false);
            searchResult.addMessage(ExceptionUtil.getStackTrace(e));
        }

        return searchResult;
    }

    public CrudsResult persist(Customer customer) {
        CrudsResult crudsResult = new CrudsResult();
        crudsResult.setValue(customer.getId());

        try {
            customer.setId(UUID.randomUUID().toString());
            File file = new File(CUSTOMERS_DATA_URI + customer.getId() + ".xml");
            if (file.exists()) {
                crudsResult.setSuccess(false);
                crudsResult.addMessage("Customer already registered!");
            } else {
                Marshaller marshaller = getMarshaller();
                marshaller.marshal(customer, new File(CUSTOMERS_DATA_URI + customer.getId() + ".xml"));

                crudsResult.setSuccess(true);
            }
        } catch (Exception e) {
            crudsResult.setSuccess(false);
            crudsResult.addMessage(ExceptionUtil.getStackTrace(e));
        }

        return crudsResult;
    }

    public CrudsResult update(Customer customer) {
        CrudsResult crudsResult = new CrudsResult();
        crudsResult.setValue(customer.getId());

        try {
            File file = new File(CUSTOMERS_DATA_URI + customer.getId() + ".xml");
            if (!file.exists()) {
                crudsResult.setSuccess(false);
                crudsResult.addMessage("Unknown customer!");
            } else {
                Marshaller marshaller = getMarshaller();
                marshaller.marshal(customer, new File(CUSTOMERS_DATA_URI + customer.getId() + ".xml"));

                crudsResult.setSuccess(true);
            }
        } catch (Exception e) {
            crudsResult.setSuccess(false);
            crudsResult.addMessage(ExceptionUtil.getStackTrace(e));
        }

        return crudsResult;
    }

    public CrudsResult delete(String id) {
        CrudsResult crudsResult = new CrudsResult();

        try {
            File customerFile = new File(CUSTOMERS_DATA_URI + id + ".xml");
            boolean deleted = customerFile.delete();
            crudsResult.setSuccess(deleted);
            crudsResult.setValue(id);
        } catch (Exception e) {
            crudsResult.setSuccess(false);
            crudsResult.addMessage(ExceptionUtil.getStackTrace(e));
        }

        return crudsResult;
    }

    public CrudsResult deleteAll() {
        CrudsResult crudsResult = new CrudsResult();

        try {
            File customerDirectory = new File(CUSTOMERS_DATA_URI);
            File[] customerFiles = customerDirectory.listFiles();
            if (customerFiles != null) {
                for (File customerFile : customerFiles) {
                    boolean deleted = customerFile.delete();
                    crudsResult.setSuccess(deleted);
                }
            }
        } catch (Exception e) {
            crudsResult.setSuccess(false);
            crudsResult.addMessage(ExceptionUtil.getStackTrace(e));
        }

        return crudsResult;
    }

    private Marshaller getMarshaller() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        return marshaller;
    }

    private Unmarshaller getUnMarshaller() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
        return jaxbContext.createUnmarshaller();
    }
}
