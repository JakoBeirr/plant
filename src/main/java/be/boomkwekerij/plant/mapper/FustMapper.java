package be.boomkwekerij.plant.mapper;

import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.model.dto.FustDTO;
import be.boomkwekerij.plant.model.repository.Fust;

public class FustMapper {

    public Fust mapDTOToDAO(FustDTO fustDTO) {
        Fust fust = new Fust();
        fust.setId(fustDTO.getId());
        fust.setCustomerId(fustDTO.getCustomer().getId());
        return fust;
    }

    public FustDTO mapDAOToDTO(Fust fust, CustomerDTO customer) {
        FustDTO fustDTO = new FustDTO();
        fustDTO.setId(fust.getId());
        fustDTO.setCustomer(customer);
        return fustDTO;
    }
}
