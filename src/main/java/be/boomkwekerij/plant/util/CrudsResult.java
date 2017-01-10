package be.boomkwekerij.plant.util;

import java.util.ArrayList;
import java.util.List;

public class CrudsResult {

    private boolean success;
    private List<String> messages = new ArrayList<String>();
    private String value;

    public CrudsResult success() {
        this.success = true;
        return this;
    }

    public CrudsResult success(String value) {
        this.success = true;
        this.value = value;
        return this;
    }

    public CrudsResult error(List<String> messages) {
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

    public String getValue() {
        return value;
    }
}
