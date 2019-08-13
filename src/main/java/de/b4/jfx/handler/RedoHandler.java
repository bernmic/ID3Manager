package de.b4.jfx.handler;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import org.kordamp.ikonli.javafx.FontIcon;

public class RedoHandler extends Handler {
  private static RedoHandler instance;

  public static Handler getInstance() {
    if (instance == null) {
      instance = new RedoHandler();
    }
    return instance;
  }

  public MenuItem createMenuItem() {
    MenuItem menuItem = new MenuItem("Redo");
    menuItem.setGraphic(new FontIcon(getIconCode("fa-repeat")));
    menuItem.setOnAction(RedoHandler::action);
    menuItem.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));
    return menuItem;
  }

  public Button createToolbarButton() {
    Button button = new Button();
    button.setGraphic(new FontIcon(getIconCode("fa-repeat")));
    button.setOnAction(RedoHandler::action);
    return button;
  }

  private static void action(ActionEvent actionEvent) {
    System.out.println("Redo...");
  }
}
