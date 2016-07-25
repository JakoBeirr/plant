package be.boomkwekerij.plant.controller;

import be.boomkwekerij.plant.service.BackupService;
import be.boomkwekerij.plant.service.BackupServiceImpl;

import java.io.IOException;

public class BackupController {

    private BackupService backupService = new BackupServiceImpl();

    public void backupDatabase() throws IOException {
        backupService.backupDatabase();
    }

    public void restoreBackupDatabase() throws IOException {
        backupService.restoreBackup();
    }
}
