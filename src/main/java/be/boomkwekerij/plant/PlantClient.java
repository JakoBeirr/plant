package be.boomkwekerij.plant;

import be.boomkwekerij.plant.util.Initializer;

public class PlantClient {

    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Data uri not specified!");
        }

        String dataUri = args[0];

        Initializer.init(dataUri);
    }
}
