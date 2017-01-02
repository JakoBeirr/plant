package be.boomkwekerij.plant.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
        this.success = true;
        this.messages = messages;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isError() {
        return !success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public void addMessage(String message) {
        messages.add(message);
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    public void addResult(T result) {
        results.add(result);
    }

    public T getFirst() {
        if (results.size() > 0) {
            return results.get(0);
        }
        return null;
    }
}
