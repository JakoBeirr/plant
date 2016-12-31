package be.boomkwekerij.plant.controller;

import be.boomkwekerij.plant.service.BackupService;
import be.boomkwekerij.plant.service.BackupServiceImpl;

public class BackupController {

    private BackupService backupService = new BackupServiceImpl();

    public void backupDatabase() {
        try {
            backupService.backupDatabase();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void restoreBackupDatabase() {
        try {
            backupService.restoreBackup();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
