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

import java.util.ArrayList;
import java.util.List;

public class PlantServiceImpl implements PlantService {

    private PlantDAO plantDAO = new PlantDAOImpl();
    private PlantMemory plantMemory = MemoryDatabase.getPlantMemory();

    private PlantMapper plantMapper = new PlantMapper();

    public CrudsResult createPlant(PlantDTO plantDTO) {
        Plant plant = plantMapper.mapDTOToDAO(plantDTO);
        CrudsResult crudsResult = plantDAO.persist(plant);

        if (crudsResult.isSuccess()) {
            plantMemory.createPlant(plant);
        }

        return crudsResult;
    }

    public SearchResult<PlantDTO> getPlant(String id) {
        SearchResult<Plant> searchResult = plantMemory.getPlant(id);

        SearchResult<PlantDTO> plantSearchResult = new SearchResult<PlantDTO>();
        plantSearchResult.setSuccess(searchResult.isSuccess());
        plantSearchResult.setMessages(searchResult.getMessages());

        if (searchResult.isSuccess()) {
            Plant plant = searchResult.getFirst();
            PlantDTO plantDTO = plantMapper.mapDAOToDTO(plant);
            plantSearchResult.addResult(plantDTO);
        }

        return plantSearchResult;
    }

    public SearchResult<PlantDTO> getAllPlants() {
        SearchResult<Plant> searchResult = plantMemory.getPlants();

        List<PlantDTO> allPlants = new ArrayList<PlantDTO>();
        if (searchResult.isSuccess()) {
            for (Plant plant : searchResult.getResults()) {
                PlantDTO plantDTO = plantMapper.mapDAOToDTO(plant);
                allPlants.add(plantDTO);
            }
        }

        SearchResult<PlantDTO> allPlantsSearchResult = new SearchResult<PlantDTO>();
        allPlantsSearchResult.setSuccess(searchResult.isSuccess());
        allPlantsSearchResult.setMessages(searchResult.getMessages());
        allPlantsSearchResult.setResults(allPlants);
        return allPlantsSearchResult;
    }

    public SearchResult<PlantDTO> getAllPlants(String name) {
        SearchResult<Plant> searchResult = plantMemory.getPlants(name);

        List<PlantDTO> allPlantsWithName = new ArrayList<PlantDTO>();
        if (searchResult.isSuccess()) {
            for (Plant plant : searchResult.getResults()) {
                PlantDTO plantDTO = plantMapper.mapDAOToDTO(plant);
                allPlantsWithName.add(plantDTO);
            }
        }

        SearchResult<PlantDTO> allPlantsWithNameSearchResult = new SearchResult<PlantDTO>();
        allPlantsWithNameSearchResult.setSuccess(searchResult.isSuccess());
        allPlantsWithNameSearchResult.setMessages(searchResult.getMessages());
        allPlantsWithNameSearchResult.setResults(allPlantsWithName);
        return allPlantsWithNameSearchResult;
    }

    public CrudsResult updatePlant(PlantDTO plantDTO) {
        Plant plant = plantMapper.mapDTOToDAO(plantDTO);
        CrudsResult crudsResult = plantDAO.update(plant);

        if (crudsResult.isSuccess()) {
            plantMemory.updatePlant(plant);
        }

        return crudsResult;
    }

    public CrudsResult deletePlant(String id) {
        CrudsResult crudsResult = plantDAO.delete(id);

        if (crudsResult.isSuccess()) {
            plantMemory.deletePlant(id);
        }

        return crudsResult;
    }
}
