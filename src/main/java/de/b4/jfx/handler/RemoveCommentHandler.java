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

public class RemoveCommentHandler extends SelectedHandler {
  private static RemoveCommentHandler instance;

  public static Handler getInstance() {
    if (instance == null) {
      instance = new RemoveCommentHandler();
    }
    return instance;
  }

  public MenuItem createMenuItem() {
    MenuItem menuItem = new MenuItem(Messages.getString("menu.removecomments.label"));
    menuItem.setOnAction(this::action);
    menuItem.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.ALT_DOWN));
    return menuItem;
  }

  public Button createToolbarButton() {
    return null;
  }

  private void action(ActionEvent actionEvent) {
    for (Song song : Main.theApp.getSelectedSongs()) {
      if (song.getComments() != null) {
        song.setComments(null);
        song.setDirty(true);
      }
    }
    Main.theApp.songTable.refresh();
  }
}
