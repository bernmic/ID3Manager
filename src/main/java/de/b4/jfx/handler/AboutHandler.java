package de.b4.jfx.handler;

import de.b4.jfx.dialogs.AboutDialog;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

public class AboutHandler extends Handler {
  private static AboutHandler instance;

  public static Handler getInstance() {
    if (instance == null) {
      instance = new AboutHandler();
    }
    return instance;
  }

  @Override
  Button createToolbarButton() {
    return null;
  }

  public MenuItem createMenuItem() {
    MenuItem menuItem = new MenuItem("About");
    menuItem.setOnAction(this::action);
    return menuItem;
  }


  private void action(ActionEvent actionEvent) {
    AboutDialog.newInstance().showAndWait();
  }
}

