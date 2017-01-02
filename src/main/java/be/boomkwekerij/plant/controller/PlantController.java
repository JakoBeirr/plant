package be.boomkwekerij.plant.controller;

import be.boomkwekerij.plant.model.dto.PlantDTO;
import be.boomkwekerij.plant.service.PlantService;
import be.boomkwekerij.plant.service.PlantServiceImpl;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.ExceptionUtil;
import be.boomkwekerij.plant.util.SearchResult;

import java.util.Collections;

public class PlantController {

    private PlantService plantService = new PlantServiceImpl();

    public CrudsResult createPlant(PlantDTO plantDTO) {
        try {
            return plantService.createPlant(plantDTO);
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    public SearchResult<PlantDTO> getPlant(String id) {
        try {
            return plantService.getPlant(id);
        } catch (Exception e) {
            return new SearchResult<PlantDTO>().error(Collections.singletonList(e.getMessage()));
        }
    }

    public SearchResult<PlantDTO> getAllPlants() {
        try {
            return plantService.getAllPlants();
        } catch (Exception e) {
            return new SearchResult<PlantDTO>().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult updatePlant(PlantDTO plantDTO) {
        try {
            return plantService.updatePlant(plantDTO);
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult deletePlant(String id) {
        try {
            return plantService.deletePlant(id);
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    public SearchResult<PlantDTO> getAllPlantsWithName(String name) {
        try {
            return plantService.getAllPlants(name);
        } catch (Exception e) {
            return new SearchResult<PlantDTO>().error(Collections.singletonList(e.getMessage()));
        }
    }
}
