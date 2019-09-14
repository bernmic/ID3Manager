package de.b4.jfx.handler;

import de.b4.jfx.Main;
import de.b4.jfx.dialogs.ID3Dialog;
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

public class SaveHandler extends Handler {
  private static SaveHandler instance;

  public static Handler getInstance() {
    if (instance == null) {
      instance = new SaveHandler();
    }
    return instance;
  }

  public MenuItem createMenuItem() {
    MenuItem menuItem = new MenuItem("Save");
    menuItem.setGraphic(new FontIcon(getIconCode("fa-save")));
    menuItem.setOnAction(SaveHandler::action);
    menuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN));
    return menuItem;
  }

  public Button createToolbarButton() {
    Button button = new Button();
    button.setGraphic(new FontIcon(getIconCode("fa-save")));
    button.setOnAction(SaveHandler::action);
    button.setTooltip(new Tooltip("Save all changes"));
    return button;
  }

  private static void action(ActionEvent actionEvent) {
    Main.theApp.songTable.getItems().forEach(s -> {
      if (s.isDirty()) {
        System.out.println("Save " + s.getPath() + s.getFilename());
        s.save();
      }
    });
  }
}
