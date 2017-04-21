package be.boomkwekerij.plant.view;

import be.boomkwekerij.plant.util.InitializerSingleton;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class PlantApplication extends Application {

    @Override
    public void init() throws Exception {
        InitializerSingleton.getInitializer().init();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Thread.setDefaultUncaughtExceptionHandler(new FxUncaughtExceptionHandler());
        prepareClient(primaryStage);
    }

    private void prepareClient(Stage primaryStage) throws IOException {
        MultipleScreenApplicationLoader applicationLoader = new MultipleScreenApplicationLoader();
        applicationLoader.load();

        primaryStage.getIcons().add(new Image(ClassLoader.getSystemResourceAsStream("invoiceDocument/logo.png")));
        primaryStage.setTitle("Plant");
        primaryStage.setScene(getScene(applicationLoader.getRoot()));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    private Scene getScene(BorderPane root) {
        Scene scene = new Scene(root, 300, 275);
        scene.getStylesheets().add("/style/app.css");
        return scene;
    }
}
