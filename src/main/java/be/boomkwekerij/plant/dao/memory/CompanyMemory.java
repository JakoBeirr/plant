package be.boomkwekerij.plant.dao.memory;

import be.boomkwekerij.plant.model.repository.Company;
import be.boomkwekerij.plant.util.SearchResult;

public interface CompanyMemory {

    void createCompany(Company company);

    SearchResult<Company> getCompany();

    void updateCompany(Company company);

    void deleteCompany();
}
