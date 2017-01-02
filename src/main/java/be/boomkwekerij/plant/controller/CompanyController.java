package be.boomkwekerij.plant.controller;

import be.boomkwekerij.plant.model.dto.CompanyDTO;
import be.boomkwekerij.plant.service.CompanyService;
import be.boomkwekerij.plant.service.CompanyServiceImpl;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;

import java.util.Collections;

public class CompanyController {

    private CompanyService companyService = new CompanyServiceImpl();

    public CrudsResult createCompany(CompanyDTO companyDTO) {
        try {
            return companyService.createCompany(companyDTO);
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    public SearchResult<CompanyDTO> getCompany() {
        try {
            return companyService.getCompany();
        } catch (Exception e) {
            return new SearchResult<CompanyDTO>().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult updateCompany(CompanyDTO companyDTO) {
        try {
            return companyService.updateCompany(companyDTO);
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }
}
