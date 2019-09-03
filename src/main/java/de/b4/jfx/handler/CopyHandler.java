package de.b4.jfx.handler;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import org.kordamp.ikonli.javafx.FontIcon;

public class CopyHandler extends Handler {
  private static CopyHandler instance;

  public static Handler getInstance() {
    if (instance == null) {
      instance = new CopyHandler();
    }
    return instance;
  }

  public MenuItem createMenuItem() {
    MenuItem menuItem = new MenuItem("Copy");
    menuItem.setGraphic(new FontIcon(getIconCode("fa-copy")));
    menuItem.setOnAction(CopyHandler::action);
    menuItem.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.SHORTCUT_DOWN));
    return menuItem;
  }

  public Button createToolbarButton() {
    Button button = new Button();
    button.setGraphic(new FontIcon(getIconCode("fa-copy")));
    button.setOnAction(CopyHandler::action);
    return button;
  }

  private static void action(ActionEvent actionEvent) {
    System.out.println("Copy...");
  }
}
