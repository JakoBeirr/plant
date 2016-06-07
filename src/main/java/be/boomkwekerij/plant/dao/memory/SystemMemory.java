package be.boomkwekerij.plant.dao.memory;

import be.boomkwekerij.plant.model.repository.System;

public interface SystemMemory {

    void createSystem(System system);

    void updateSystem(System system);

    void deleteSystem();
}
