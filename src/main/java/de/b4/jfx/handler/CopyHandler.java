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

public class CopyHandler extends SelectedHandler {
  private static CopyHandler instance;

  public static Handler getInstance() {
    if (instance == null) {
      instance = new CopyHandler();
    }
    return instance;
  }

  public MenuItem createMenuItem() {
    MenuItem menuItem = new MenuItem(Messages.getString("menu.copy.label"));
    menuItem.setGraphic(new FontIcon(getIconCode("fa-copy")));
    menuItem.setOnAction(this::action);
    menuItem.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.SHORTCUT_DOWN));
    return menuItem;
  }

  public Button createToolbarButton() {
    Button button = new Button();
    button.setGraphic(new FontIcon(getIconCode("fa-copy")));
    button.setOnAction(this::action);
    button.setTooltip(new Tooltip(Messages.getString("menu.copy.tooltip")));
    return button;
  }

  private void action(ActionEvent actionEvent) {
    System.out.println("Copy...");
  }
}
