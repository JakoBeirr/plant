package be.boomkwekerij.plant.util;

import java.util.ArrayList;
import java.util.List;

public class SearchResult<T> {

    private boolean success;
    private List<String> messages = new ArrayList<String>();
    private List<T> results = new ArrayList<T>();

    public boolean isSuccess() {
        return success;
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
