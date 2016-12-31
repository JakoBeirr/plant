package be.boomkwekerij.plant.view.services;

import be.boomkwekerij.plant.view.controller.AlertController;
import javafx.concurrent.Service;

public class ServiceHandler {

    public static void success(Service service) {
        AlertController.alertSuccess("Uitvoeren [" + service.getTitle() + "] succesvol afgerond");
    }

    public static void error(Service service) {
        Throwable throwedException = service.getException();
        if (throwedException != null) {
            Throwable cause = throwedException.getCause();
            if (throwedException.getMessage() == null && cause != null) {
                AlertController.alertError("Uitvoeren [" + service.getTitle() + "] gefaald", cause.getMessage());
            } else {
                AlertController.alertError("Uitvoeren [" + service.getTitle() + "] gefaald", throwedException.getMessage());
            }
        } else {
            AlertController.alertError("Uitvoeren [" + service.getTitle() + "] gefaald", "Onbekende reden");
        }
    }
}
