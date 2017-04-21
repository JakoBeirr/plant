package be.boomkwekerij.plant.dao.repository;

import be.boomkwekerij.plant.model.repository.Plant;
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

public class PlantDAOImpl implements PlantDAO {

    private final String plantsDataUri;

    public PlantDAOImpl() {
        plantsDataUri = InitializerSingleton.getInitializer().getDataDirectory() + "/plants/";
    }

    public SearchResult<Plant> get(String id) {
        try {
            Unmarshaller unmarshaller = unmarshaller();
            Plant plant = (Plant) unmarshaller.unmarshal(new File(plantsDataUri + id + ".xml"));

            return new SearchResult<Plant>().success(Collections.singletonList(plant));
        } catch (Exception e) {
            return new SearchResult<Plant>().error(Collections.singletonList(e.getMessage()));
        }
    }

    public SearchResult<Plant> findAll() {
        try {
            Unmarshaller unmarshaller = unmarshaller();
            File plantDirectory = new File(plantsDataUri);
            File[] plantFiles = plantDirectory.listFiles();

            List<Plant> plants = new ArrayList<>();
            if (plantFiles != null) {
                for (File plantFile : plantFiles) {
                    Plant plant = (Plant) unmarshaller.unmarshal(plantFile);
                    plants.add(plant);
                }
            }
            return new SearchResult<Plant>().success(plants);
        } catch (Exception e) {
            return new SearchResult<Plant>().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult persist(Plant plant) {
        try {
            plant.setId(UUID.randomUUID().toString());
            File file = new File(plantsDataUri + plant.getId() + ".xml");
            if (file.exists()) {
                return new CrudsResult().error(Collections.singletonList("Plant reeds aangemaakt"));
            } else {
                Marshaller marshaller = marshaller();
                marshaller.marshal(plant, new File(plantsDataUri + plant.getId() + ".xml"));

                return new CrudsResult().success(plant.getId());
            }
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult update(Plant plant) {
        try {
            File file = new File(plantsDataUri + plant.getId() + ".xml");
            if (!file.exists()) {
                return new CrudsResult().error(Collections.singletonList("Onbekende plant"));
            } else {
                Marshaller marshaller = marshaller();
                marshaller.marshal(plant, new File(plantsDataUri + plant.getId() + ".xml"));

                return new CrudsResult().success(plant.getId());
            }
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult delete(String id) {
        try {
            File plantFile = new File(plantsDataUri + id + ".xml");
            boolean deleted = plantFile.delete();
            if (deleted) {
                return new CrudsResult().success(id);
            }
            return new CrudsResult().error(Collections.singletonList("Plant verwijderen mislukt"));
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult deleteAll() {
        try {
            File plantDirectory = new File(plantsDataUri);
            File[] plantFiles = plantDirectory.listFiles();
            if (plantFiles != null) {
                for (File plantFile : plantFiles) {
                    boolean deleted = plantFile.delete();
                    if (!deleted) {
                        return new CrudsResult().error(Collections.singletonList("Plant verwijderen mislukt"));
                    }
                }
            }
            return new CrudsResult().success();
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    private Marshaller marshaller() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Plant.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        return marshaller;
    }

    private Unmarshaller unmarshaller() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Plant.class);
        return jaxbContext.createUnmarshaller();
    }
}
