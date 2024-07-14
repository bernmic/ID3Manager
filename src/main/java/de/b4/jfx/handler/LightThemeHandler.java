package de.b4.jfx.handler;

import atlantafx.base.theme.PrimerLight;
import de.b4.jfx.Main;
import de.b4.jfx.Messages;
import de.b4.jfx.dialogs.AboutDialog;
import de.b4.jfx.model.Configuration;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

public class LightThemeHandler extends Handler {

    private static LightThemeHandler instance;

    public static Handler getInstance() {
        if (instance == null) {
            instance = new LightThemeHandler();
        }
        return instance;
    }

    @Override
    Button createToolbarButton() {
        return null;
    }

    public MenuItem createMenuItem() {
        MenuItem menuItem = new MenuItem(
            Messages.getString("menu.theme.light.label")
        );
        menuItem.setOnAction(this::action);
        return menuItem;
    }

    private void action(ActionEvent actionEvent) {
        Application.setUserAgentStylesheet(
            new PrimerLight().getUserAgentStylesheet()
        );
        Main.theApp.getConfiguration().setTheme(Configuration.THEME_LIGHT);
    }
}
