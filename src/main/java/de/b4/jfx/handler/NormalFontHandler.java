package de.b4.jfx.handler;

import atlantafx.base.theme.PrimerDark;
import de.b4.jfx.Main;
import de.b4.jfx.Messages;
import de.b4.jfx.dialogs.AboutDialog;
import de.b4.jfx.model.Configuration;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

public class NormalFontHandler extends Handler {

    private static NormalFontHandler instance;

    public static Handler getInstance() {
        if (instance == null) {
            instance = new NormalFontHandler();
        }
        return instance;
    }

    @Override
    Button createToolbarButton() {
        return null;
    }

    public MenuItem createMenuItem() {
        MenuItem menuItem = new MenuItem(
            Messages.getString("font.size.normal.label")
        );
        menuItem.setOnAction(this::action);
        return menuItem;
    }

    private void action(ActionEvent actionEvent) {
        Scene scene = Main.theApp.rootStage.getScene();
        scene
            .getStylesheets()
            .add(
                "data:text/css;base64," +
                Base64.getEncoder()
                    .encodeToString(
                        ".root { -fx-font-size: 12pt; }".getBytes(
                                StandardCharsets.UTF_8
                            )
                    )
            );
        Main.theApp
            .getConfiguration()
            .setFontSize(Configuration.FONT_SIZE_NORMAL);
    }
}
