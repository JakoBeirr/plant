package be.boomkwekerij.plant.dao.repository;

import be.boomkwekerij.plant.model.repository.Invoice;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.ExceptionUtil;
import be.boomkwekerij.plant.util.SearchResult;
import be.boomkwekerij.plant.util.Initializer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.UUID;

public class InvoiceDAOImpl implements InvoiceDAO {

    private static final String INVOICES_DATA_URI = Initializer.getDataUri() + "/invoices/";

    public SearchResult<Invoice> get(String id) {
        SearchResult<Invoice> searchResult = new SearchResult<Invoice>();

        try {
            Unmarshaller unmarshaller = getUnMarshaller();
            Invoice invoice = (Invoice) unmarshaller.unmarshal(new File(INVOICES_DATA_URI + id + ".xml"));

            searchResult.setSuccess(true);
            searchResult.addResult(invoice);
        } catch (Exception e) {
            searchResult.setSuccess(false);
            searchResult.addMessage(e.getMessage());
        }

        return searchResult;
    }

    public SearchResult<Invoice> findAll() {
        SearchResult<Invoice> searchResult = new SearchResult<Invoice>();

        try {
            Unmarshaller unmarshaller = getUnMarshaller();
            File invoiceDirectory = new File(INVOICES_DATA_URI);
            File[] invoiceFiles = invoiceDirectory.listFiles();
            if (invoiceFiles != null) {
                for (File invoiceFile : invoiceFiles) {
                    Invoice invoice = (Invoice) unmarshaller.unmarshal(invoiceFile);
                    searchResult.addResult(invoice);
                }
            }

            searchResult.setSuccess(true);
        } catch (Exception e) {
            searchResult.setSuccess(false);
            searchResult.addMessage(e.getMessage());
        }

        return searchResult;
    }

    public CrudsResult persist(Invoice invoice) {
        CrudsResult crudsResult = new CrudsResult();

        try {
            invoice.setId(UUID.randomUUID().toString());
            File file = new File(INVOICES_DATA_URI + invoice.getId() + ".xml");
            if (file.exists()) {
                crudsResult.setSuccess(false);
                crudsResult.addMessage("Invoice already registered!");
            } else {
                Marshaller marshaller = getMarshaller();
                marshaller.marshal(invoice, new File(INVOICES_DATA_URI + invoice.getId() + ".xml"));

                crudsResult.setValue(invoice.getId());
                crudsResult.setSuccess(true);
            }
        } catch (Exception e) {
            crudsResult.setSuccess(false);
            crudsResult.addMessage(e.getMessage());
        }

        return crudsResult;
    }

    public CrudsResult update(Invoice invoice) {
        CrudsResult crudsResult = new CrudsResult();
        crudsResult.setValue(invoice.getId());

        try {
            File file = new File(INVOICES_DATA_URI + invoice.getId() + ".xml");
            if (!file.exists()) {
                crudsResult.setSuccess(false);
                crudsResult.addMessage("Unknown invoice!");
            } else {
                Marshaller marshaller = getMarshaller();
                marshaller.marshal(invoice, new File(INVOICES_DATA_URI + invoice.getId() + ".xml"));

                crudsResult.setSuccess(true);
            }
        } catch (Exception e) {
            crudsResult.setSuccess(false);
            crudsResult.addMessage(e.getMessage());
        }

        return crudsResult;
    }

    public CrudsResult delete(String id) {
        CrudsResult crudsResult = new CrudsResult();

        try {
            File invoiceFile = new File(INVOICES_DATA_URI + id + ".xml");
            boolean deleted = invoiceFile.delete();
            crudsResult.setSuccess(deleted);
            crudsResult.setValue(id);
        } catch (Exception e) {
            crudsResult.setSuccess(false);
            crudsResult.addMessage(e.getMessage());
        }

        return crudsResult;
    }

    public CrudsResult deleteAll() {
        CrudsResult crudsResult = new CrudsResult();

        try {
            File invoiceDirectory = new File(INVOICES_DATA_URI);
            File[] invoiceFiles = invoiceDirectory.listFiles();
            if (invoiceFiles != null) {
                for (File invoiceFile : invoiceFiles) {
                    boolean deleted = invoiceFile.delete();
                    crudsResult.setSuccess(deleted);
                }
            }
        } catch (Exception e) {
            crudsResult.setSuccess(false);
            crudsResult.addMessage(e.getMessage());
        }

        return crudsResult;
    }

    private Marshaller getMarshaller() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Invoice.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        return marshaller;
    }

    private Unmarshaller getUnMarshaller() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Invoice.class);
        return jaxbContext.createUnmarshaller();
    }
}
