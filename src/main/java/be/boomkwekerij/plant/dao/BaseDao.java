package be.boomkwekerij.plant.dao;

import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.SearchResult;

import java.io.Serializable;

public interface BaseDao<T, K extends Serializable> {

    SearchResult<T> get(K id);

    SearchResult<T> findAll();

    CrudsResult persist(T t);

    CrudsResult update(T t);

    CrudsResult delete(K id);

    CrudsResult deleteAll();
}