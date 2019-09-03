package de.b4.jfx.handler;

import de.b4.jfx.Main;
import de.b4.jfx.model.Song;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

public class RemoveID3V2Handler extends Handler {
  private static RemoveID3V2Handler instance;

  public static Handler getInstance() {
    if (instance == null) {
      instance = new RemoveID3V2Handler();
    }
    return instance;
  }

  public MenuItem createMenuItem() {
    MenuItem menuItem = new MenuItem("Remove ID3v2");
    menuItem.setOnAction(RemoveID3V2Handler::action);
    menuItem.setAccelerator(new KeyCodeCombination(KeyCode.DIGIT2, KeyCombination.ALT_DOWN));
    return menuItem;
  }

  public Button createToolbarButton() {
    return null;
  }

  private static void action(ActionEvent actionEvent) {
    for (Song song : Main.theApp.getSelectedSongs()) {
      song.removeID3v2();
    }
    Main.theApp.songTable.refresh();
  }
}
