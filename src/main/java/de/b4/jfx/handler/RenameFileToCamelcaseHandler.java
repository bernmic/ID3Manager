package de.b4.jfx.handler;

import de.b4.jfx.Main;
import de.b4.jfx.Messages;
import de.b4.jfx.model.Song;
import de.b4.jfx.util.StringUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

public class RenameFileToCamelcaseHandler extends SelectedHandler {
  private final static char[] FILENAME_SEPARATORS = new char[] { ' ', '-', '_', '(', ')' };
  private static RenameFileToCamelcaseHandler instance;

  public static Handler getInstance() {
    if (instance == null) {
      instance = new RenameFileToCamelcaseHandler();
    }
    return instance;
  }

  public MenuItem createMenuItem() {
    MenuItem menuItem = new MenuItem(Messages.getString("menu.camelcasefilename.label"));
    menuItem.setOnAction(this::action);
    //menuItem.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.ALT_DOWN));
    return menuItem;
  }

  public Button createToolbarButton() {
    return null;
  }

  private void action(ActionEvent actionEvent) {
    for (Song song : Main.theApp.getSelectedSongs()) {
      String neu = StringUtils.capitalizeFully(song.getFilename(), FILENAME_SEPARATORS);
      if (!song.getFilename().equals(neu)) {
        song.setFilename(neu);
        song.setDirty(true);
      }
    }
    Main.theApp.songTable.refresh();
  }
}
