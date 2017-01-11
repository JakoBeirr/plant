package be.boomkwekerij.plant.view;

import be.boomkwekerij.plant.view.controller.AlertController;

public class FxUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        AlertController.alertException("Onbekende fout opgetreden!", e);
    }
}
