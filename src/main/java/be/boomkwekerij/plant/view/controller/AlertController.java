package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.util.ExceptionUtil;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.util.Optional;

public class AlertController {

    public static void alertSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SUCCESS");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public static void alertError(String headerMessage, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(headerMessage);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public static void alertException(String headerMessage, Throwable e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(headerMessage);
        alert.setContentText("The exception stacktrace was:");

        String exceptionText = ExceptionUtil.getStackTrace(e);

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane exceptionContent = new GridPane();
        exceptionContent.setMaxWidth(Double.MAX_VALUE);
        exceptionContent.add(textArea, 0, 1);
        alert.getDialogPane().setExpandableContent(exceptionContent);

        alert.showAndWait();
    }

    public static boolean areYouSure(String headerMessage, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("BEVESTIGING");
        alert.setHeaderText(headerMessage);
        alert.setContentText(message);

        Optional<ButtonType> buttonType = alert.showAndWait();
        return buttonType.get() == ButtonType.OK;
    }
}
