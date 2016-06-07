package be.boomkwekerij.plant.dao.repository;

import be.boomkwekerij.plant.model.repository.System;
import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;

public interface SystemDAO {

    SearchResult<System> get();

    CrudsResult persist(System system);

    CrudsResult update(System system);

    CrudsResult delete();
}
