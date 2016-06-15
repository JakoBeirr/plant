package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.dao.memory.SystemMemory;
import be.boomkwekerij.plant.dao.repository.SystemDAO;
import be.boomkwekerij.plant.dao.repository.SystemDAOImpl;
import be.boomkwekerij.plant.mapper.SystemMapper;
import be.boomkwekerij.plant.model.dto.SystemDTO;
import be.boomkwekerij.plant.model.repository.System;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.MemoryDatabase;
import be.boomkwekerij.plant.util.SearchResult;

public class SystemServiceImpl implements SystemService {

    private SystemDAO systemDAO = new SystemDAOImpl();
    private SystemMemory systemMemory = MemoryDatabase.getSystemMemory();

    private SystemMapper systemMapper = new SystemMapper();

    public CrudsResult createSystem(SystemDTO systemDTO) {
        System system = systemMapper.mapDTOToDAO(systemDTO);
        CrudsResult crudsResult = systemDAO.persist(system);

        if (crudsResult.isSuccess()) {
            systemMemory.createSystem(system);
        }

        return crudsResult;
    }

    public SearchResult<SystemDTO> getSystem() {
        SearchResult<System> searchResult = systemMemory.getSystem();

        SearchResult<SystemDTO> customerSearchResult = new SearchResult<SystemDTO>();
        customerSearchResult.setSuccess(searchResult.isSuccess());
        customerSearchResult.setMessages(searchResult.getMessages());

        if (searchResult.isSuccess()) {
            System system = searchResult.getFirst();
            SystemDTO systemDTO = systemMapper.mapDAOToDTO(system);
            customerSearchResult.addResult(systemDTO);
        }

        return customerSearchResult;
    }

    public CrudsResult updateSystem(SystemDTO systemDTO) {
        System system = systemMapper.mapDTOToDAO(systemDTO);
        CrudsResult crudsResult = systemDAO.update(system);

        if (crudsResult.isSuccess()) {
            systemMemory.updateSystem(system);
        }

        return crudsResult;
    }

    public CrudsResult deleteSystem() {
        CrudsResult crudsResult = systemDAO.delete();

        if (crudsResult.isSuccess()) {
            systemMemory.deleteSystem();
        }

        return crudsResult;
    }
}
