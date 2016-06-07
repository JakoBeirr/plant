package be.boomkwekerij.plant.dao.memory;

import be.boomkwekerij.plant.model.repository.Company;

public interface CompanyMemory {

    void createCompany(Company company);

    void updateCompany(Company company);

    void deleteCompany();
}
