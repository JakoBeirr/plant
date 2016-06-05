package be.boomkwekerij.plant.dao;

import be.boomkwekerij.plant.model.repository.System;
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

public class SystemDAOImpl implements SystemDAO {

    private static final String SYSTEM_DATA_URI = SystemRepository.getDataUri() + "/system/";

    public SearchResult<System> get(String id) {
        SearchResult<System> searchResult = new SearchResult<System>();

        try {
            Unmarshaller unmarshaller = getUnMarshaller();
            System system = (System) unmarshaller.unmarshal(new File(SYSTEM_DATA_URI + id + ".xml"));

            searchResult.setSuccess(true);
            searchResult.addResult(system);
        } catch (Exception e) {
            searchResult.setSuccess(false);
            searchResult.addMessage(ExceptionUtil.getStackTrace(e));
        }

        return searchResult;
    }

    public SearchResult<System> findAll() {
        SearchResult<System> searchResult = new SearchResult<System>();

        try {
            Unmarshaller unmarshaller = getUnMarshaller();
            File systemDirectory = new File(SYSTEM_DATA_URI);
            File[] systemFiles = systemDirectory.listFiles();
            if (systemFiles != null) {
                for (File systemFile : systemFiles) {
                    System system = (System) unmarshaller.unmarshal(systemFile);
                    searchResult.addResult(system);
                }
            }

            searchResult.setSuccess(true);
        } catch (Exception e) {
            searchResult.setSuccess(false);
            searchResult.addMessage(ExceptionUtil.getStackTrace(e));
        }

        return searchResult;
    }

    public CrudsResult persist(System system) {
        CrudsResult crudsResult = new CrudsResult();
        crudsResult.setValue(system.getId());

        try {
            system.setId(UUID.randomUUID().toString());
            File file = new File(SYSTEM_DATA_URI + system.getId() + ".xml");
            if (file.exists()) {
                crudsResult.setSuccess(false);
                crudsResult.addMessage("System already registered!");
            } else {
                Marshaller marshaller = getMarshaller();
                marshaller.marshal(system, new File(SYSTEM_DATA_URI + system.getId() + ".xml"));

                crudsResult.setSuccess(true);
            }
        } catch (Exception e) {
            crudsResult.setSuccess(false);
            crudsResult.addMessage(ExceptionUtil.getStackTrace(e));
        }

        return crudsResult;
    }

    public CrudsResult update(System system) {
        CrudsResult crudsResult = new CrudsResult();
        crudsResult.setValue(system.getId());

        try {
            File file = new File(SYSTEM_DATA_URI + system.getId() + ".xml");
            if (!file.exists()) {
                crudsResult.setSuccess(false);
                crudsResult.addMessage("Unknown system!");
            } else {
                Marshaller marshaller = getMarshaller();
                marshaller.marshal(system, new File(SYSTEM_DATA_URI + system.getId() + ".xml"));

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
            File systemFile = new File(SYSTEM_DATA_URI + id + ".xml");
            boolean deleted = systemFile.delete();
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
            File systemDirectory = new File(SYSTEM_DATA_URI);
            File[] systemFiles = systemDirectory.listFiles();
            if (systemFiles != null) {
                for (File systemFile : systemFiles) {
                    boolean deleted = systemFile.delete();
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
