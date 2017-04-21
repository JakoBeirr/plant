package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.model.dto.PlantDTO;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;

public interface PlantService {

    CrudsResult createPlant(PlantDTO plant);

    SearchResult<PlantDTO> getPlant(String id);

    SearchResult<PlantDTO> getAllPlants();

    SearchResult<PlantDTO> getAllPlants(String name);

    SearchResult<PlantDTO> getAllPlants(String name, String age, String measure);

    CrudsResult updatePlant(PlantDTO plant);

    CrudsResult deletePlant(String id);
}
