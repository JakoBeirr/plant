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
import be.boomkwekerij.plant.validator.SystemValidator;

import java.util.List;

public class SystemServiceImpl implements SystemService {

    private SystemDAO systemDAO = new SystemDAOImpl();
    private SystemMemory systemMemory = MemoryDatabase.getSystemMemory();

    private SystemMapper systemMapper = new SystemMapper();
    private SystemValidator systemValidator = new SystemValidator();

    public CrudsResult createSystem(SystemDTO systemDTO) {
        CrudsResult crudsResult = validateSystem(systemDTO);
        if (crudsResult != null) {
            return crudsResult;
        }

        System system = systemMapper.mapDTOToDAO(systemDTO);
        crudsResult = systemDAO.persist(system);

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
            if (system != null) {
                SystemDTO systemDTO = systemMapper.mapDAOToDTO(system);
                customerSearchResult.addResult(systemDTO);
            }
        }

        return customerSearchResult;
    }

    public CrudsResult updateSystem(SystemDTO systemDTO) {
        CrudsResult crudsResult = validateSystem(systemDTO);
        if (crudsResult != null) {
            return crudsResult;
        }

        System system = systemMapper.mapDTOToDAO(systemDTO);
        crudsResult = systemDAO.update(system);

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

    public String getNextInvoiceNumber() {
        SearchResult<SystemDTO> systemSearchResult = getSystem();
        if (systemSearchResult.isSuccess()) {
            SystemDTO system = systemSearchResult.getFirst();
            return system.getNextInvoiceNumber();
        }
        return "";
    }

    public void setNextInvoiceNumber(String invoiceNumber) {
        int invoiceNumberInteger = Integer.parseInt(invoiceNumber);
        int nextInvoiceNumberInteger = invoiceNumberInteger + 1;
        String nextInvoiceNumber = Integer.toString(nextInvoiceNumberInteger);

        SearchResult<SystemDTO> systemSearchResult = getSystem();
        if (systemSearchResult.isSuccess()) {
            SystemDTO system = systemSearchResult.getFirst();
            system.setNextInvoiceNumber(nextInvoiceNumber);
            updateSystem(system);
        }
    }

    private CrudsResult validateSystem(SystemDTO systemDTO) {
        List<String> validationResult = systemValidator.validate(systemDTO);
        if (validationResult.size() > 0) {
            CrudsResult crudsResult = new CrudsResult();
            crudsResult.setSuccess(false);
            crudsResult.setMessages(validationResult);
            return crudsResult;
        }
        return null;
    }
}
