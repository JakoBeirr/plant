package be.boomkwekerij.plant.dao.repository;

import be.boomkwekerij.plant.model.repository.Fust;
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

public class FustDAOImpl implements FustDAO {

    private final String fustsDataUri;

    public FustDAOImpl() {
        fustsDataUri = InitializerSingleton.getInitializer().getDataDirectory() + "/fusts/";
    }

    public SearchResult<Fust> get(String id) {
        try {
            Unmarshaller unmarshaller = unmarshaller();
            Fust fust = (Fust) unmarshaller.unmarshal(new File(fustsDataUri + id + ".xml"));

            return new SearchResult<Fust>().success(Collections.singletonList(fust));
        } catch (Exception e) {
            return new SearchResult<Fust>().error(Collections.singletonList(e.getMessage()));
        }
    }

    public SearchResult<Fust> findAll() {
        try {
            Unmarshaller unmarshaller = unmarshaller();
            File fustDirectory = new File(fustsDataUri);
            File[] fustFiles = fustDirectory.listFiles();

            List<Fust> fusts = new ArrayList<>();
            if (fustFiles != null) {
                for (File fustFile : fustFiles) {
                    Fust fust = (Fust) unmarshaller.unmarshal(fustFile);
                    fusts.add(fust);
                }
            }
            return new SearchResult<Fust>().success(fusts);
        } catch (Exception e) {
            return new SearchResult<Fust>().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult persist(Fust fust) {
        try {
            fust.setId(UUID.randomUUID().toString());
            File file = new File(fustsDataUri + fust.getId() + ".xml");
            if (file.exists()) {
                return new CrudsResult().error(Collections.singletonList("Fust reeds aangemaakt!"));
            } else {
                Marshaller marshaller = marshaller();
                marshaller.marshal(fust, new File(fustsDataUri + fust.getId() + ".xml"));

                return new CrudsResult().success(fust.getId());
            }
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult update(Fust fust) {
        try {
            File file = new File(fustsDataUri + fust.getId() + ".xml");
            if (!file.exists()) {
                return new CrudsResult().error(Collections.singletonList("Onbekende fust"));
            } else {
                Marshaller marshaller = marshaller();
                marshaller.marshal(fust, new File(fustsDataUri + fust.getId() + ".xml"));

                return new CrudsResult().success(fust.getId());
            }
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult delete(String id) {
        try {
            File fustFile = new File(fustsDataUri + id + ".xml");
            boolean deleted = fustFile.delete();
            if (deleted) {
                return new CrudsResult().success(id);
            }
            return new CrudsResult().error(Collections.singletonList("Fust verwijderen mislukt"));
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult deleteAll() {
        try {
            File fustDirectory = new File(fustsDataUri);
            File[] fustFiles = fustDirectory.listFiles();
            if (fustFiles != null) {
                for (File fustFile : fustFiles) {
                    boolean deleted = fustFile.delete();
                    if (!deleted) {
                        return new CrudsResult().error(Collections.singletonList("Fust verwijderen mislukt"));
                    }
                }
            }
            return new CrudsResult().success();
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    private Marshaller marshaller() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Fust.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        return marshaller;
    }

    private Unmarshaller unmarshaller() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Fust.class);
        return jaxbContext.createUnmarshaller();
    }
}
