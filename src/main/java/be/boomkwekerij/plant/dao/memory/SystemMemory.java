package be.boomkwekerij.plant.dao.memory;

import be.boomkwekerij.plant.model.repository.System;
import be.boomkwekerij.plant.util.SearchResult;

public interface SystemMemory {

    void createSystem(System system);

    SearchResult<System> getSystem();

    void updateSystem(System system);

    void deleteSystem();
}
