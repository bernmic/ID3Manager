package de.b4.jfx.handler;

import de.b4.jfx.Main;
import de.b4.jfx.dialogs.ParseDialog;
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

public class ParseHandler extends SelectedHandler {
  private static ParseHandler instance;

  public static Handler getInstance() {
    if (instance == null) {
      instance = new ParseHandler();
    }
    return instance;
  }

  public MenuItem createMenuItem() {
    MenuItem menuItem = new MenuItem("Parse filename");
    menuItem.setGraphic(new FontIcon(getIconCode("fa-code")));
    menuItem.setOnAction(ParseHandler::action);
    menuItem.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCombination.ALT_DOWN));
    return menuItem;
  }

  public Button createToolbarButton() {
    Button button = new Button();
    button.setGraphic(new FontIcon(getIconCode("fa-code")));
    button.setOnAction(ParseHandler::action);
    button.setTooltip(new Tooltip("Parse filename for ID3 tags"));
    return button;
  }

  private static void action(ActionEvent actionEvent) {
    Song[] songs = Main.theApp.getSelectedSongs();
    if (songs.length > 0) {
      Optional<Song[]> result = ParseDialog.newInstance(songs).showAndWait();
      if (result.isPresent()) {
        Main.theApp.songTable.refresh();
      }
    }
  }
}
