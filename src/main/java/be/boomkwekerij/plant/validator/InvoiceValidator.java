package be.boomkwekerij.plant.validator;

import be.boomkwekerij.plant.model.dto.InvoiceDTO;
import be.boomkwekerij.plant.model.dto.InvoiceLineDTO;

import java.util.ArrayList;
import java.util.List;

public class InvoiceValidator {

    private static final String REQUIRED_FIELD = "Volgend veld is verplicht: ";

    public List<String> validate(InvoiceDTO invoice) {
        List<String> validationErrors = new ArrayList<>();

        validateRequiredFields(invoice, validationErrors);
        validatePercentField(invoice, validationErrors);

        return validationErrors;
    }

    private void validateRequiredFields(InvoiceDTO invoice, List<String> validationErrors) {
        if (invoice.getInvoiceLines().size() == 0) {
            validationErrors.add(REQUIRED_FIELD + "factuurlijnen");
        }
        if (invoice.getDate() == null) {
            validationErrors.add(REQUIRED_FIELD + "datum");
        }
    }

    private void validatePercentField(InvoiceDTO invoice, List<String> validationErrors) {
        for (InvoiceLineDTO invoiceLineDTO : invoice.getInvoiceLines()) {
            if (invoiceLineDTO.getBtw() < 0 || invoiceLineDTO.getBtw() > 100) {
                validationErrors.add("BTW " + invoiceLineDTO.getBtw() + "% voor factuurlijn: " + invoiceLineDTO.getPlantName() + " (" + invoiceLineDTO.getPlantAge() + " - " + invoiceLineDTO.getPlantMeasure() + ") is incorrect, dit moet een getal tussen 0 en 100 zijn");
            }
        }
    }
}
