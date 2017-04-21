package be.boomkwekerij.plant.dao.memory;

import be.boomkwekerij.plant.model.repository.Plant;
import be.boomkwekerij.plant.util.SearchResult;

import java.util.*;

public class PlantMemoryImpl implements PlantMemory {

    private HashMap<String, Plant> plants = new HashMap<String, Plant>();

    public void createPlant(Plant plant) {
        plants.put(plant.getId(), plant);
    }

    public void createPlants(List<Plant> plantList) {
        for (Plant plant : plantList) {
            plants.put(plant.getId(), plant);
        }
    }

    public SearchResult<Plant> getPlant(String id) {
        if (id == null) {
            return new SearchResult<Plant>().error(Collections.singletonList("Kon geen plant vinden voor id null!"));
        } else {
            Plant plant = plants.get(id);
            if (plant != null) {
                return new SearchResult<Plant>().success(Collections.singletonList(plant));
            }
            return new SearchResult<Plant>().error(Collections.singletonList("Onbekende plant"));
        }
    }

    public SearchResult<Plant> getPlants() {
        return new SearchResult<Plant>().success(new ArrayList<Plant>(plants.values()));
    }

    public SearchResult<Plant> getPlants(String name) {
        if (name == null) {
            return new SearchResult<Plant>().error(Collections.singletonList("Kon geen plant vinden voor name null!"));
        } else {
            List<Plant> plantsWithName = new ArrayList<>();
            for (Plant plant : plants.values()) {
                if (plantNameContains(plant, name)) {
                    plantsWithName.add(plant);
                }
            }
            return new SearchResult<Plant>().success(plantsWithName);
        }
    }

    private boolean plantNameContains(Plant plant, String name) {
        String plantName = plant.getName() != null ? plant.getName() : "";

        return plantName.trim().toUpperCase().contains(name.toUpperCase());
    }

    public void updatePlant(Plant plant) {
        if (plants.get(plant.getId()) != null) {
            plants.put(plant.getId(), plant);
        }
    }

    public void deletePlant(String id) {
        plants.remove(id);
    }

    @Override
    public void deleteAllPlants() {
        plants = new HashMap<>();
    }
}
