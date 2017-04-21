package be.boomkwekerij.plant.helper;

import be.boomkwekerij.plant.model.dto.PlantDTO;
import be.boomkwekerij.plant.util.StringUtils;

import java.util.*;

public class PlantListOrganizer {

    public static List<PlantDTO> organize(List<PlantDTO> plants, String name) {
        Map<Integer, List<PlantDTO>> plantsWithNameSimilarity = new TreeMap<>((Comparator<Integer>) (d1, d2) -> d2 - d1);

        for (PlantDTO plant : plants) {
            int nameSimilarityPercentage = StringUtils.countStringSimilarity(plant.getName().trim(), name);

            if (!plantsWithNameSimilarity.containsKey(nameSimilarityPercentage)) {
                plantsWithNameSimilarity.put(nameSimilarityPercentage, new ArrayList<>());
                plantsWithNameSimilarity.get(nameSimilarityPercentage).add(plant);
            } else {
                plantsWithNameSimilarity.get(nameSimilarityPercentage).add(plant);
            }
        }

        List<PlantDTO> result = new ArrayList<>();
        for (List<PlantDTO> plantsWithSameNameSimilarity : plantsWithNameSimilarity.values()) {
            result.addAll(plantsWithSameNameSimilarity);
        }
        return result;
    }

    public static List<PlantDTO> organize(List<PlantDTO> plants, String name, String age, String measure) {
        Map<Integer, List<PlantDTO>> plantsWithNameSimilarity = new TreeMap<>((Comparator<Integer>) (d1, d2) -> d2 - d1);

        for (PlantDTO plant : plants) {
            int nameSimilarityPercentage = StringUtils.countStringSimilarity(plant.getName().trim(), name);
            int ageSimilarityPercentage = StringUtils.countStringSimilarity(plant.getName().trim(), age);
            int measureSimilarityPercentage = StringUtils.countStringSimilarity(plant.getName().trim(), measure);
            int similarityPercentage = nameSimilarityPercentage + ageSimilarityPercentage + measureSimilarityPercentage;

            if (!plantsWithNameSimilarity.containsKey(similarityPercentage)) {
                plantsWithNameSimilarity.put(similarityPercentage, new ArrayList<>());
                plantsWithNameSimilarity.get(similarityPercentage).add(plant);
            } else {
                plantsWithNameSimilarity.get(similarityPercentage).add(plant);
            }
        }

        List<PlantDTO> result = new ArrayList<>();
        for (List<PlantDTO> plantsWithSameNameSimilarity : plantsWithNameSimilarity.values()) {
            result.addAll(plantsWithSameNameSimilarity);
        }
        return result;
    }
}
