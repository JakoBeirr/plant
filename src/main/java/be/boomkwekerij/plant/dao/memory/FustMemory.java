package be.boomkwekerij.plant.dao.memory;

import be.boomkwekerij.plant.model.repository.Fust;
import be.boomkwekerij.plant.util.SearchResult;
import org.joda.time.DateTime;

import java.util.List;

public interface FustMemory {

    void createFust(Fust fust);

    void createFusts(List<Fust> fusts);

    SearchResult<Fust> getFust(String id);

    SearchResult<Fust> getFustFromCustomer(String customerId, DateTime date);

    SearchResult<Fust> getFusts();

    void deleteFust(String id);
}
