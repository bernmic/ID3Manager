package de.b4.jfx.handler;

import de.b4.jfx.Main;
import de.b4.jfx.Messages;
import de.b4.jfx.dialogs.RenameDialog;
import de.b4.jfx.model.Song;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Optional;

public class RenameHandler extends SelectedHandler {
  private static RenameHandler instance;

  public static Handler getInstance() {
    if (instance == null) {
      instance = new RenameHandler();
    }
    return instance;
  }

  public MenuItem createMenuItem() {
    MenuItem menuItem = new MenuItem(Messages.getString("menu.rename.label"));
    menuItem.setGraphic(new FontIcon(getIconCode("fa-exchange")));
    menuItem.setOnAction(this::action);
    menuItem.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.ALT_DOWN));
    return menuItem;
  }

  public Button createToolbarButton() {
    Button button = new Button();
    button.setGraphic(new FontIcon(getIconCode("fa-exchange")));
    button.setOnAction(this::action);
    button.setTooltip(new Tooltip(Messages.getString("menu.rename.tooltip")));
    return button;
  }

  private void action(ActionEvent actionEvent) {
    Song[] songs = Main.theApp.getSelectedSongs();
    if (songs.length > 0) {
      Optional<Song[]> result = RenameDialog.newInstance(songs).showAndWait();
      if (result.isPresent()) {
        Main.theApp.songTable.refresh();
      }
    }
  }
}
