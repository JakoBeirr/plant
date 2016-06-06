package be.boomkwekerij.plant.mapper;

import be.boomkwekerij.plant.model.dto.CompanyDTO;
import be.boomkwekerij.plant.model.repository.Company;

public class CompanyMapper {

    public Company mapDTOToDAO(CompanyDTO companyDTO) {
        Company company = new Company();
        company.setId(companyDTO.getId());
        company.setName(companyDTO.getName());
        company.setTelephone(companyDTO.getTelephone());
        company.setFax(companyDTO.getFax());
        company.setGsm(companyDTO.getGsm());
        company.setAccountNumberBelgium(companyDTO.getAccountNumberBelgium());
        company.setIbanBelgium(companyDTO.getIbanBelgium());
        company.setBicBelgium(companyDTO.getBicBelgium());
        company.setAccountNumberNetherlands(companyDTO.getAccountNumberNetherlands());
        company.setIbanNetherlands(companyDTO.getIbanNetherlands());
        company.setBicNetherlands(companyDTO.getBicNetherlands());
        company.setBtwNumber(companyDTO.getBtwNumber());
        return company;
    }

    public CompanyDTO mapDAOToDTO(Company company) {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(company.getId());
        companyDTO.setName(company.getName());
        companyDTO.setTelephone(company.getTelephone());
        companyDTO.setFax(company.getFax());
        companyDTO.setGsm(company.getGsm());
        companyDTO.setAccountNumberBelgium(company.getAccountNumberBelgium());
        companyDTO.setIbanBelgium(company.getIbanBelgium());
        companyDTO.setBicBelgium(company.getBicBelgium());
        companyDTO.setAccountNumberNetherlands(company.getAccountNumberNetherlands());
        companyDTO.setIbanNetherlands(company.getIbanNetherlands());
        companyDTO.setBicNetherlands(company.getBicNetherlands());
        companyDTO.setBtwNumber(company.getBtwNumber());
        return companyDTO;
    }
}
