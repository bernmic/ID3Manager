package de.b4.jfx.components;

import javafx.collections.FXCollections;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

import java.util.Collection;

public class MultiEdit<T> {
    private ComboBox<T> comboBox;
    private CheckBox checkBox;
    private boolean manualChanged = false;

    public MultiEdit() {
        comboBox = new ComboBox<>();
        comboBox.setMaxWidth(Double.MAX_VALUE);
        comboBox.setEditable(true);
        checkBox = new CheckBox();
        setChangeHandler();
    }

    public boolean hasChanged() {
        return checkBox.isSelected();
    }

    public void setItems(Collection<T> items) {
        comboBox.setItems(FXCollections.observableArrayList(items));
        if (items.size() == 1) {
            selectItem(0);
        }
    }

    public ComboBox<T> getComboBox() {
        return comboBox;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void selectItem(int i) {
        manualChanged = true;
        comboBox.getSelectionModel().select(i);
    }

    public void setDisable(boolean disable) {
        comboBox.setDisable(disable);
        checkBox.setDisable(disable);
    }

    private void setChangeHandler() {
        comboBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (!manualChanged) {
                checkBox.setSelected(true);
            }
            manualChanged = false;
        });
    }
}
