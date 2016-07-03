package be.boomkwekerij.plant.controller;

import be.boomkwekerij.plant.model.dto.SystemDTO;
import be.boomkwekerij.plant.service.SystemService;
import be.boomkwekerij.plant.service.SystemServiceImpl;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.ExceptionUtil;
import be.boomkwekerij.plant.util.SearchResult;

public class SystemController {

    private SystemService systemService = new SystemServiceImpl();

    public CrudsResult createSystem(SystemDTO systemDTO) {
        try {
            return systemService.createSystem(systemDTO);
        } catch (Exception e) {
            return createCrudsError(e);
        }
    }

    public SearchResult<SystemDTO> getSystem() {
        try {
            return systemService.getSystem();
        } catch (Exception e) {
            return createSearchError(e);
        }
    }

    public CrudsResult updateSystem(SystemDTO systemDTO) {
        try {
            return systemService.updateSystem(systemDTO);
        } catch (Exception e) {
            return createCrudsError(e);
        }
    }

    private SearchResult<SystemDTO> createSearchError(Exception e) {
        SearchResult<SystemDTO> failure = new SearchResult<SystemDTO>();
        failure.setSuccess(false);
        failure.getMessages().add(ExceptionUtil.getStackTrace(e));
        return failure;
    }

    private CrudsResult createCrudsError(Exception e) {
        CrudsResult failure = new CrudsResult();
        failure.setSuccess(false);
        failure.getMessages().add(ExceptionUtil.getStackTrace(e));
        return failure;
    }
}
