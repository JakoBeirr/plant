package be.boomkwekerij.plant.dao;

import be.boomkwekerij.plant.model.repository.Plant;
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

public class PlantDAOImpl implements PlantDAO {

    private static final String PLANTS_DATA_URI = SystemRepository.getDataUri() + "/plants/";

    public SearchResult<Plant> get(String id) {
        SearchResult<Plant> searchResult = new SearchResult<Plant>();

        try {
            Unmarshaller unmarshaller = getUnMarshaller();
            Plant plant = (Plant) unmarshaller.unmarshal(new File(PLANTS_DATA_URI + id + ".xml"));

            searchResult.setSuccess(true);
            searchResult.addResult(plant);
        } catch (Exception e) {
            searchResult.setSuccess(false);
            searchResult.addMessage(ExceptionUtil.getStackTrace(e));
        }

        return searchResult;
    }

    public SearchResult<Plant> findAll() {
        SearchResult<Plant> searchResult = new SearchResult<Plant>();

        try {
            Unmarshaller unmarshaller = getUnMarshaller();
            File plantDirectory = new File(PLANTS_DATA_URI);
            File[] plantFiles = plantDirectory.listFiles();
            if (plantFiles != null) {
                for (File plantFile : plantFiles) {
                    Plant plant = (Plant) unmarshaller.unmarshal(plantFile);
                    searchResult.addResult(plant);
                }
            }

            searchResult.setSuccess(true);
        } catch (Exception e) {
            searchResult.setSuccess(false);
            searchResult.addMessage(ExceptionUtil.getStackTrace(e));
        }

        return searchResult;
    }

    public CrudsResult persist(Plant plant) {
        CrudsResult crudsResult = new CrudsResult();
        crudsResult.setValue(plant.getId());

        try {
            plant.setId(UUID.randomUUID().toString());
            File file = new File(PLANTS_DATA_URI + plant.getId() + ".xml");
            if (file.exists()) {
                crudsResult.setSuccess(false);
                crudsResult.addMessage("Plant already registered!");
            } else {
                Marshaller marshaller = getMarshaller();
                marshaller.marshal(plant, new File(PLANTS_DATA_URI + plant.getId() + ".xml"));

                crudsResult.setSuccess(true);
            }
        } catch (Exception e) {
            crudsResult.setSuccess(false);
            crudsResult.addMessage(ExceptionUtil.getStackTrace(e));
        }

        return crudsResult;
    }

    public CrudsResult update(Plant plant) {
        CrudsResult crudsResult = new CrudsResult();
        crudsResult.setValue(plant.getId());

        try {
            File file = new File(PLANTS_DATA_URI + plant.getId() + ".xml");
            if (!file.exists()) {
                crudsResult.setSuccess(false);
                crudsResult.addMessage("Unknown plant!");
            } else {
                Marshaller marshaller = getMarshaller();
                marshaller.marshal(plant, new File(PLANTS_DATA_URI + plant.getId() + ".xml"));

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
            File plantFile = new File(PLANTS_DATA_URI + id + ".xml");
            boolean deleted = plantFile.delete();
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
            File plantDirectory = new File(PLANTS_DATA_URI);
            File[] plantFiles = plantDirectory.listFiles();
            if (plantFiles != null) {
                for (File plantFile : plantFiles) {
                    boolean deleted = plantFile.delete();
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
        JAXBContext jaxbContext = JAXBContext.newInstance(Plant.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        return marshaller;
    }

    private Unmarshaller getUnMarshaller() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Plant.class);
        return jaxbContext.createUnmarshaller();
    }
}
