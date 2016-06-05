package be.boomkwekerij.plant.dao;

import be.boomkwekerij.plant.model.repository.Company;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class CompanyDAOImpl implements CompanyDAO {

    public SearchResult<Company> get(Long id) {
        SearchResult<Company> searchResult = new SearchResult<Company>();

        try {
            Unmarshaller unmarshaller = getUnMarshaller();
            Company company = (Company) unmarshaller.unmarshal(new File("c:/Users/Janse/Desktop/companies/" + id + ".xml"));

            searchResult.setSuccess(true);
            searchResult.addResult(company);
        } catch (JAXBException e) {
            searchResult.setSuccess(false);
            searchResult.addMessage(e.getMessage());
        }

        return searchResult;
    }

    public SearchResult<Company> findAll() {
        SearchResult<Company> searchResult = new SearchResult<Company>();

        try {
            Unmarshaller unmarshaller = getUnMarshaller();
            File companyDirectory = new File("c:/Users/Janse/Desktop/companies");
            File[] companyFiles = companyDirectory.listFiles();
            for (File companyFile : companyFiles) {
                Company company = (Company) unmarshaller.unmarshal(companyFile);
                searchResult.addResult(company);
            }

            searchResult.setSuccess(true);
        } catch (JAXBException e) {
            searchResult.setSuccess(false);
            searchResult.addMessage(e.getMessage());
        }

        return searchResult;
    }

    public CrudsResult persist(Company company) {
        CrudsResult crudsResult = new CrudsResult();
        crudsResult.setValue(company.getName());

        try {
            Marshaller marshaller = getMarshaller();
            marshaller.marshal(company, new File("c:/Users/Janse/Desktop/companies/" + company.getId() + ".xml"));

            crudsResult.setSuccess(true);
        } catch (JAXBException e) {
            crudsResult.setSuccess(false);
            crudsResult.addMessage(e.getMessage());
        }

        return crudsResult;
    }

    public CrudsResult update(Company company) {
        CrudsResult crudsResult = new CrudsResult();
        crudsResult.setValue(company.getName());

        File file = new File("c:/Users/Janse/Desktop/companies/" + company.getId() + ".xml");
        if (!file.exists()) {
            crudsResult.setSuccess(false);
            crudsResult.addMessage("Unknown company");
        } else {
            try {
                Marshaller marshaller = getMarshaller();
                marshaller.marshal(company, new File("c:/Users/Janse/Desktop/companies/" + company.getId() + ".xml"));

                crudsResult.setSuccess(true);
            } catch (JAXBException e) {
                crudsResult.setSuccess(false);
                crudsResult.addMessage(e.getMessage());
            }
        }

        return crudsResult;
    }

    public CrudsResult delete(Long id) {
        CrudsResult crudsResult = new CrudsResult();

        File companyFile = new File("c:/Users/Janse/Desktop/companies/" + id + ".xml");
        boolean deleted = companyFile.delete();
        crudsResult.setSuccess(deleted);
        crudsResult.setValue(Long.toString(id));

        return crudsResult;
    }

    public CrudsResult deleteAll() {
        CrudsResult crudsResult = new CrudsResult();

        File companyDirectory = new File("c:/Users/Janse/Desktop/companies");
        File[] companyFiles = companyDirectory.listFiles();
        for (File companyFile : companyFiles) {
            boolean deleted = companyFile.delete();
            crudsResult.setSuccess(deleted);
        }

        return crudsResult;
    }

    private Marshaller getMarshaller() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Company.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        return marshaller;
    }

    private Unmarshaller getUnMarshaller() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Company.class);
        return jaxbContext.createUnmarshaller();
    }
}
