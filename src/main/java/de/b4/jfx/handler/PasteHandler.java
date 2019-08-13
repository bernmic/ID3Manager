package de.b4.jfx.handler;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import org.kordamp.ikonli.javafx.FontIcon;

public class PasteHandler extends Handler {
  private static PasteHandler instance;

  public static Handler getInstance() {
    if (instance == null) {
      instance = new PasteHandler();
    }
    return instance;
  }

  MenuItem createMenuItem() {
    MenuItem menuItem = new MenuItem("Paste");
    menuItem.setGraphic(new FontIcon(getIconCode("fa-paste")));
    menuItem.setOnAction(PasteHandler::action);
    menuItem.setAccelerator(new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN));
    return menuItem;
  }

  Button createToolbarButton() {
    Button button = new Button();
    button.setGraphic(new FontIcon(getIconCode("fa-paste")));
    button.setOnAction(PasteHandler::action);
    return button;
  }

  private static void action(ActionEvent actionEvent) {
    System.out.println("Paste...");
  }
}
