package be.boomkwekerij.plant;

import be.boomkwekerij.plant.util.InitializerSingleton;
import be.boomkwekerij.plant.view.PlantApplication;
import be.boomkwekerij.plant.view.PlantApplicationPreloader;
import com.sun.javafx.application.LauncherImpl;

public class TestPlantClient {

    public static void main(String[] args) {
        String dataUri = "C:\\Users\\Janse\\Desktop\\data";
        InitializerSingleton.getInitializer().setDataDirectory(dataUri);

        LauncherImpl.launchApplication(PlantApplication.class, PlantApplicationPreloader.class, args);
    }
}
