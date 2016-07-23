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

import java.util.List;

public class CompanyServiceImpl implements CompanyService {

    private CompanyDAO companyDAO = new CompanyDAOImpl();
    private CompanyMemory companyMemory = MemoryDatabase.getCompanyMemory();

    private CompanyMapper companyMapper = new CompanyMapper();
    private CompanyValidator companyValidator = new CompanyValidator();

    public CrudsResult createCompany(CompanyDTO companyDTO) {
        CrudsResult crudsResult = validateCompany(companyDTO);
        if (crudsResult != null) {
            return crudsResult;
        }

        Company company = companyMapper.mapDTOToDAO(companyDTO);
        crudsResult = companyDAO.persist(company);

        if (crudsResult.isSuccess()) {
            companyMemory.createCompany(company);
        }

        return crudsResult;
    }

    public SearchResult<CompanyDTO> getCompany() {
        SearchResult<Company> searchResult = companyMemory.getCompany();

        SearchResult<CompanyDTO> customerSearchResult = new SearchResult<CompanyDTO>();
        customerSearchResult.setSuccess(searchResult.isSuccess());
        customerSearchResult.setMessages(searchResult.getMessages());

        if (searchResult.isSuccess()) {
            Company company = searchResult.getFirst();
            CompanyDTO companyDTO = companyMapper.mapDAOToDTO(company);
            customerSearchResult.addResult(companyDTO);
        }

        return customerSearchResult;
    }

    public CrudsResult updateCompany(CompanyDTO companyDTO) {
        CrudsResult crudsResult = validateCompany(companyDTO);
        if (crudsResult != null) {
            return crudsResult;
        }

        Company company = companyMapper.mapDTOToDAO(companyDTO);
        crudsResult = companyDAO.update(company);

        if (crudsResult.isSuccess()) {
            companyMemory.updateCompany(company);
        }

        return crudsResult;
    }

    public CrudsResult deleteCompany() {
        CrudsResult crudsResult = companyDAO.delete();

        if (crudsResult.isSuccess()) {
            companyMemory.deleteCompany();
        }

        return crudsResult;
    }

    private CrudsResult validateCompany(CompanyDTO companyDTO) {
        List<String> validationResult = companyValidator.validate(companyDTO);
        if (validationResult.size() > 0) {
            CrudsResult crudsResult = new CrudsResult();
            crudsResult.setSuccess(false);
            crudsResult.setMessages(validationResult);
            return crudsResult;
        }
        return null;
    }
}
