package be.boomkwekerij.plant.validator;

import be.boomkwekerij.plant.model.dto.PlantDTO;

import java.util.ArrayList;
import java.util.List;

public class PlantValidator {

    private static final String REQUIRED_FIELD = "Volgend veld is verplicht: ";

    public List<String> validate(PlantDTO plant) {
        List<String> validationErrors = new ArrayList<>();

        validateRequiredFields(plant, validationErrors);

        return validationErrors;
    }

    private void validateRequiredFields(PlantDTO plant, List<String> validationErrors) {
        validateRequiredField("naam", plant.getName(), validationErrors);
        if (plant.getPrice() == 0.0) {
            validationErrors.add(REQUIRED_FIELD + "prijs");
        }
    }

    private void validateRequiredField(String field, String fieldValue, List<String> validationErrors) {
        if (fieldValue.trim().isEmpty()) {
            validationErrors.add(REQUIRED_FIELD + field);
        }
    }
}
