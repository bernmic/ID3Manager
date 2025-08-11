package de.b4.jfx.handler;

import de.b4.jfx.Messages;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import org.kordamp.ikonli.javafx.FontIcon;

public class CutHandler extends SelectedHandler {
    private static CutHandler instance;

    public static Handler getInstance() {
        if (instance == null) {
            instance = new CutHandler();
        }
        return instance;
    }

    @Override
    FontIcon getFontIcon() {
        return new FontIcon(getIconCode("fa-cut"));
    }

    @Override
    String getMenuItemText() {
        return Messages.getString("menu.cut.label");
    }

    @Override
    String getTooltipText() {
        return Messages.getString("menu.cut.tooltip");
    }

    @Override
    EventHandler<ActionEvent> getEventHandler() {
        return this::action;
    }

    @Override
    KeyCodeCombination getKeyCodeCombination() {
        return new KeyCodeCombination(KeyCode.X, KeyCombination.SHORTCUT_DOWN);
    }

    @Override
    boolean hasToolbarEntry() {
        return true;
    }

    private void action(ActionEvent actionEvent) {
        System.out.println("Cut...");
    }
}
