package de.b4.jfx.handler;

import de.b4.jfx.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCodeCombination;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SkinHandler extends Handler {

    private static Map<String, SkinHandler> instance = new HashMap<>();
    private String skinName;

    private SkinHandler(String skinName) {
        super();
        this.skinName = skinName;
    }

    public static Handler getInstance(String skinName) {
        if (instance.get(skinName) == null) {
            SkinHandler sh = new SkinHandler(skinName);
            instance.put(skinName, sh);
        }
        return instance.get(skinName);
    }

    @Override
    public MenuItem getMenuItem() {
        this.menuItem.setText(skinName);
        return this.menuItem;
    }

    @Override
    FontIcon getFontIcon() {
        return null;
    }

    @Override
    String getMenuItemText() {
        return this.skinName;
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
        Scene s = Main.theApp.rootStage.getScene();
        s.getStylesheets().clear();
        File f = new File("./skins");
        s.getStylesheets().add("file:///" + f.getAbsolutePath() + "/" + skinName + ".css");
        System.out.println(skinName);
    }
}
