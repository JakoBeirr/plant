package be.boomkwekerij.plant.dao.memory;

import be.boomkwekerij.plant.model.repository.Company;
import be.boomkwekerij.plant.util.SearchResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CompanyMemoryImpl implements CompanyMemory {

    private HashMap<String, Company> companies = new HashMap<String, Company>();

    public void createCompany(Company company) {
        companies.put(company.getId(), company);
    }

    public void createCompanies(List<Company> companyList) {
        for (Company company : companyList) {
            companies.put(company.getId(), company);
        }
    }

    public SearchResult<Company> getCompany(String id) {
        SearchResult<Company> searchResult = new SearchResult<Company>();
        searchResult.setSuccess(true);
        searchResult.addResult(companies.get(id));
        return searchResult;
    }

    public SearchResult<Company> getCompanies() {
        SearchResult<Company> searchResult = new SearchResult<Company>();
        searchResult.setSuccess(true);
        searchResult.setResults(new ArrayList<Company>(companies.values()));
        return searchResult;
    }

    public SearchResult<Company> getCompanies(String name) {
        SearchResult<Company> companiesWithName = new SearchResult<Company>();

        for (Company company : companies.values()) {
            if (companyNameStartsWith(company, name)) {
                companiesWithName.addResult(company);
            }
        }

        companiesWithName.setSuccess(true);
        return companiesWithName;
    }

    private boolean companyNameStartsWith(Company company, String name) {
        return company.getName() != null && company.getName().toUpperCase().startsWith(name.toUpperCase());
    }

    public void deleteCompany(String id) {
        companies.remove(id);
    }
}
