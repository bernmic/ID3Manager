package de.b4.jfx.handler;

import de.b4.jfx.Messages;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

public class QuitHandler extends Handler {
  private static QuitHandler instance;

  public static Handler getInstance() {
    if (instance == null) {
      instance = new QuitHandler();
    }
    return instance;
  }

  @Override
  Button createToolbarButton() {
    return null;
  }

  public MenuItem createMenuItem() {
    MenuItem menuItem = new MenuItem(Messages.getString("menu.quit.label"));
    menuItem.setOnAction(this::action);
    return menuItem;
  }

  private void action(ActionEvent actionEvent) {
    Platform.exit();
  }
}

