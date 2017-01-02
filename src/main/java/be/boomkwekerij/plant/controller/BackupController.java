package be.boomkwekerij.plant.controller;

import be.boomkwekerij.plant.service.BackupService;
import be.boomkwekerij.plant.service.BackupServiceImpl;

public class BackupController {

    private BackupService backupService = new BackupServiceImpl();

    public void backup() {
        try {
            backupService.backup();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void restoreBackup() {
        try {
            backupService.restoreBackup();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
