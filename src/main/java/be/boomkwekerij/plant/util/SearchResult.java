package be.boomkwekerij.plant.util;

import java.util.ArrayList;
import java.util.List;

public class SearchResult<T> {

    private boolean success;
    private List<String> messages = new ArrayList<String>();
    private List<T> results = new ArrayList<T>();

    public SearchResult<T> success(List<T> results) {
        this.success = true;
        this.results = results;
        return this;
    }

    public SearchResult<T> error(List<String> messages) {
        this.success = false;
        this.messages = messages;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isError() {
        return !success;
    }

    public List<String> getMessages() {
        return messages;
    }

    public List<T> getResults() {
        return results;
    }

    public T getFirst() {
        if (results.size() > 0) {
            return results.get(0);
        }
        return null;
    }
}
