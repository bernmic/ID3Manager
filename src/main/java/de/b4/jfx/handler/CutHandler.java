package de.b4.jfx.handler;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import org.kordamp.ikonli.javafx.FontIcon;

public class CutHandler extends SelectedHandler {
  private static CutHandler instance;

  public static Handler getInstance() {
    if (instance == null) {
      instance = new CutHandler();
    }
    return instance;
  }

  public MenuItem createMenuItem() {
    MenuItem menuItem = new MenuItem("Cut");
    menuItem.setGraphic(new FontIcon(getIconCode("fa-cut")));
    menuItem.setOnAction(this::action);
    menuItem.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.SHORTCUT_DOWN));
    return menuItem;
  }

  public Button createToolbarButton() {
    Button button = new Button();
    button.setGraphic(new FontIcon(getIconCode("fa-cut")));
    button.setOnAction(this::action);
    button.setTooltip(new Tooltip("Cut selected items"));

    return button;
  }

  private void action(ActionEvent actionEvent) {
    System.out.println("Cut...");
  }
}
