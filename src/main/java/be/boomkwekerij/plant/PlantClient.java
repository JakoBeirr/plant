package be.boomkwekerij.plant;

import be.boomkwekerij.plant.dao.CompanyDAO;
import be.boomkwekerij.plant.dao.CompanyDAOImpl;
import be.boomkwekerij.plant.model.repository.Company;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;
import be.boomkwekerij.plant.util.SystemRepository;

import java.util.List;

public class PlantClient {

    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Data uri not specified!");
        }

        String dataUri = args[0];
        SystemRepository.init(dataUri);

        CompanyDAO companyDAO = new CompanyDAOImpl();

        Company company = getCompany(1);
        companyDAO.persist(company);
        Company company2 = getCompany(2);
        companyDAO.persist(company2);

        SearchResult<Company> searchResult = companyDAO.get((long) 1);
        Company first = searchResult.getFirst();

        System.out.println("Company gevonden: " + searchResult.isSuccess() + ", aantal: " + searchResult.getResults().size());
        System.out.println("=============================================");
        System.out.println("Telefoonnummer: " + first.getTelephone());
        System.out.println("KBC: " + first.getAccountNumberBelgium());
        System.out.println("Rabobank: " + first.getAccountNumberNetherlands());
        System.out.println("BTW-nummer: " + first.getBtwNumber());

        System.out.println();

        SearchResult<Company> all = companyDAO.findAll();
        List<Company> results = all.getResults();
        System.out.println("Company gevonden: " + searchResult.isSuccess() + ", aantal: " + results.size());
        System.out.println("=============================================");
        for (Company companyResult : results) {
            System.out.println("Naam: " + companyResult.getName());
            System.out.println("Telefoonnummer: " + companyResult.getTelephone());
            System.out.println("KBC: " + companyResult.getAccountNumberBelgium());
            System.out.println("Rabobank: " + companyResult.getAccountNumberNetherlands());
            System.out.println("BTW-nummer: " + companyResult.getBtwNumber());
            System.out.println("=============================================");
        }

        System.out.println();

        company.setName("test3");
        companyDAO.update(company);

        Company unknownCompany = new Company();
        unknownCompany.setId(5);
        CrudsResult update = companyDAO.update(unknownCompany);
        System.out.println("Update gelukt: " + update.isSuccess() + ", reden: " + update.getMessages().get(0));
    }

    private static Company getCompany(long id) {
        Company company = new Company();
        company.setId(id);
        company.setName("test");
        company.setTelephone("test");
        company.setFax("test");
        company.setGsm("test");
        company.setAccountNumberBelgium("BEtest");
        company.setIbanBelgium("BEtest");
        company.setBicBelgium("BEtest");
        company.setAccountNumberNetherlands("NLtest");
        company.setIbanNetherlands("NLtest");
        company.setBicNetherlands("NLtest");
        company.setBtwNumber("test");
        return company;
    }
}
