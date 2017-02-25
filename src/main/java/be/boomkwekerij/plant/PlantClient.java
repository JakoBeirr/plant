package be.boomkwekerij.plant;

import be.boomkwekerij.plant.util.Initializer;
import be.boomkwekerij.plant.view.PlantApplication;
import be.boomkwekerij.plant.view.PlantApplicationPreloader;
import com.sun.javafx.application.LauncherImpl;

public class PlantClient {

    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Data uri not specified!");
        }

        String dataUri = args[0];
        Initializer.launch(dataUri);

        LauncherImpl.launchApplication(PlantApplication.class, PlantApplicationPreloader.class, args);
    }
}
