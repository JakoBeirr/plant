package be.boomkwekerij.plant.dao;

import java.io.Serializable;
import java.util.List;

public interface BaseDao<T, K extends Serializable> {

    T get(K id);

    List<T> findAll();

    void persist(T t);

    void update(T t);

    void delete(K id);

    void deleteAll();
}