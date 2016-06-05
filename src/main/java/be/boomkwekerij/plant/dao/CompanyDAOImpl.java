package be.boomkwekerij.plant.dao;

import be.boomkwekerij.plant.model.repository.Company;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.ExceptionUtil;
import be.boomkwekerij.plant.util.SearchResult;
import be.boomkwekerij.plant.util.SystemRepository;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class CompanyDAOImpl implements CompanyDAO {

    private static final String DATA_URI = SystemRepository.getDataUri();

    public SearchResult<Company> get(Long id) {
        SearchResult<Company> searchResult = new SearchResult<Company>();

        try {
            Unmarshaller unmarshaller = getUnMarshaller();
            Company company = (Company) unmarshaller.unmarshal(new File(DATA_URI + "/companies/" + id + ".xml"));

            searchResult.setSuccess(true);
            searchResult.addResult(company);
        } catch (Exception e) {
            searchResult.setSuccess(false);
            searchResult.addMessage(ExceptionUtil.getStackTrace(e));
        }

        return searchResult;
    }

    public SearchResult<Company> findAll() {
        SearchResult<Company> searchResult = new SearchResult<Company>();

        try {
            Unmarshaller unmarshaller = getUnMarshaller();
            File companyDirectory = new File(DATA_URI + "/companies");
            File[] companyFiles = companyDirectory.listFiles();
            if (companyFiles != null) {
                for (File companyFile : companyFiles) {
                    Company company = (Company) unmarshaller.unmarshal(companyFile);
                    searchResult.addResult(company);
                }
            }

            searchResult.setSuccess(true);
        } catch (Exception e) {
            searchResult.setSuccess(false);
            searchResult.addMessage(ExceptionUtil.getStackTrace(e));
        }

        return searchResult;
    }

    public CrudsResult persist(Company company) {
        CrudsResult crudsResult = new CrudsResult();
        crudsResult.setValue(company.getName());

        try {
            File file = new File(DATA_URI + "/companies/" + company.getId() + ".xml");
            if (file.exists()) {
                crudsResult.setSuccess(false);
                crudsResult.addMessage("Company already registered!");
            } else {
                Marshaller marshaller = getMarshaller();
                marshaller.marshal(company, new File(DATA_URI + "/companies/" + company.getId() + ".xml"));

                crudsResult.setSuccess(true);
            }
        } catch (Exception e) {
            crudsResult.setSuccess(false);
            crudsResult.addMessage(ExceptionUtil.getStackTrace(e));
        }

        return crudsResult;
    }

    public CrudsResult update(Company company) {
        CrudsResult crudsResult = new CrudsResult();
        crudsResult.setValue(company.getName());

        try {
            File file = new File(DATA_URI + "/companies/" + company.getId() + ".xml");
            if (!file.exists()) {
                crudsResult.setSuccess(false);
                crudsResult.addMessage("Unknown company!");
            } else {
                Marshaller marshaller = getMarshaller();
                marshaller.marshal(company, new File(DATA_URI + "/companies/" + company.getId() + ".xml"));

                crudsResult.setSuccess(true);
            }
        } catch (Exception e) {
            crudsResult.setSuccess(false);
            crudsResult.addMessage(ExceptionUtil.getStackTrace(e));
        }

        return crudsResult;
    }

    public CrudsResult delete(Long id) {
        CrudsResult crudsResult = new CrudsResult();

        try {
            File companyFile = new File(DATA_URI + "/companies/" + id + ".xml");
            boolean deleted = companyFile.delete();
            crudsResult.setSuccess(deleted);
            crudsResult.setValue(Long.toString(id));
        } catch (Exception e) {
            crudsResult.setSuccess(false);
            crudsResult.addMessage(ExceptionUtil.getStackTrace(e));
        }

        return crudsResult;
    }

    public CrudsResult deleteAll() {
        CrudsResult crudsResult = new CrudsResult();

        try {
            File companyDirectory = new File(DATA_URI + "/companies");
            File[] companyFiles = companyDirectory.listFiles();
            if (companyFiles != null) {
                for (File companyFile : companyFiles) {
                    boolean deleted = companyFile.delete();
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
