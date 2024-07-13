package de.b4.jfx.handler;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import de.b4.jfx.Main;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.Scene;

public class SkinHandler extends Handler {

    private static Map<String,SkinHandler> instance = new HashMap<>();
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
    Button createToolbarButton() {
        return null;
    }
    
    @Override
    public MenuItem getMenuItem() {
        this.menuItem.setText(skinName);
        return this.menuItem;
    }

    public MenuItem createMenuItem() {
        MenuItem menuItem = new MenuItem(skinName);
        menuItem.setOnAction(this::action);
        return menuItem;
    }

    private void action(ActionEvent actionEvent) {
        Scene s = Main.theApp.rootStage.getScene();
        s.getStylesheets().clear();
        File f = new File("./skins");
        s.getStylesheets().add("file:///" + f.getAbsolutePath() + "/" + skinName + ".css");
        System.out.println(skinName);
    }
}
