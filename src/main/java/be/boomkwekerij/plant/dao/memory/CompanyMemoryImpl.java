package be.boomkwekerij.plant.dao.memory;

import be.boomkwekerij.plant.model.repository.Company;
import be.boomkwekerij.plant.util.SearchResult;

import java.util.Collections;

public class CompanyMemoryImpl implements CompanyMemory {

    private Company company = null;

    public void createCompany(Company company) {
        this.company = company;
    }

    public SearchResult<Company> getCompany() {
        if (company != null) {
            return new SearchResult<Company>().success(Collections.singletonList(company));
        }
        return new SearchResult<Company>().error(Collections.singletonList("Bedrijf niet gevonden"));
    }

    public void updateCompany(Company company) {
        this.company = company;
    }

    public void deleteCompany() {
        company = null;
    }
}
