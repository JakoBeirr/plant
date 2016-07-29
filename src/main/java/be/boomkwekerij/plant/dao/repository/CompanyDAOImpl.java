package be.boomkwekerij.plant.dao.repository;

import be.boomkwekerij.plant.model.repository.Company;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.ExceptionUtil;
import be.boomkwekerij.plant.util.SearchResult;
import be.boomkwekerij.plant.util.Initializer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class CompanyDAOImpl implements CompanyDAO {

    private static final String COMPANIES_DATA_URI = Initializer.getDataUri() + "/company/";

    public SearchResult<Company> get() {
        SearchResult<Company> searchResult = new SearchResult<Company>();

        try {
            Unmarshaller unmarshaller = getUnMarshaller();
            Company company = (Company) unmarshaller.unmarshal(new File(COMPANIES_DATA_URI + "company.xml"));

            searchResult.setSuccess(true);
            searchResult.addResult(company);
        } catch (Exception e) {
            searchResult.setSuccess(false);
            searchResult.addMessage(e.getMessage());
        }

        return searchResult;
    }

    public CrudsResult persist(Company company) {
        CrudsResult crudsResult = new CrudsResult();

        try {
            File file = new File(COMPANIES_DATA_URI + "company.xml");
            if (file.exists()) {
                crudsResult.setSuccess(false);
                crudsResult.addMessage("Company already registered!");
            } else {
                Marshaller marshaller = getMarshaller();
                marshaller.marshal(company, new File(COMPANIES_DATA_URI + "company.xml"));

                crudsResult.setSuccess(true);
            }
        } catch (Exception e) {
            crudsResult.setSuccess(false);
            crudsResult.addMessage(e.getMessage());
        }

        return crudsResult;
    }

    public CrudsResult update(Company company) {
        CrudsResult crudsResult = new CrudsResult();

        try {
            File file = new File(COMPANIES_DATA_URI + "company.xml");
            if (!file.exists()) {
                crudsResult.setSuccess(false);
                crudsResult.addMessage("Unknown company!");
            } else {
                Marshaller marshaller = getMarshaller();
                marshaller.marshal(company, new File(COMPANIES_DATA_URI + "company.xml"));

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
            File companyFile = new File(COMPANIES_DATA_URI + "company.xml");
            boolean deleted = companyFile.delete();
            crudsResult.setSuccess(deleted);
        } catch (Exception e) {
            crudsResult.setSuccess(false);
            crudsResult.addMessage(e.getMessage());
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
