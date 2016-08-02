package be.boomkwekerij.plant.dao.memory;

import be.boomkwekerij.plant.model.repository.System;
import be.boomkwekerij.plant.util.SearchResult;

public class SystemMemoryImpl implements SystemMemory {

    private System system = null;

    public void createSystem(System system) {
        this.system = system;
    }

    public SearchResult<System> getSystem() {
        SearchResult<System> searchResult = new SearchResult<System>();
        searchResult.setSuccess(true);
        if (system != null) {
            searchResult.addResult(system);
        }
        return searchResult;
    }

    public void updateSystem(System system) {
        this.system = system;
    }

    public void deleteSystem() {
        system = null;
    }
}
