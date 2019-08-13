package de.b4.jfx.handler;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import org.kordamp.ikonli.javafx.FontIcon;

public class RenameHandler extends Handler {
  private static RenameHandler instance;

  public static Handler getInstance() {
    if (instance == null) {
      instance = new RenameHandler();
    }
    return instance;
  }

  public MenuItem createMenuItem() {
    MenuItem menuItem = new MenuItem("Rename");
    menuItem.setGraphic(new FontIcon(getIconCode("fa-exchange")));
    menuItem.setOnAction(RenameHandler::action);
    menuItem.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN));
    return menuItem;
  }

  public Button createToolbarButton() {
    Button button = new Button();
    button.setGraphic(new FontIcon(getIconCode("fa-exchange")));
    button.setOnAction(RenameHandler::action);
    return button;
  }

  private static void action(ActionEvent actionEvent) {
    System.out.println("Rename...");
  }
}
