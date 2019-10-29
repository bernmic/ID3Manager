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

public class NumberTracksHandler extends SelectedHandler {
  private static NumberTracksHandler instance;

  public static Handler getInstance() {
    if (instance == null) {
      instance = new NumberTracksHandler();
    }
    return instance;
  }

  public MenuItem createMenuItem() {
    MenuItem menuItem = new MenuItem(Messages.getString("menu.numbertracks.label"));
    menuItem.setOnAction(this::action);
    menuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.ALT_DOWN));
    return menuItem;
  }

  public Button createToolbarButton() {
    return null;
  }

  private void action(ActionEvent actionEvent) {
    int cnt = 1;
    for (Song song : Main.theApp.getSelectedSongs()) {
      song.setTrack(String.format("%02d", cnt++));
      song.setDirty(true);
    }
    Main.theApp.songTable.refresh();
  }
}
