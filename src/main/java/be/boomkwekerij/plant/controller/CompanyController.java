package be.boomkwekerij.plant.controller;

import be.boomkwekerij.plant.model.dto.CompanyDTO;
import be.boomkwekerij.plant.service.CompanyService;
import be.boomkwekerij.plant.service.CompanyServiceImpl;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.ExceptionUtil;
import be.boomkwekerij.plant.util.SearchResult;

public class CompanyController {

    private CompanyService companyService = new CompanyServiceImpl();

    public CrudsResult createCompany(CompanyDTO companyDTO) {
        try {
            return companyService.createCompany(companyDTO);
        } catch (Exception e) {
            return createCrudsError(e);
        }
    }

    public SearchResult<CompanyDTO> getCompany() {
        try {
            return companyService.getCompany();
        } catch (Exception e) {
            return createSearchError(e);
        }
    }

    public CrudsResult updateCompany(CompanyDTO companyDTO) {
        try {
            return companyService.updateCompany(companyDTO);
        } catch (Exception e) {
            return createCrudsError(e);
        }
    }

    private SearchResult<CompanyDTO> createSearchError(Exception e) {
        SearchResult<CompanyDTO> failure = new SearchResult<CompanyDTO>();
        failure.setSuccess(false);
        failure.getMessages().add(ExceptionUtil.getStackTrace(e));
        return failure;
    }

    private CrudsResult createCrudsError(Exception e) {
        CrudsResult failure = new CrudsResult();
        failure.setSuccess(false);
        failure.getMessages().add(ExceptionUtil.getStackTrace(e));
        return failure;
    }
}
