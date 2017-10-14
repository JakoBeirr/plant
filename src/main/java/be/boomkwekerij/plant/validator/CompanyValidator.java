package be.boomkwekerij.plant.validator;

import be.boomkwekerij.plant.model.dto.CompanyDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CompanyValidator {

    private static final String REQUIRED_FIELD = "Volgend veld is verplicht: ";
    private static final String WRONG_SYNTAX_FIELD = "Volgend veld voldoet niet aan de richtlijnen: ";
    private static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

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
        validateRequiredField("E-mail", company.getEmailAddress(), validationErrors);
        if (!EMAIL_ADDRESS_PATTERN.matcher(company.getEmailAddress()).matches()) {
            validationErrors.add(WRONG_SYNTAX_FIELD + "E-mail");
        }
    }

    private void validateRequiredField(String field, String fieldValue, List<String> validationErrors) {
        if (fieldValue.trim().isEmpty()) {
            validationErrors.add(REQUIRED_FIELD + field);
        }
    }
}
