package be.boomkwekerij.plant.view.mapper;

import be.boomkwekerij.plant.model.dto.PlantDTO;
import be.boomkwekerij.plant.util.NumberUtils;
import be.boomkwekerij.plant.view.model.PlantViewModel;

public class PlantViewMapper {

    public PlantViewModel mapDTOToViewModel(PlantDTO plantDTO) {
        PlantViewModel plantViewModel = new PlantViewModel();
        plantViewModel.setId(plantDTO.getId());
        plantViewModel.setName(plantDTO.getName());
        plantViewModel.setAge(plantDTO.getAge());
        plantViewModel.setMeasure(plantDTO.getMeasure());
        plantViewModel.setPrice(NumberUtils.formatDouble(plantDTO.getPrice(), 2));
        return plantViewModel;
    }
}
