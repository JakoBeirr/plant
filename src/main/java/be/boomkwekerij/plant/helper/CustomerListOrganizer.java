package be.boomkwekerij.plant.helper;

import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.util.StringUtils;

import java.util.*;

public class CustomerListOrganizer {

    public static List<CustomerDTO> organize(List<CustomerDTO> customers, String name) {
        Map<Integer, List<CustomerDTO>> customersWithNameSimilarity = new TreeMap<>((Comparator<Integer>) (d1, d2) -> d2 - d1);

        for (CustomerDTO customer : customers) {
            String customerName = customer.getName1() + " " + customer.getName2();
            int nameSimilarityPercentage = StringUtils.countStringSimilarity(customerName.trim(), name);

            if (!customersWithNameSimilarity.containsKey(nameSimilarityPercentage)) {
                customersWithNameSimilarity.put(nameSimilarityPercentage, new ArrayList<>());
                customersWithNameSimilarity.get(nameSimilarityPercentage).add(customer);
            } else {
                customersWithNameSimilarity.get(nameSimilarityPercentage).add(customer);
            }
        }

        List<CustomerDTO> result = new ArrayList<>();
        for (List<CustomerDTO> customersWithSameNameSimilarity : customersWithNameSimilarity.values()) {
            result.addAll(customersWithSameNameSimilarity);
        }
        return result;
    }
}
