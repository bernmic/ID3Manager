package de.b4.jfx.handler;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import org.kordamp.ikonli.javafx.FontIcon;

public class UndoHandler extends Handler {
  private static UndoHandler instance;

  public static Handler getInstance() {
    if (instance == null) {
      instance = new UndoHandler();
    }
    return instance;
  }

  public MenuItem createMenuItem() {
    MenuItem menuItem = new MenuItem("Undo");
    menuItem.setGraphic(new FontIcon(getIconCode("fa-undo")));
    menuItem.setOnAction(UndoHandler::action);
    menuItem.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
    return menuItem;
  }

  public Button createToolbarButton() {
    Button button = new Button();
    button.setGraphic(new FontIcon(getIconCode("fa-undo")));
    button.setOnAction(UndoHandler::action);
    return button;
  }

  private static void action(ActionEvent actionEvent) {
    System.out.println("Undo...");
  }
}
