package be.boomkwekerij.plant.validator;

import be.boomkwekerij.plant.model.dto.SystemDTO;

import java.util.ArrayList;
import java.util.List;

public class SystemValidator {

    private static final String REQUIRED_FIELD = "Volgend veld is verplicht: ";

    public List<String> validate(SystemDTO system) {
        List<String> validationErrors = new ArrayList<>();

        validateRequiredFields(system, validationErrors);

        return validationErrors;
    }

    private void validateRequiredFields(SystemDTO system, List<String> validationErrors) {
        validateRequiredField("factuurnummer", system.getNextInvoiceNumber(), validationErrors);
    }

    private void validateRequiredField(String field, String fieldValue, List<String> validationErrors) {
        if (fieldValue.trim().isEmpty()) {
            validationErrors.add(REQUIRED_FIELD + field);
        }
    }
}
