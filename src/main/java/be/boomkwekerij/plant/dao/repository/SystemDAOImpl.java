package be.boomkwekerij.plant.dao.repository;

import be.boomkwekerij.plant.model.repository.System;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.ExceptionUtil;
import be.boomkwekerij.plant.util.SearchResult;
import be.boomkwekerij.plant.util.Initializer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class SystemDAOImpl implements SystemDAO {

    private static final String SYSTEM_DATA_URI = Initializer.getDataUri() + "/system/";

    public SearchResult<System> get() {
        SearchResult<System> searchResult = new SearchResult<System>();

        try {
            Unmarshaller unmarshaller = getUnMarshaller();
            System system = (System) unmarshaller.unmarshal(new File(SYSTEM_DATA_URI + "system.xml"));

            searchResult.setSuccess(true);
            searchResult.addResult(system);
        } catch (Exception e) {
            searchResult.setSuccess(false);
            searchResult.addMessage(e.getMessage());
        }

        return searchResult;
    }

    public CrudsResult persist(System system) {
        CrudsResult crudsResult = new CrudsResult();

        try {
            File file = new File(SYSTEM_DATA_URI + "system.xml");
            if (file.exists()) {
                crudsResult.setSuccess(false);
                crudsResult.addMessage("System already registered!");
            } else {
                Marshaller marshaller = getMarshaller();
                marshaller.marshal(system, new File(SYSTEM_DATA_URI + "system.xml"));

                crudsResult.setSuccess(true);
            }
        } catch (Exception e) {
            crudsResult.setSuccess(false);
            crudsResult.addMessage(e.getMessage());
        }

        return crudsResult;
    }

    public CrudsResult update(System system) {
        CrudsResult crudsResult = new CrudsResult();

        try {
            File file = new File(SYSTEM_DATA_URI + "system.xml");
            if (!file.exists()) {
                crudsResult.setSuccess(false);
                crudsResult.addMessage("Unknown system!");
            } else {
                Marshaller marshaller = getMarshaller();
                marshaller.marshal(system, new File(SYSTEM_DATA_URI + "system.xml"));

                crudsResult.setSuccess(true);
            }
        } catch (Exception e) {
            crudsResult.setSuccess(false);
            crudsResult.addMessage(e.getMessage());
        }

        return crudsResult;
    }

    public CrudsResult delete() {
        CrudsResult crudsResult = new CrudsResult();

        try {
            File systemFile = new File(SYSTEM_DATA_URI + "system.xml");
            boolean deleted = systemFile.delete();
            crudsResult.setSuccess(deleted);
        } catch (Exception e) {
            crudsResult.setSuccess(false);
            crudsResult.addMessage(e.getMessage());
        }

        return crudsResult;
    }

    private Marshaller getMarshaller() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(System.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        return marshaller;
    }

    private Unmarshaller getUnMarshaller() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(System.class);
        return jaxbContext.createUnmarshaller();
    }
}
