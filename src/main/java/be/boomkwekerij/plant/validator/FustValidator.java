package be.boomkwekerij.plant.validator;

import be.boomkwekerij.plant.model.dto.FustDTO;

import java.util.ArrayList;
import java.util.List;

public class FustValidator {

    private static final String REQUIRED_FIELD = "Volgend veld is verplicht: ";

    public List<String> validate(FustDTO fust) {
        List<String> validationErrors = new ArrayList<>();

        validateRequiredFields(fust, validationErrors);

        return validationErrors;
    }

    private void validateRequiredFields(FustDTO fust, List<String> validationErrors) {

    }
}
