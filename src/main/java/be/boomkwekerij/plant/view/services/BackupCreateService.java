package be.boomkwekerij.plant.view.services;

import be.boomkwekerij.plant.controller.BackupController;
import be.boomkwekerij.plant.util.CrudsResult;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.util.Arrays;

public class BackupCreateService {

    private BackupController backupController = new BackupController();

    private Button backupButton;
    private Button restoreBackupButton;

    public final Service backupService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Maken backup");

                    CrudsResult backupResult = backupController.backup();
                    if (backupResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(backupResult.getMessages().toArray()));
                    }

                    return null;
                }
            };
        }
    };

    public final Service restoreBackupService = new Service() {
        @Override
        protected Task createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateTitle("Herstellen backup");

                    CrudsResult restoreBackupResult = backupController.restoreBackup();
                    if (restoreBackupResult.isError()) {
                        throw new IllegalArgumentException(Arrays.toString(restoreBackupResult.getMessages().toArray()));
                    }

                    return null;
                }
            };
        }
    };

    public void setBackupButton(Button backupButton) {
        this.backupButton = backupButton;
    }

    public void setRestoreBackupButton(Button restoreBackupButton) {
        this.restoreBackupButton = restoreBackupButton;
    }

    public void init(Pane root) {
        root.cursorProperty()
                .bind(Bindings.when(backupService.runningProperty()
                            .or(restoreBackupService.runningProperty()))
                        .then(Cursor.WAIT)
                        .otherwise(Cursor.DEFAULT)
                );
        backupButton.disableProperty()
                .bind(backupService.runningProperty());
        restoreBackupButton.disableProperty()
                .bind(restoreBackupService.runningProperty());

        backupService.setOnSucceeded(serviceEvent -> {
            ServiceHandler.success(backupService);
        });
        backupService.setOnFailed(serviceEvent -> {
            ServiceHandler.error(backupService);
        });
        restoreBackupService.setOnSucceeded(serviceEvent -> {
            ServiceHandler.success(restoreBackupService);
        });
        restoreBackupService.setOnFailed(serviceEvent -> {
            ServiceHandler.error(restoreBackupService);
        });
    }
}
