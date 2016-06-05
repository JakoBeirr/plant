package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.util.SystemRepository;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;

import java.io.File;
import java.io.IOException;

public class BackupServiceImpl implements BackupService {

    private static final String PLANT_DIRECTORY = "C:/Plant";
    private static final String BACKUP_DIRECTORY = "C:/Plant/backup";

    public void backupDatabase() throws IOException {
        File sourceDirectory = new File(SystemRepository.getDataUri());
        File backupDirectory = createBackupFolder();

        FileUtils.copyDirectory(sourceDirectory, backupDirectory);
    }

    @SuppressWarnings("all")
    private File createBackupFolder() {
        createBackupsFolderIfNotExists();

        File backupDirectory = new File(getTimestamp());
        if (backupDirectory.exists()) {
            backupDirectory.delete();
        }
        backupDirectory.mkdir();
        return backupDirectory;
    }

    @SuppressWarnings("all")
    private void createBackupsFolderIfNotExists() {
        File plantDirectory = new File(PLANT_DIRECTORY);
        if (!plantDirectory.exists()) {
            plantDirectory.mkdir();
        }

        File backupsDirectory = new File(BACKUP_DIRECTORY);
        if (!backupsDirectory.exists()) {
            backupsDirectory.mkdir();
        }
    }

    private String getTimestamp() {
        DateTime dateTime = DateTime.now();
        return BACKUP_DIRECTORY + "/" + getYear(dateTime) + getMonth(dateTime) + getDay(dateTime);
    }

    private String getYear(DateTime dateTime) {
        int year = dateTime.getYear();
        return String.format("%04d", year);
    }

    private String getMonth(DateTime dateTime) {
        int monthOfYear = dateTime.getMonthOfYear();
        return String.format("%02d", monthOfYear);
    }

    private String getDay(DateTime dateTime) {
        int dayOfMonth = dateTime.getDayOfMonth();
        return String.format("%02d", dayOfMonth);
    }
}
