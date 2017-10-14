package be.boomkwekerij.plant.helper;

import be.boomkwekerij.plant.model.dto.InvoiceDTO;
import be.boomkwekerij.plant.util.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class InvoiceListOrganizer {

    public static List<InvoiceDTO> organize(List<InvoiceDTO> invoices, String invoiceNumber) {
        Map<Integer, List<InvoiceDTO>> invoicesWithNameSimilarity = new TreeMap<>((Comparator<Integer>) (d1, d2) -> d2 - d1);

        for (InvoiceDTO invoice : invoices) {
            int nameSimilarityPercentage = StringUtils.countStringSimilarity(invoice.getInvoiceNumber().trim(), invoiceNumber);

            if (!invoicesWithNameSimilarity.containsKey(nameSimilarityPercentage)) {
                invoicesWithNameSimilarity.put(nameSimilarityPercentage, new ArrayList<>());
                invoicesWithNameSimilarity.get(nameSimilarityPercentage).add(invoice);
            } else {
                invoicesWithNameSimilarity.get(nameSimilarityPercentage).add(invoice);
            }
        }

        List<InvoiceDTO> result = new ArrayList<>();
        for (List<InvoiceDTO> invoicesWithSameNameSimilarity : invoicesWithNameSimilarity.values()) {
            result.addAll(invoicesWithSameNameSimilarity);
        }
        return result;
    }
}
