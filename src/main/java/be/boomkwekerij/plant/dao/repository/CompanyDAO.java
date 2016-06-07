package be.boomkwekerij.plant.dao.repository;

import be.boomkwekerij.plant.model.repository.Company;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;

public interface CompanyDAO{

    SearchResult<Company> get();

    CrudsResult persist(Company company);

    CrudsResult update(Company company);

    CrudsResult delete();
}
