package be.boomkwekerij.plant.util;

import java.io.File;

public class SystemRepository {

    private static String dataUri;

    public static String getDataUri() {
        return dataUri;
    }

    public static void init(String dataUri) {
        SystemRepository.dataUri = dataUri;

        initDirectoryStructure(dataUri);
    }

    private static void initDirectoryStructure(String dataUri) {
        createDirectoryWhenNotExists(dataUri, "/companies");
        createDirectoryWhenNotExists(dataUri, "/customers");
        createDirectoryWhenNotExists(dataUri, "/plants");
        createDirectoryWhenNotExists(dataUri, "/invoices");
        createDirectoryWhenNotExists(dataUri, "/system");
    }

    @SuppressWarnings("all")
    private static void createDirectoryWhenNotExists(String dataUri, String directory) {
        File file = new File(dataUri + directory);
        if (!file.exists()) {
            file.mkdir();
        }
    }
}
