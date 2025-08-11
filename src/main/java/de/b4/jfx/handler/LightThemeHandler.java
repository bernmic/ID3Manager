package de.b4.jfx.handler;

import atlantafx.base.theme.PrimerLight;
import de.b4.jfx.Main;
import de.b4.jfx.Messages;
import de.b4.jfx.model.Configuration;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCodeCombination;
import org.kordamp.ikonli.javafx.FontIcon;

public class LightThemeHandler extends Handler {

    private static LightThemeHandler instance;

    public static Handler getInstance() {
        if (instance == null) {
            instance = new LightThemeHandler();
        }
        return instance;
    }

    @Override
    FontIcon getFontIcon() {
        return null;
    }

    @Override
    String getMenuItemText() {
        return Messages.getString("menu.theme.light.label");
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
        Application.setUserAgentStylesheet(
            new PrimerLight().getUserAgentStylesheet()
        );
        Main.theApp.getConfiguration().setTheme(Configuration.THEME_LIGHT);
    }
}
