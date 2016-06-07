package be.boomkwekerij.plant.mapper;

import be.boomkwekerij.plant.model.dto.SystemDTO;
import be.boomkwekerij.plant.model.repository.System;

public class SystemMapper {

    public System mapDTOToDAO(SystemDTO systemDTO) {
        System system = new System();
        system.setLastInvoiceNumber(systemDTO.getLastInvoiceNumber());
        return system;
    }

    public SystemDTO mapDAOToDTO(System system) {
        SystemDTO systemDTO = new SystemDTO();
        systemDTO.setLastInvoiceNumber(system.getLastInvoiceNumber());
        return systemDTO;
    }
}
