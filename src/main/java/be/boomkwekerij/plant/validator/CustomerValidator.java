package be.boomkwekerij.plant.validator;

import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.util.CountryCode;

import java.util.ArrayList;
import java.util.List;

public class CustomerValidator {

    private static final String REQUIRED_FIELD = "Volgend veld is verplicht: ";
    private static final String INVALID_CONTENT_FIELD = "Volgend veld heeft een foute inhoud: ";

    public List<String> validate(CustomerDTO customer) {
        List<String> validationErrors = new ArrayList<>();

        validateRequiredFields(customer, validationErrors);
        validateValidContentFields(customer, validationErrors);

        return validationErrors;
    }

    private void validateRequiredFields(CustomerDTO customer, List<String> validationErrors) {
        validateRequiredField("naam1", customer.getName1(), validationErrors);
        validateRequiredField("adres1", customer.getAddress1(), validationErrors);
        validateRequiredField("postcode", customer.getPostalCode(), validationErrors);
        validateRequiredField("woonplaats", customer.getResidence(), validationErrors);
        validateRequiredField("land", customer.getCountry(), validationErrors);
    }

    private void validateRequiredField(String field, String fieldValue, List<String> validationErrors) {
        if (fieldValue.trim().isEmpty()) {
            validationErrors.add(REQUIRED_FIELD + field);
        }
    }

    private void validateValidContentFields(CustomerDTO customer, List<String> validationErrors) {
        if (CountryCode.fromCountryCode(customer.getCountry()) == null) {
            validationErrors.add(INVALID_CONTENT_FIELD + "land, onbekend");
        }
    }
}
