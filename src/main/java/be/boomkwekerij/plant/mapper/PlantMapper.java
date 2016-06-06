package be.boomkwekerij.plant.mapper;

import be.boomkwekerij.plant.model.dto.PlantDTO;
import be.boomkwekerij.plant.model.repository.Plant;

public class PlantMapper {

    public Plant mapDTOToDAO(PlantDTO plantDTO) {
        Plant plant = new Plant();
        plant.setId(plantDTO.getId());
        plant.setName(plantDTO.getName());
        plant.setAge(plantDTO.getAge());
        plant.setMeasure(plantDTO.getMeasure());
        plant.setPrice(plantDTO.getPrice());
        return plant;
    }

    public PlantDTO mapDAOToDTO(Plant plant) {
        PlantDTO plantDTO = new PlantDTO();
        plantDTO.setId(plant.getId());
        plantDTO.setName(plant.getName());
        plantDTO.setAge(plant.getAge());
        plantDTO.setMeasure(plant.getMeasure());
        plantDTO.setPrice(plant.getPrice());
        return plantDTO;
    }
}
