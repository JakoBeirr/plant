package be.boomkwekerij.plant.view.helper;

import javafx.scene.control.TableCell;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.util.Callback;

public class CheckboxCellFactory implements Callback {

    @Override
    public TableCell call(Object param) {
        return new CheckBoxTableCell();
    }
}
