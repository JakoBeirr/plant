package be.boomkwekerij.plant.mapper;

import be.boomkwekerij.plant.model.dto.CompanyDTO;
import be.boomkwekerij.plant.model.repository.Company;

public class CompanyMapper {

    public Company mapDTOToDAO(CompanyDTO companyDTO) {
        Company company = new Company();
        company.setName1(companyDTO.getName1());
        company.setName2(companyDTO.getName2());
        company.setAddress(companyDTO.getAddress());
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
        company.setEmailAddress(companyDTO.getEmailAddress());
        return company;
    }

    public CompanyDTO mapDAOToDTO(Company company) {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setName1(company.getName1());
        companyDTO.setName2(company.getName2());
        companyDTO.setAddress(company.getAddress());
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
        companyDTO.setEmailAddress(company.getEmailAddress());
        return companyDTO;
    }
}
