package be.boomkwekerij.plant.dao.memory;

import be.boomkwekerij.plant.model.repository.Company;
import be.boomkwekerij.plant.util.SearchResult;

import java.util.List;

public interface CompanyMemory {

    void createCompany(Company company);

    void createCompanies(List<Company> companies);

    SearchResult<Company> getCompany(String id);

    SearchResult<Company> getCompanies();

    SearchResult<Company> getCompanies(String name);

    void deleteCompany(String id);
}
