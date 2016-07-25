package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.controller.BackupController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class BackupViewController implements Initializable {

    private BackupController backupController = new BackupController();

    @FXML
    private Button backupButton;
    @FXML
    private Button restoreBackupButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void backupButton(Event event) {
        try {
            backupController.backupDatabase();
            AlertController.alertSuccess("Backup aangemaakt en is te vinden op volgende locatie: C:/plant/backup");
        } catch (Exception e) {
            AlertController.alertException("Backup aanmaken gefaald", e);
        }
    }

    public void restoreBackupButton(Event event) {
        try {
            backupController.restoreBackupDatabase();
            AlertController.alertSuccess("Laatste backup is hersteld");
        } catch (Exception e) {
            AlertController.alertException("Backup herstellen gefaald", e);
        }
    }
}
