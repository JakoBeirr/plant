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

public class CompanyServiceImpl implements CompanyService {

    private CompanyDAO companyDAO = new CompanyDAOImpl();
    private CompanyMemory companyMemory = MemoryDatabase.getCompanyMemory();

    private CompanyMapper companyMapper = new CompanyMapper();

    public CrudsResult createCompany(CompanyDTO companyDTO) {
        Company company = companyMapper.mapDTOToDAO(companyDTO);
        CrudsResult crudsResult = companyDAO.persist(company);

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
        Company company = companyMapper.mapDTOToDAO(companyDTO);
        CrudsResult crudsResult = companyDAO.update(company);

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
}
