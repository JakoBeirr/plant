package be.boomkwekerij.plant;

import be.boomkwekerij.plant.dao.CompanyDAO;
import be.boomkwekerij.plant.dao.CompanyDAOImpl;
import be.boomkwekerij.plant.model.repository.Company;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;

import java.util.List;

public class Client {

    public static void main(String[] args) {
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

        System.out.println();

        companyDAO.delete((long) 2);

        all = companyDAO.findAll();
        results = all.getResults();
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

        companyDAO.deleteAll();

        all = companyDAO.findAll();
        results = all.getResults();
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
