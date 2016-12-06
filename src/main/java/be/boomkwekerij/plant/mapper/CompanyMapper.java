package be.boomkwekerij.plant.mapper;

import be.boomkwekerij.plant.model.dto.CompanyDTO;
import be.boomkwekerij.plant.model.report.CompanyReportObject;
import be.boomkwekerij.plant.model.repository.Company;

public class CompanyMapper {

    public Company mapDTOToDAO(CompanyDTO companyDTO) {
        Company company = new Company();
        company.setName(companyDTO.getName());
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
        return company;
    }

    public CompanyDTO mapDAOToDTO(Company company) {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setName(company.getName());
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
        return companyDTO;
    }

    public CompanyReportObject mapDTOToReportObject(CompanyDTO companyDTO) {
        CompanyReportObject companyReportObject = new CompanyReportObject();
        companyReportObject.setAddress(getReportObjectValue(companyDTO.getAddress()));
        companyReportObject.setTelephoneBelgium(getReportObjectValue(companyDTO.getTelephone()));
        companyReportObject.setTelephoneNetherlands(getDutchVariant(getReportObjectValue(companyDTO.getTelephone())));
        companyReportObject.setFaxBelgium(getReportObjectValue(companyDTO.getFax()));
        companyReportObject.setFaxNetherlands(getDutchVariant(getReportObjectValue(companyDTO.getFax())));
        companyReportObject.setGsmBelgium(getReportObjectValue(companyDTO.getGsm()));
        companyReportObject.setGsmNetherlands(getDutchVariant(getReportObjectValue(companyDTO.getGsm())));
        companyReportObject.setIbanBelgium(getReportObjectValue(companyDTO.getIbanBelgium()));
        companyReportObject.setBicBelgium(getReportObjectValue(companyDTO.getBicBelgium()));
        companyReportObject.setIbanNetherlands(getReportObjectValue(companyDTO.getIbanNetherlands()));
        companyReportObject.setBicNetherlands(getReportObjectValue(companyDTO.getBicNetherlands()));
        companyReportObject.setBtwNumber(getReportObjectValue(companyDTO.getBtwNumber()));
        return companyReportObject;
    }

    private String getDutchVariant(String number) {
        return number.replaceFirst("0", "0032");
    }

    private String getReportObjectValue(String value) {
        if (value == null) {
            return "";
        }
        return value;
    }
}
