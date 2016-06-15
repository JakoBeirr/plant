package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.model.dto.CompanyDTO;
import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;

public interface CompanyService {

    CrudsResult createCompany(CompanyDTO company);

    SearchResult<CompanyDTO> getCompany();

    CrudsResult updateCompany(CompanyDTO company);

    CrudsResult deleteCompany();
}
