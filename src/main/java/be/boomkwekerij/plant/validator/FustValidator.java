package be.boomkwekerij.plant.validator;

import be.boomkwekerij.plant.model.dto.FustDTO;
import be.boomkwekerij.plant.model.dto.FustOverviewDTO;

import java.util.ArrayList;
import java.util.List;

public class FustValidator {

    private static final String POSITIVE_FIELD = "Volgend veld is groter dan huidige Fust: ";

    public List<String> validate(FustDTO newFust, FustOverviewDTO currentFust) {
        List<String> validationErrors = new ArrayList<>();

        validateNumericFields(newFust, currentFust, validationErrors);

        return validationErrors;
    }

    private void validateNumericFields(FustDTO newFust, FustOverviewDTO currentFust, List<String> validationErrors) {
        validateIfTotalPositive(newFust.getLageKisten(), currentFust.getLageKisten(), "Lage kisten", validationErrors);
        validateIfTotalPositive(newFust.getHogeKisten(), currentFust.getHogeKisten(), "Hoge kisten", validationErrors);
        validateIfTotalPositive(newFust.getPalletBodem(), currentFust.getPalletBodem(), "Pallet-Bodem", validationErrors);
        validateIfTotalPositive(newFust.getBoxPallet(), currentFust.getBoxPallet(), "Box-Pallet", validationErrors);
        validateIfTotalPositive(newFust.getHalveBox(), currentFust.getHalveBox(), "Halve box", validationErrors);
        validateIfTotalPositive(newFust.getFerroPalletKlein(), currentFust.getFerroPalletKlein(), "Ferro-Pallet (klein)", validationErrors);
        validateIfTotalPositive(newFust.getFerroPalletGroot(), currentFust.getFerroPalletGroot(), "Ferro-Pallet (groot)", validationErrors);
        validateIfTotalPositive(newFust.getKarrenEnBorden(), currentFust.getKarrenEnBorden(), "C.C. Karren/Borden", validationErrors);
        validateIfTotalPositive(newFust.getDiverse(), currentFust.getDiverse(), "Diverse", validationErrors);
    }

    private void validateIfTotalPositive(int extraValue, int oldValue, String fieldName, List<String> validationErrors) {
        if((oldValue+extraValue) < 0) {
            validationErrors.add(POSITIVE_FIELD + fieldName);
        }
    }
}
