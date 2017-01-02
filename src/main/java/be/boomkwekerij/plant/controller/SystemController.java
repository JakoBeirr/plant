package be.boomkwekerij.plant.controller;

import be.boomkwekerij.plant.model.dto.SystemDTO;
import be.boomkwekerij.plant.service.SystemService;
import be.boomkwekerij.plant.service.SystemServiceImpl;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;

import java.util.Collections;

public class SystemController {

    private SystemService systemService = new SystemServiceImpl();

    public CrudsResult createSystem(SystemDTO systemDTO) {
        try {
            return systemService.createSystem(systemDTO);
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    public SearchResult<SystemDTO> getSystem() {
        try {
            return systemService.getSystem();
        } catch (Exception e) {
            return new SearchResult<SystemDTO>().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult updateSystem(SystemDTO systemDTO) {
        try {
            return systemService.updateSystem(systemDTO);
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }
}
