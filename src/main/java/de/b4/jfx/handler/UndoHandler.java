package de.b4.jfx.handler;

import de.b4.jfx.Messages;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import org.kordamp.ikonli.javafx.FontIcon;

public class UndoHandler extends Handler {
    private static UndoHandler instance;

    public static Handler getInstance() {
        if (instance == null) {
            instance = new UndoHandler();
        }
        return instance;
    }

    @Override
    FontIcon getFontIcon() {
        return new FontIcon(getIconCode("fa-undo"));
    }

    @Override
    String getMenuItemText() {
        return Messages.getString("menu.undo.label");
    }

    @Override
    String getTooltipText() {
        return Messages.getString("menu.undo.tooltip");
    }

    @Override
    EventHandler<ActionEvent> getEventHandler() {
        return this::action;
    }

    @Override
    KeyCodeCombination getKeyCodeCombination() {
        return new KeyCodeCombination(KeyCode.Z, KeyCombination.SHORTCUT_DOWN);
    }

    @Override
    boolean hasToolbarEntry() {
        return true;
    }

    private void action(ActionEvent actionEvent) {
        System.out.println("Undo...");
    }
}
