package be.boomkwekerij.plant.dao.repository;

import be.boomkwekerij.plant.model.repository.Company;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.Initializer;
import be.boomkwekerij.plant.util.SearchResult;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Collections;

public class CompanyDAOImpl implements CompanyDAO {

    private static final String COMPANIES_DATA_URI = Initializer.getDataUri() + "/company/";

    public SearchResult<Company> get() {
        try {
            Unmarshaller unmarshaller = unmarshaller();
            Company company = (Company) unmarshaller.unmarshal(new File(COMPANIES_DATA_URI + "company.xml"));

            return new SearchResult<Company>().success(Collections.singletonList(company));
        } catch (Exception e) {
            return new SearchResult<Company>().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult persist(Company company) {
        try {
            File file = new File(COMPANIES_DATA_URI + "company.xml");
            if (file.exists()) {
                return new CrudsResult().error(Collections.singletonList("Bedrijf reeds geregistreerd!"));
            } else {
                Marshaller marshaller = marshaller();
                marshaller.marshal(company, new File(COMPANIES_DATA_URI + "company.xml"));

                return new CrudsResult().success(company.getName());
            }
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult update(Company company) {
        try {
            File file = new File(COMPANIES_DATA_URI + "company.xml");
            if (!file.exists()) {
                return new CrudsResult().error(Collections.singletonList("Onbekend bedrijf!"));
            } else {
                Marshaller marshaller = marshaller();
                marshaller.marshal(company, new File(COMPANIES_DATA_URI + "company.xml"));

                return new CrudsResult().success(company.getName());
            }
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult delete() {
        try {
            File companyFile = new File(COMPANIES_DATA_URI + "company.xml");
            boolean deleted = companyFile.delete();

            if (deleted) {
                return new CrudsResult().success();
            }
            return new CrudsResult().error(Collections.singletonList("Kon bedrijf niet verwijderen"));
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    private Marshaller marshaller() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Company.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        return marshaller;
    }

    private Unmarshaller unmarshaller() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Company.class);
        return jaxbContext.createUnmarshaller();
    }
}
