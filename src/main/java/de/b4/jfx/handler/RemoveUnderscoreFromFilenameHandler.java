package de.b4.jfx.handler;

import de.b4.jfx.Main;
import de.b4.jfx.Messages;
import de.b4.jfx.model.Song;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

public class RemoveUnderscoreFromFilenameHandler extends SelectedHandler {
  private static RemoveUnderscoreFromFilenameHandler instance;

  public static Handler getInstance() {
    if (instance == null) {
      instance = new RemoveUnderscoreFromFilenameHandler();
    }
    return instance;
  }

  public MenuItem createMenuItem() {
    MenuItem menuItem = new MenuItem(Messages.getString("menu.removeunderscores.label"));
    menuItem.setOnAction(this::action);
    menuItem.setAccelerator(new KeyCodeCombination(KeyCode.UNDERSCORE, KeyCombination.ALT_DOWN));
    return menuItem;
  }

  public Button createToolbarButton() {
    return null;
  }

  private void action(ActionEvent actionEvent) {
    for (Song song : Main.theApp.getSelectedSongs()) {
      if (song.getFilename().contains("_")) {
        song.setFilename(song.getFilename().replaceAll("_", " "));
        song.setDirty(true);
      }
    }
    Main.theApp.songTable.refresh();
  }
}
