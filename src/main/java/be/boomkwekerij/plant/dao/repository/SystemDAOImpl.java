package be.boomkwekerij.plant.dao.repository;

import be.boomkwekerij.plant.model.repository.System;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.InitializerSingleton;
import be.boomkwekerij.plant.util.SearchResult;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Collections;

public class SystemDAOImpl implements SystemDAO {

    private final String systemDataUri;

    public SystemDAOImpl() {
        systemDataUri = InitializerSingleton.getInitializer().getDataDirectory() + "/system/";
    }

    public SearchResult<System> get() {
        try {
            Unmarshaller unmarshaller = unmarshaller();
            System system = (System) unmarshaller.unmarshal(new File(systemDataUri + "system.xml"));

            return new SearchResult<System>().success(Collections.singletonList(system));
        } catch (Exception e) {
            return new SearchResult<System>().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult persist(System system) {
        try {
            File file = new File(systemDataUri + "system.xml");
            if (file.exists()) {
                return new CrudsResult().error(Collections.singletonList("Systeem reeds geregistreerd"));
            } else {
                Marshaller marshaller = marshaller();
                marshaller.marshal(system, new File(systemDataUri + "system.xml"));

                return new CrudsResult().success(system.getNextInvoiceNumber());
            }
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult update(System system) {
        try {
            File file = new File(systemDataUri + "system.xml");
            if (!file.exists()) {
                return new CrudsResult().error(Collections.singletonList("Onbekend systeem"));
            } else {
                Marshaller marshaller = marshaller();
                marshaller.marshal(system, new File(systemDataUri + "system.xml"));

                return new CrudsResult().success(system.getNextInvoiceNumber());
            }
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult delete() {
        try {
            File systemFile = new File(systemDataUri + "system.xml");
            boolean deleted = systemFile.delete();
            if (deleted) {
                return new CrudsResult().success();
            }
            return new CrudsResult().error(Collections.singletonList("Systeem verwijderen mislukt"));
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    private Marshaller marshaller() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(System.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        return marshaller;
    }

    private Unmarshaller unmarshaller() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(System.class);
        return jaxbContext.createUnmarshaller();
    }
}
