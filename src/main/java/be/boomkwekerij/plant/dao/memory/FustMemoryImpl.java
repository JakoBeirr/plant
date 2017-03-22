package be.boomkwekerij.plant.dao.memory;

import be.boomkwekerij.plant.model.repository.Fust;
import be.boomkwekerij.plant.util.SearchResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class FustMemoryImpl implements FustMemory {

    private HashMap<String, Fust> fusts = new HashMap<String, Fust>();

    public void createFust(Fust fust) {
        fusts.put(fust.getId(), fust);
    }

    @Override
    public void createFusts(List<Fust> fustList) {
        for (Fust fust : fustList) {
            fusts.put(fust.getId(), fust);
        }
    }

    public SearchResult<Fust> getFust(String id) {
        if (id == null) {
            return new SearchResult<Fust>().error(Collections.singletonList("Kon geen fust vinden voor id null!"));
        } else {
            Fust fust = fusts.get(id);
            if (fust != null) {
                return new SearchResult<Fust>().success(Collections.singletonList(fust));
            }
            return new SearchResult<Fust>().error(Collections.singletonList("Onbekende fust"));
        }
    }

    @Override
    public SearchResult<Fust> getFusts() {
        return new SearchResult<Fust>().success(new ArrayList<Fust>(fusts.values()));
    }

    @Override
    public SearchResult<Fust> getFustFromCustomer(String customerId) {
        if (customerId == null) {
            return new SearchResult<Fust>().error(Collections.singletonList("Kon geen fust vinden voor klant met id null!"));
        } else {
            List<Fust> fustsFromCustomer = new ArrayList<>();
            for (Fust fust : fusts.values()) {
                if (fustFromCustomer(fust, customerId)) {
                    fustsFromCustomer.add(fust);
                }
            }
            return new SearchResult<Fust>().success(fustsFromCustomer);
        }
    }

    private boolean fustFromCustomer(Fust fust, String customerId) {
        return fust.getCustomerId() != null && fust.getCustomerId().equals(customerId);
    }

    @Override
    public void deleteFust(String id) {
        fusts.remove(id);
    }
}
