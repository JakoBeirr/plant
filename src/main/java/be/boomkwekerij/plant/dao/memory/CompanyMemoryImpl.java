package be.boomkwekerij.plant.dao.memory;

import be.boomkwekerij.plant.model.repository.Company;
import be.boomkwekerij.plant.util.SearchResult;

public class CompanyMemoryImpl implements CompanyMemory {

    private Company company = null;

    public void createCompany(Company company) {
        this.company = company;
    }

    public SearchResult<Company> getCompany() {
        SearchResult<Company> searchResult = new SearchResult<Company>();
        searchResult.setSuccess(true);
        searchResult.addResult(company);
        return searchResult;
    }

    public void updateCompany(Company company) {
        this.company = company;
    }

    public void deleteCompany() {
        company = null;
    }
}
