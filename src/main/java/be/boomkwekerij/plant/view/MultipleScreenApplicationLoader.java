package be.boomkwekerij.plant.view;

import be.boomkwekerij.plant.view.controller.PageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.HashMap;

public class MultipleScreenApplicationLoader {

    private static HashMap<String, String> templates;
    private static BorderPane root;

    public MultipleScreenApplicationLoader() {
        templates = new HashMap<>();
        templates.put("customerList", "customer_list.fxml");
        templates.put("customerCreate", "customer_create.fxml");
        templates.put("customerModify", "customer_modify.fxml");
        templates.put("plantList", "plant_list.fxml");
        templates.put("plantCreate", "plant_create.fxml");
        templates.put("plantModify", "plant_modify.fxml");
        templates.put("invoiceList", "invoice_list.fxml");
        templates.put("invoiceCreate", "invoice_create.fxml");
        templates.put("invoiceModify", "invoice_modify.fxml");
        templates.put("archivedInvoiceList", "archived_invoice_list.fxml");
        templates.put("fustList", "fust_list.fxml");
        templates.put("fustCreate", "fust_create.fxml");
        templates.put("generalSettings", "general_settings.fxml");
        templates.put("backup", "backup.fxml");
        templates.put("report", "report.fxml");
    }

    public void load() throws IOException {
        root = FXMLLoader.load(getClass().getResource("/template/app.fxml"));
        MenuBar menu = FXMLLoader.load(getClass().getResource("/template/menu.fxml"));
        AnchorPane startPage = FXMLLoader.load(getClass().getResource("/template/start.fxml"));
        root.setTop(menu);
        root.setCenter(startPage);
    }

    public void reload(String tab) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/template/" + templates.get(tab)));
        AnchorPane page = fxmlLoader.load();
        root.setCenter(page);

        PageController controller = fxmlLoader.getController();
        controller.init(root);
    }

    public BorderPane getRoot() {
        return root;
    }
}