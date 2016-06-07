package be.boomkwekerij.plant.dao.memory;

import be.boomkwekerij.plant.model.repository.Plant;
import be.boomkwekerij.plant.util.SearchResult;

import java.util.List;

public interface PlantMemory {

    void createPlant(Plant plant);

    void createPlants(List<Plant> plants);

    SearchResult<Plant> getPlant(String id);

    SearchResult<Plant> getPlants();

    SearchResult<Plant> getPlants(String name);

    void updatePlant(Plant plant);

    void deletePlant(String id);
}
