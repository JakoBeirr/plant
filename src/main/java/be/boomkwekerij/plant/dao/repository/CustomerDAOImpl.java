package be.boomkwekerij.plant.dao.repository;

import be.boomkwekerij.plant.model.repository.Customer;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.InitializerSingleton;
import be.boomkwekerij.plant.util.SearchResult;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class CustomerDAOImpl implements CustomerDAO {

    private final String customersDataUri;

    public CustomerDAOImpl() {
        customersDataUri = InitializerSingleton.getInitializer().getDataDirectory() + "/customers/";
    }

    public SearchResult<Customer> get(String id) {
        try {
            Unmarshaller unmarshaller = unmarshaller();
            Customer customer = (Customer) unmarshaller.unmarshal(new File(customersDataUri + id + ".xml"));

            return new SearchResult<Customer>().success(Collections.singletonList(customer));
        } catch (Exception e) {
            return new SearchResult<Customer>().error(Collections.singletonList(e.getMessage()));
        }
    }

    public SearchResult<Customer> findAll() {
        try {
            Unmarshaller unmarshaller = unmarshaller();
            File customerDirectory = new File(customersDataUri);
            File[] customerFiles = customerDirectory.listFiles();

            List<Customer> customers = new ArrayList<>();
            if (customerFiles != null) {
                for (File customerFile : customerFiles) {
                    Customer customer = (Customer) unmarshaller.unmarshal(customerFile);
                    customers.add(customer);
                }
            }
            return new SearchResult<Customer>().success(customers);
        } catch (Exception e) {
            return new SearchResult<Customer>().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult persist(Customer customer) {
        try {
            customer.setId(UUID.randomUUID().toString());
            File file = new File(customersDataUri + customer.getId() + ".xml");
            if (file.exists()) {
                return new CrudsResult().error(Collections.singletonList("Klant reeds geregistreerd"));
            } else {
                Marshaller marshaller = marshaller();
                marshaller.marshal(customer, new File(customersDataUri + customer.getId() + ".xml"));

                return new CrudsResult().success(customer.getId());
            }
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult update(Customer customer) {
        try {
            File file = new File(customersDataUri + customer.getId() + ".xml");
            if (!file.exists()) {
                return new CrudsResult().error(Collections.singletonList("Onbekende klant"));
            } else {
                Marshaller marshaller = marshaller();
                marshaller.marshal(customer, new File(customersDataUri + customer.getId() + ".xml"));

                return new CrudsResult().success(customer.getId());
            }
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult delete(String id) {
        try {
            File customerFile = new File(customersDataUri + id + ".xml");
            boolean deleted = customerFile.delete();

            if (deleted) {
                return new CrudsResult().success(id);
            }
            return new CrudsResult().error(Collections.singletonList("Klant verwijderen mislukt"));
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult deleteAll() {
        try {
            File customerDirectory = new File(customersDataUri);
            File[] customerFiles = customerDirectory.listFiles();
            if (customerFiles != null) {
                for (File customerFile : customerFiles) {
                    boolean deleted = customerFile.delete();
                    if (!deleted) {
                        return new CrudsResult().error(Collections.singletonList("Klant verwijderen mislukt"));
                    }
                }
            }
            return new CrudsResult().success();
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    private Marshaller marshaller() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        return marshaller;
    }

    private Unmarshaller unmarshaller() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
        return jaxbContext.createUnmarshaller();
    }
}
