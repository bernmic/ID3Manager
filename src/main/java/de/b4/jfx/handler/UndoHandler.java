package de.b4.jfx.handler;

import de.b4.jfx.Messages;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
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
    MenuItem menuItem = new MenuItem(Messages.getString("menu.undo.label"));
    menuItem.setGraphic(new FontIcon(getIconCode("fa-undo")));
    menuItem.setOnAction(this::action);
    menuItem.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.SHORTCUT_DOWN));
    return menuItem;
  }

  public Button createToolbarButton() {
    Button button = new Button();
    button.setGraphic(new FontIcon(getIconCode("fa-undo")));
    button.setOnAction(this::action);
    button.setTooltip(new Tooltip(Messages.getString("menu.undo.tooltip")));
    return button;
  }

  private void action(ActionEvent actionEvent) {
    System.out.println("Undo...");
  }
}
