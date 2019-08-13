package de.b4.jfx.handler;

import de.b4.jfx.Main;
import de.b4.jfx.dialogs.ID3Dialog;
import de.b4.jfx.model.Song;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Optional;

public class EditHandler extends Handler {
  private static EditHandler instance;

  public static Handler getInstance() {
    if (instance == null) {
      instance = new EditHandler();
    }
    return instance;
  }

  public MenuItem createMenuItem() {
    MenuItem menuItem = new MenuItem("Edit");
    menuItem.setGraphic(new FontIcon(getIconCode("fa-edit")));
    menuItem.setOnAction(EditHandler::action);
    menuItem.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));
    return menuItem;
  }

  public Button createToolbarButton() {
    Button button = new Button();
    button.setGraphic(new FontIcon(getIconCode("fa-edit")));
    button.setOnAction(EditHandler::action);
    return button;
  }

  private static void action(ActionEvent actionEvent) {
    Song[] songs = Main.theApp.getSelectedSongs();
    if (songs.length > 0) {
      Optional<Song[]> result = ID3Dialog.newInstance(songs).showAndWait();
      if (result.isPresent()) {
        System.out.println(result.get());
      }
    }
  }
}
