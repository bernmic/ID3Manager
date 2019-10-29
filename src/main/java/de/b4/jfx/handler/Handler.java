package de.b4.jfx.handler;

import de.b4.jfx.Main;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

public abstract class Handler {
  final MenuItem menuItem;
  final Button toolbarButton;

  Handler() {
    menuItem = createMenuItem();
    toolbarButton = createToolbarButton();
  }

  abstract Button createToolbarButton();
  abstract  MenuItem createMenuItem();

  public MenuItem getMenuItem() {
    return menuItem;
  }

  public Button getToolbarButton() {
    return toolbarButton;
  }

  String getIconCode(String code) {
    return Main.theApp.getConfiguration().getIconCode(code);
  }

  public void fireEvent() {
    if (menuItem != null) {
      menuItem.fire();
    } else if (toolbarButton != null) {
      toolbarButton.fire();
    }
  }
}
