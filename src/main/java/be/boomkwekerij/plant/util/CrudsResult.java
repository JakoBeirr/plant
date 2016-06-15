package be.boomkwekerij.plant.util;

import java.util.ArrayList;
import java.util.List;

public class CrudsResult {

    private boolean success;
    private List<String> messages = new ArrayList<String>();
    private String value;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
