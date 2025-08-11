package de.b4.jfx.handler;

import de.b4.jfx.Messages;
import de.b4.jfx.dialogs.AboutDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCodeCombination;
import org.kordamp.ikonli.javafx.FontIcon;

public class AboutHandler extends Handler {
    private static AboutHandler instance;

    public static Handler getInstance() {
        if (instance == null) {
            instance = new AboutHandler();
        }
        return instance;
    }

    @Override
    FontIcon getFontIcon() {
        return null;
    }

    @Override
    String getMenuItemText() {
        return Messages.getString("menu.about.label");
    }

    @Override
    String getTooltipText() {
        return null;
    }

    @Override
    EventHandler<ActionEvent> getEventHandler() {
        return this::action;
    }

    @Override
    KeyCodeCombination getKeyCodeCombination() {
        return null;
    }

    private void action(ActionEvent actionEvent) {
        AboutDialog.newInstance().showAndWait();
    }
}

