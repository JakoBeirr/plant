package be.boomkwekerij.plant.controller;

import be.boomkwekerij.plant.service.BackupService;
import be.boomkwekerij.plant.service.BackupServiceImpl;
import be.boomkwekerij.plant.util.CrudsResult;

import java.util.Collections;

public class BackupController {

    private BackupService backupService = new BackupServiceImpl();

    public CrudsResult backup() {
        try {
            return backupService.backup();
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }

    public CrudsResult restoreBackup() {
        try {
            return backupService.restoreBackup();
        } catch (Exception e) {
            return new CrudsResult().error(Collections.singletonList(e.getMessage()));
        }
    }
}
