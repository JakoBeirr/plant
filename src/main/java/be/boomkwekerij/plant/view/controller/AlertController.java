package be.boomkwekerij.plant.view.controller;

import be.boomkwekerij.plant.util.ExceptionUtil;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

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

    public static void alertException(String headerMessage, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(headerMessage);

        String exceptionText = ExceptionUtil.getStackTrace(e);

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane exceptionContent = new GridPane();
        exceptionContent.setMaxWidth(Double.MAX_VALUE);
        exceptionContent.add(label, 0, 0);
        exceptionContent.add(textArea, 0, 1);
        alert.getDialogPane().setExpandableContent(exceptionContent);

        alert.showAndWait();
    }
}
