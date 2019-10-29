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

public class PasteHandler extends SelectedHandler {
  private static PasteHandler instance;

  public static Handler getInstance() {
    if (instance == null) {
      instance = new PasteHandler();
    }
    return instance;
  }

  MenuItem createMenuItem() {
    MenuItem menuItem = new MenuItem(Messages.getString("menu.paste.label"));
    menuItem.setGraphic(new FontIcon(getIconCode("fa-paste")));
    menuItem.setOnAction(this::action);
    menuItem.setAccelerator(new KeyCodeCombination(KeyCode.V, KeyCombination.SHORTCUT_DOWN));
    return menuItem;
  }

  Button createToolbarButton() {
    Button button = new Button();
    button.setGraphic(new FontIcon(getIconCode("fa-paste")));
    button.setOnAction(this::action);
    button.setTooltip(new Tooltip(Messages.getString("menu.paste.tooltip")));
    return button;
  }

  private void action(ActionEvent actionEvent) {
    System.out.println("Paste...");
  }
}
