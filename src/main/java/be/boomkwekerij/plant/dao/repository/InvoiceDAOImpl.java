package be.boomkwekerij.plant.dao.repository;

import be.boomkwekerij.plant.model.repository.Invoice;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.Initializer;
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

public class InvoiceDAOImpl implements InvoiceDAO {

    private static final String INVOICES_DATA_URI = Initializer.getDataUri() + "/invoices/";

    public SearchResult<Invoice> get(String id) {
        try {
            Unmarshaller unmarshaller = unmarshaller();
            Invoice invoice = (Invoice) unmarshaller.unmarshal(new File(INVOICES_DATA_URI + id + ".xml"));

            return new SearchResult<Invoice>().success(Collections.singletonList(invoice));
        } catch (Exception e) {
            return new SearchResult<Invoice>().error(Collections.singletonList(e.getMessage()));
        }
    }

    public SearchResult<Invoice> findAll() {
        try {
            Unmarshaller unmarshaller = unmarshaller();
            File invoiceDirectory = new File(INVOICES_DATA_URI);
            File[] invoiceFiles = invoiceDirectory.listFiles();

            List<Invoice> invoices = new ArrayList<>();
            if (invoiceFiles != null) {
                for (File invoiceFile : invoiceFiles) {
                    Invoice invoice = (Invoice) unmarshaller.unmarshal(invoiceFile);
                    invoices.add(invoice);
                }
            }
            return new SearchResult<Invoice>().success(invoices);
        } catch (Exception e) {
            return new SearchResult<Invoice>().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult persist(Invoice invoice) {
        try {
            invoice.setId(UUID.randomUUID().toString());
            File file = new File(INVOICES_DATA_URI + invoice.getId() + ".xml");
            if (file.exists()) {
                return new CrudsResult().error(Collections.singletonList("Factuur reeds aangemaakt!"));
            } else {
                Marshaller marshaller = marshaller();
                marshaller.marshal(invoice, new File(INVOICES_DATA_URI + invoice.getId() + ".xml"));

                return new CrudsResult().success(invoice.getId());
            }
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult update(Invoice invoice) {
        try {
            File file = new File(INVOICES_DATA_URI + invoice.getId() + ".xml");
            if (!file.exists()) {
                return new CrudsResult().error(Collections.singletonList("Onbekende factuur"));
            } else {
                Marshaller marshaller = marshaller();
                marshaller.marshal(invoice, new File(INVOICES_DATA_URI + invoice.getId() + ".xml"));

                return new CrudsResult().success(invoice.getId());
            }
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult delete(String id) {
        try {
            File invoiceFile = new File(INVOICES_DATA_URI + id + ".xml");
            boolean deleted = invoiceFile.delete();
            if (deleted) {
                return new CrudsResult().success(id);
            }
            return new CrudsResult().error(Collections.singletonList("Factuur verwijderen mislukt"));
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult deleteAll() {
        try {
            File invoiceDirectory = new File(INVOICES_DATA_URI);
            File[] invoiceFiles = invoiceDirectory.listFiles();
            if (invoiceFiles != null) {
                for (File invoiceFile : invoiceFiles) {
                    boolean deleted = invoiceFile.delete();
                    if (!deleted) {
                        return new CrudsResult().error(Collections.singletonList("Factuur verwijderen mislukt"));
                    }
                }
            }
            return new CrudsResult().success();
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    private Marshaller marshaller() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Invoice.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        return marshaller;
    }

    private Unmarshaller unmarshaller() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Invoice.class);
        return jaxbContext.createUnmarshaller();
    }
}
