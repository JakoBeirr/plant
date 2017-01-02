package be.boomkwekerij.plant.dao.memory;

import be.boomkwekerij.plant.model.repository.System;
import be.boomkwekerij.plant.util.SearchResult;

import java.util.Collections;

public class SystemMemoryImpl implements SystemMemory {

    private System system = null;

    public void createSystem(System system) {
        this.system = system;
    }

    public SearchResult<System> getSystem() {
        if (system != null) {
            return new SearchResult<System>().success(Collections.singletonList(system));
        }
        return new SearchResult<System>().error(Collections.singletonList("Onbekend systeem"));
    }

    public void updateSystem(System system) {
        this.system = system;
    }

    public void deleteSystem() {
        system = null;
    }
}
