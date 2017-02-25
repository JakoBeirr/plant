package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.dao.memory.CompanyMemory;
import be.boomkwekerij.plant.dao.repository.CompanyDAO;
import be.boomkwekerij.plant.dao.repository.CompanyDAOImpl;
import be.boomkwekerij.plant.mapper.CompanyMapper;
import be.boomkwekerij.plant.model.dto.CompanyDTO;
import be.boomkwekerij.plant.model.repository.Company;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.MemoryDatabase;
import be.boomkwekerij.plant.util.SearchResult;
import be.boomkwekerij.plant.validator.CompanyValidator;

import java.util.Collections;
import java.util.List;

public class CompanyServiceImpl implements CompanyService {

    private CompanyDAO companyDAO = new CompanyDAOImpl();
    private CompanyMemory companyMemory = MemoryDatabase.getCompanyMemory();

    private CompanyMapper companyMapper = new CompanyMapper();
    private CompanyValidator companyValidator = new CompanyValidator();

    public CrudsResult createCompany(CompanyDTO companyDTO) {
        CrudsResult validateResult = validateCompany(companyDTO);
        if (validateResult.isError()) {
            return validateResult;
        }

        Company company = companyMapper.mapDTOToDAO(companyDTO);
        CrudsResult createResult = companyDAO.persist(company);
        if (createResult.isSuccess()) {
            companyMemory.createCompany(company);
        }
        return createResult;
    }

    public SearchResult<CompanyDTO> getCompany() {
        SearchResult<Company> searchResult = companyMemory.getCompany();
        if (searchResult.isSuccess()) {
            Company company = searchResult.getFirst();
            if (company != null) {
                CompanyDTO companyDTO = companyMapper.mapDAOToDTO(company);
                return new SearchResult<CompanyDTO>().success(Collections.singletonList(companyDTO));
            }
        }
        return new SearchResult<CompanyDTO>().error(searchResult.getMessages());
    }

    public CrudsResult updateCompany(CompanyDTO companyDTO) {
        CrudsResult validateResult = validateCompany(companyDTO);
        if (validateResult.isError()) {
            return validateResult;
        }

        Company company = companyMapper.mapDTOToDAO(companyDTO);
        CrudsResult updateResult = companyDAO.update(company);
        if (updateResult.isSuccess()) {
            companyMemory.updateCompany(company);
        }
        return updateResult;
    }

    public CrudsResult deleteCompany() {
        CrudsResult deleteResult = companyDAO.delete();
        if (deleteResult.isSuccess()) {
            companyMemory.deleteCompany();
        }
        return deleteResult;
    }

    private CrudsResult validateCompany(CompanyDTO companyDTO) {
        List<String> validationResult = companyValidator.validate(companyDTO);
        if (validationResult.size() > 0) {
            return new CrudsResult().error(validationResult);
        }
        return new CrudsResult().success(companyDTO.getName1() + " " + companyDTO.getName2());
    }
}
