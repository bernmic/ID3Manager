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
import org.kordamp.ikonli.javafx.FontIcon;

public class RemoveID3V1Handler extends SelectedHandler {
  private static RemoveID3V1Handler instance;

  public static Handler getInstance() {
    if (instance == null) {
      instance = new RemoveID3V1Handler();
    }
    return instance;
  }

  public MenuItem createMenuItem() {
    MenuItem menuItem = new MenuItem(Messages.getString("menu.removeid3v1.label"));
    menuItem.setOnAction(this::action);
    menuItem.setAccelerator(new KeyCodeCombination(KeyCode.DIGIT1, KeyCombination.ALT_DOWN));
    return menuItem;
  }

  public Button createToolbarButton() {
    return null;
  }

  private void action(ActionEvent actionEvent) {
    for (Song song : Main.theApp.getSelectedSongs()) {
      song.removeID3v1();
    }
    Main.theApp.songTable.refresh();
  }
}
