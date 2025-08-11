package de.b4.jfx.handler;

import de.b4.jfx.Main;
import de.b4.jfx.Messages;
import de.b4.jfx.model.Configuration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCodeCombination;
import org.kordamp.ikonli.javafx.FontIcon;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class NormalFontHandler extends Handler {

    private static NormalFontHandler instance;

    public static Handler getInstance() {
        if (instance == null) {
            instance = new NormalFontHandler();
        }
        return instance;
    }

    @Override
    FontIcon getFontIcon() {
        return null;
    }

    @Override
    String getMenuItemText() {
        return Messages.getString("font.size.normal.label");
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
