package be.boomkwekerij.plant.dao.memory;

import be.boomkwekerij.plant.model.repository.Plant;
import be.boomkwekerij.plant.util.SearchResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        SearchResult<Plant> searchResult = new SearchResult<Plant>();
        searchResult.setSuccess(true);
        searchResult.addResult(plants.get(id));
        return searchResult;
    }

    public SearchResult<Plant> getPlants() {
        SearchResult<Plant> searchResult = new SearchResult<Plant>();
        searchResult.setSuccess(true);
        searchResult.setResults(new ArrayList<Plant>(plants.values()));
        return searchResult;
    }

    public SearchResult<Plant> getPlants(String name) {
        SearchResult<Plant> plantsWithName = new SearchResult<Plant>();

        for (Plant plant : plants.values()) {
            if (plantNameStartsWith(plant, name)) {
                plantsWithName.addResult(plant);
            }
        }

        plantsWithName.setSuccess(true);
        return plantsWithName;
    }

    private boolean plantNameStartsWith(Plant plant, String name) {
        return plant.getName() != null && plant.getName().toUpperCase().contains(name.toUpperCase());
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
