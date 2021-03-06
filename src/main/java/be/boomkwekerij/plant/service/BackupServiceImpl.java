package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.util.CrudsResult;
import be.boomkwekerij.plant.util.InitializerSingleton;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class BackupServiceImpl implements BackupService {

    private static final String PLANT_DIRECTORY = "C:/plant";
    private static final String BACKUP_DIRECTORY = "C:/plant/backup";

    public CrudsResult backup() throws IOException {
        File sourceDirectory = new File(InitializerSingleton.getInitializer().getDataDirectory());
        File backupDirectory = createBackupFolder();

        FileUtils.copyDirectory(sourceDirectory, backupDirectory);

        return new CrudsResult().success();
    }

    @SuppressWarnings("all")
    private File createBackupFolder() throws IOException {
        createBackupsFolderIfNotExists();

        File backupDirectory = new File(getTimestamp());
        if (backupDirectory.exists()) {
            FileUtils.deleteDirectory(backupDirectory);
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

    @Override
    public CrudsResult restoreBackup() throws IOException {
        String latestBackupDirectory = getLatestBackupDirectory();
        backupSourceDirectory();
        copyLatestBackup(latestBackupDirectory);

        InitializerSingleton.getInitializer().reloadInMemoryDatabase();

        return new CrudsResult().success();
    }

    private String getLatestBackupDirectory() {
        String[] directories = getAllBackupDirectories();
        return getLatestBackup(directories);
    }

    private String[] getAllBackupDirectories() {
        File backupsDirectory = new File(BACKUP_DIRECTORY);
        return backupsDirectory.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
    }

    private String getLatestBackup(String[] directories) {
        String latestBackupDirectory = null;
        for (int i = 0; i < directories.length; i++) {
            if (i == 0) {
                latestBackupDirectory = directories[i];
            } else {
                if (Integer.parseInt(directories[i]) > Integer.parseInt(latestBackupDirectory)) {
                    latestBackupDirectory = directories[i];
                }
            }
        }
        if (latestBackupDirectory == null) {
            throw new IllegalArgumentException("No database to restore");
        }
        return BACKUP_DIRECTORY + "/" + latestBackupDirectory;
    }

    private void backupSourceDirectory() throws IOException {
        File sourceDirectory = new File(InitializerSingleton.getInitializer().getDataDirectory());
        File previousSourceDirectory = new File(InitializerSingleton.getInitializer().getDataDirectory() + ".previous");
        if (previousSourceDirectory.exists()) {
            FileUtils.deleteDirectory(previousSourceDirectory);
        }

        FileUtils.copyDirectory(sourceDirectory, previousSourceDirectory);
    }

    private void copyLatestBackup(String latestBackupDirectory) throws IOException {
        File backupDirectory = new File(latestBackupDirectory);
        File sourceDirectory = new File(InitializerSingleton.getInitializer().getDataDirectory());

        FileUtils.deleteDirectory(sourceDirectory);

        FileUtils.copyDirectory(backupDirectory, sourceDirectory);
    }
}
