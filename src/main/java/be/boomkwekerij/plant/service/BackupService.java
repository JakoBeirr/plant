package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.util.CrudsResult;

import java.io.IOException;

public interface BackupService {

    CrudsResult backup() throws IOException;

    CrudsResult restoreBackup() throws IOException;
}
