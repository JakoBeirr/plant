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

import java.util.Collections;
import java.util.List;

public class SystemServiceImpl implements SystemService {

    private SystemDAO systemDAO = new SystemDAOImpl();
    private SystemMemory systemMemory = MemoryDatabase.getSystemMemory();

    private SystemMapper systemMapper = new SystemMapper();
    private SystemValidator systemValidator = new SystemValidator();

    public CrudsResult createSystem(SystemDTO systemDTO) {
        CrudsResult validateResult = validateSystem(systemDTO);
        if (validateResult.isError()) {
            return validateResult;
        }

        System system = systemMapper.mapDTOToDAO(systemDTO);
        CrudsResult createResult = systemDAO.persist(system);
        if (createResult.isSuccess()) {
            systemMemory.createSystem(system);
        }
        return createResult;
    }

    public SearchResult<SystemDTO> getSystem() {
        SearchResult<System> searchResult = systemMemory.getSystem();
        if (searchResult.isSuccess()) {
            System system = searchResult.getFirst();
            if (system != null) {
                SystemDTO systemDTO = systemMapper.mapDAOToDTO(system);
                return new SearchResult<SystemDTO>().success(Collections.singletonList(systemDTO));
            }
        }
        return new SearchResult<SystemDTO>().error(searchResult.getMessages());
    }

    public CrudsResult updateSystem(SystemDTO systemDTO) {
        CrudsResult validateResult = validateSystem(systemDTO);
        if (validateResult.isError()) {
            return validateResult;
        }

        System system = systemMapper.mapDTOToDAO(systemDTO);
        CrudsResult updateResult = systemDAO.update(system);
        if (updateResult.isSuccess()) {
            systemMemory.updateSystem(system);
        }
        return updateResult;
    }

    public CrudsResult deleteSystem() {
        CrudsResult deleteResult = systemDAO.delete();
        if (deleteResult.isSuccess()) {
            systemMemory.deleteSystem();
        }
        return deleteResult;
    }

    public String getNextInvoiceNumber() {
        SearchResult<SystemDTO> searchResult = getSystem();
        if (searchResult.isSuccess()) {
            SystemDTO system = searchResult.getFirst();
            return system.getNextInvoiceNumber();
        }
        throw new IllegalArgumentException("Systeem werd niet gevonden");
    }

    public void setNextInvoiceNumber(String invoiceNumber) {
        String nextInvoiceNumber = determineNextInvoiceNumber(invoiceNumber);

        SearchResult<SystemDTO> searchResult = getSystem();
        if (searchResult.isSuccess()) {
            SystemDTO system = searchResult.getFirst();
            system.setNextInvoiceNumber(nextInvoiceNumber);
            CrudsResult updateResult = updateSystem(system);
            if (updateResult.isError()) {
                throw new IllegalArgumentException("Bewerken van volgend factuurnummer mislukt");
            }
        }
    }

    private String determineNextInvoiceNumber(String invoiceNumber) {
        if (invoiceNumber.length() < 5) {
            throw new IllegalArgumentException("Factuurnummer moet minstens 5 tekens lang zijn");
        }

        String invoiceYear = invoiceNumber.substring(0,4);
        String invoiceNumberPart = invoiceNumber.substring(4, invoiceNumber.length());
        int invoiceNumberPartLength = invoiceNumberPart.length();

        int invoiceNumberPartInteger = Integer.parseInt(invoiceNumberPart);
        int nextInvoiceNumberPartInteger = invoiceNumberPartInteger + 1;
        return invoiceYear + String.format("%0" + invoiceNumberPartLength + "d", nextInvoiceNumberPartInteger);
    }

    private CrudsResult validateSystem(SystemDTO systemDTO) {
        List<String> validationResult = systemValidator.validate(systemDTO);
        if (validationResult.size() > 0) {
            return new CrudsResult().error(validationResult);
        }
        return new CrudsResult().success();
    }
}
