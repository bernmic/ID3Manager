package de.b4.jfx.handler;

import de.b4.jfx.Messages;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import org.kordamp.ikonli.javafx.FontIcon;

public class RedoHandler extends Handler {
    private static RedoHandler instance;

    public static Handler getInstance() {
        if (instance == null) {
            instance = new RedoHandler();
        }
        return instance;
    }

    @Override
    FontIcon getFontIcon() {
        return new FontIcon(getIconCode("fa-repeat"));
    }

    @Override
    String getMenuItemText() {
        return Messages.getString("menu.redo.label");
    }

    @Override
    String getTooltipText() {
        return Messages.getString("menu.redo.tooltip");
    }

    @Override
    EventHandler<ActionEvent> getEventHandler() {
        return this::action;
    }

    @Override
    KeyCodeCombination getKeyCodeCombination() {
        return new KeyCodeCombination(KeyCode.Y, KeyCombination.SHORTCUT_DOWN);
    }

    @Override
    boolean hasToolbarEntry() {
        return true;
    }

    private void action(ActionEvent actionEvent) {
        System.out.println("Redo...");
    }
}
