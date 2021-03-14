package be.boomkwekerij.plant.model.dto;

import java.util.UUID;

public class BestandDTO {

    private String name;
    private byte[] file;

    public String getName() {
        if (name != null) {
            return name.replaceAll("[\\\\/:*?\"<>|]", "_");
        }
        return UUID.randomUUID().toString();
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }
}
