package de.b4.jfx.handler;

import de.b4.jfx.Main;
import de.b4.jfx.Messages;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import org.kordamp.ikonli.javafx.FontIcon;

public class SelectAllHandler extends Handler {
  private static SelectAllHandler instance;

  public static Handler getInstance() {
    if (instance == null) {
      instance = new SelectAllHandler();
    }
    return instance;
  }

  public MenuItem createMenuItem() {
    MenuItem menuItem = new MenuItem(Messages.getString("menu.selectall.label"));
    menuItem.setOnAction(this::action);
    menuItem.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.SHORTCUT_DOWN));
    return menuItem;
  }

  public Button createToolbarButton() {
    return null;
  }

  private void action(ActionEvent actionEvent) {
    Main.theApp.songTable.getSelectionModel().selectAll();
    Main.theApp.songTable.requestFocus();
    //System.out.println("Select all...");
  }
}
