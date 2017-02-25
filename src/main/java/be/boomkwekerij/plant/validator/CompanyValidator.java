package be.boomkwekerij.plant.validator;

import be.boomkwekerij.plant.model.dto.CompanyDTO;

import java.util.ArrayList;
import java.util.List;

public class CompanyValidator {

    private static final String REQUIRED_FIELD = "Volgend veld is verplicht: ";

    public List<String> validate(CompanyDTO company) {
        List<String> validationErrors = new ArrayList<>();

        validateRequiredFields(company, validationErrors);

        return validationErrors;
    }

    private void validateRequiredFields(CompanyDTO company, List<String> validationErrors) {
        validateRequiredField("naam1", company.getName1(), validationErrors);
        validateRequiredField("naam2", company.getName2(), validationErrors);
        validateRequiredField("adres", company.getAddress(), validationErrors);
        validateRequiredField("telefoon", company.getTelephone(), validationErrors);
        validateRequiredField("fax", company.getFax(), validationErrors);
        validateRequiredField("gsm", company.getGsm(), validationErrors);
        validateRequiredField("IBAN BE", company.getIbanBelgium(), validationErrors);
        validateRequiredField("BIC BE", company.getBicBelgium(), validationErrors);
        validateRequiredField("IBAN NL", company.getIbanNetherlands(), validationErrors);
        validateRequiredField("BIC NL", company.getBicNetherlands(), validationErrors);
        validateRequiredField("BTW nummer", company.getBtwNumber(), validationErrors);
    }

    private void validateRequiredField(String field, String fieldValue, List<String> validationErrors) {
        if (fieldValue.trim().isEmpty()) {
            validationErrors.add(REQUIRED_FIELD + field);
        }
    }
}
