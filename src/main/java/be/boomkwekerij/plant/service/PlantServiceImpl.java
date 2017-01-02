package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.dao.memory.PlantMemory;
import be.boomkwekerij.plant.dao.repository.PlantDAO;
import be.boomkwekerij.plant.dao.repository.PlantDAOImpl;
import be.boomkwekerij.plant.mapper.PlantMapper;
import be.boomkwekerij.plant.model.dto.PlantDTO;
import be.boomkwekerij.plant.model.repository.Plant;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.MemoryDatabase;
import be.boomkwekerij.plant.util.SearchResult;
import be.boomkwekerij.plant.validator.PlantValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlantServiceImpl implements PlantService {

    private PlantDAO plantDAO = new PlantDAOImpl();
    private PlantMemory plantMemory = MemoryDatabase.getPlantMemory();

    private PlantMapper plantMapper = new PlantMapper();
    private PlantValidator plantValidator = new PlantValidator();

    public CrudsResult createPlant(PlantDTO plantDTO) {
        CrudsResult validateResult = validatePlant(plantDTO);
        if (validateResult.isError()) {
            return validateResult;
        }

        Plant plant = plantMapper.mapDTOToDAO(plantDTO);
        CrudsResult createResult = plantDAO.persist(plant);
        if (createResult.isSuccess()) {
            plantMemory.createPlant(plant);
        }
        return createResult;
    }

    public SearchResult<PlantDTO> getPlant(String id) {
        SearchResult<Plant> searchResult = plantMemory.getPlant(id);
        if (searchResult.isSuccess()) {
            Plant plant = searchResult.getFirst();
            if (plant != null) {
                PlantDTO plantDTO = plantMapper.mapDAOToDTO(plant);
                return new SearchResult<PlantDTO>().success(Collections.singletonList(plantDTO));
            }
        }
        return new SearchResult<PlantDTO>().error(searchResult.getMessages());
    }

    public SearchResult<PlantDTO> getAllPlants() {
        SearchResult<Plant> searchResult = plantMemory.getPlants();
        if (searchResult.isSuccess()) {
            List<PlantDTO> allPlants = new ArrayList<PlantDTO>();
            for (Plant plant : searchResult.getResults()) {
                PlantDTO plantDTO = plantMapper.mapDAOToDTO(plant);
                allPlants.add(plantDTO);
            }
            return new SearchResult<PlantDTO>().success(allPlants);
        }
        return new SearchResult<PlantDTO>().error(searchResult.getMessages());
    }

    public SearchResult<PlantDTO> getAllPlants(String name) {
        SearchResult<Plant> searchResult = plantMemory.getPlants(name);
        if (searchResult.isSuccess()) {
            List<PlantDTO> allPlantsWithName = new ArrayList<PlantDTO>();
            for (Plant plant : searchResult.getResults()) {
                PlantDTO plantDTO = plantMapper.mapDAOToDTO(plant);
                allPlantsWithName.add(plantDTO);
            }
            return new SearchResult<PlantDTO>().success(allPlantsWithName);
        }
        return new SearchResult<PlantDTO>().error(searchResult.getMessages());
    }

    public CrudsResult updatePlant(PlantDTO plantDTO) {
        CrudsResult validateResult = validatePlant(plantDTO);
        if (validateResult.isError()) {
            return validateResult;
        }

        Plant plant = plantMapper.mapDTOToDAO(plantDTO);
        CrudsResult updateResult = plantDAO.update(plant);
        if (updateResult.isSuccess()) {
            plantMemory.updatePlant(plant);
        }
        return updateResult;
    }

    public CrudsResult deletePlant(String id) {
        CrudsResult deleteResult = plantDAO.delete(id);
        if (deleteResult.isSuccess()) {
            plantMemory.deletePlant(id);
        }
        return deleteResult;
    }

    private CrudsResult validatePlant(PlantDTO plantDTO) {
        List<String> validationResult = plantValidator.validate(plantDTO);
        if (validationResult.size() > 0) {
            return new CrudsResult().error(validationResult);
        }
        return new CrudsResult().success(plantDTO.getId());
    }
}
