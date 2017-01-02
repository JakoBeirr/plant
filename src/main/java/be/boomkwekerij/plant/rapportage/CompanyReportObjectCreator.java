package be.boomkwekerij.plant.rapportage;

import be.boomkwekerij.plant.model.dto.CompanyDTO;
import be.boomkwekerij.plant.model.report.CompanyReportObject;

public class CompanyReportObjectCreator {

    public CompanyReportObject create(CompanyDTO companyDTO) {
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
