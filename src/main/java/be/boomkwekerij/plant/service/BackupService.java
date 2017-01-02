package be.boomkwekerij.plant.service;

import java.io.IOException;

public interface BackupService {

    void backup() throws IOException;

    void restoreBackup() throws IOException;
}
