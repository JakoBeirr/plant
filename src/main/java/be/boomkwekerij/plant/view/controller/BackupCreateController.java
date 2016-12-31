package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.view.services.BackupCreateService;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class BackupCreateController implements PageController {

    private BackupCreateService backupCreateService = new BackupCreateService();

    @FXML
    private Button backupButton;
    @FXML
    private Button restoreBackupButton;

    @Override
    public void init(Pane root) {
        backupCreateService.setBackupButton(backupButton);
        backupCreateService.setRestoreBackupButton(restoreBackupButton);
        backupCreateService.init(root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void backup(Event event) {
        backupCreateService.backupService.restart();
    }

    public void restoreBackup(Event event) {
        backupCreateService.restoreBackupService.restart();
    }
}
