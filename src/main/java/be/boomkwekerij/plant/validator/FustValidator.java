package be.boomkwekerij.plant.validator;

import be.boomkwekerij.plant.model.dto.FustDTO;

import java.util.ArrayList;
import java.util.List;

public class FustValidator {

    private static final String POSITIVE_FIELD = "Volgend veld moet groter zijn dan 0: ";

    public List<String> validate(FustDTO fust) {
        List<String> validationErrors = new ArrayList<>();

        validateNumericFields(fust, validationErrors);

        return validationErrors;
    }

    private void validateNumericFields(FustDTO fust, List<String> validationErrors) {
        validateIfPositive(fust.getLageKisten(), "Lage kisten", validationErrors);
        validateIfPositive(fust.getHogeKisten(), "Hoge kisten", validationErrors);
        validateIfPositive(fust.getPalletBodem(), "Pallet-Bodem", validationErrors);
        validateIfPositive(fust.getBoxPallet(), "Box-Pallet", validationErrors);
        validateIfPositive(fust.getHalveBox(), "Halve box", validationErrors);
        validateIfPositive(fust.getFerroPalletKlein(), "Ferro-Pallet (klein)", validationErrors);
        validateIfPositive(fust.getFerroPalletGroot(), "Ferro-Pallet (groot)", validationErrors);
        validateIfPositive(fust.getKarrenEnBorden(), "C.C. Karren/Borden", validationErrors);
        validateIfPositive(fust.getDiverse(), "Diverse", validationErrors);
    }

    private void validateIfPositive(int field, String fieldName, List<String> validationErrors) {
        if(field < 0) {
            validationErrors.add(POSITIVE_FIELD + fieldName);
        }
    }
}
