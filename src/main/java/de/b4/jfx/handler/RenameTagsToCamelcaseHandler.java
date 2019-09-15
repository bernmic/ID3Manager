package de.b4.jfx.handler;

import de.b4.jfx.Main;
import de.b4.jfx.Messages;
import de.b4.jfx.model.Song;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import org.apache.commons.lang3.text.WordUtils;

public class RenameTagsToCamelcaseHandler extends SelectedHandler {
  private final static char[] FILENAME_SEPARATORS = new char[] { ' ', '-', '_', '(', ')' };
  private static RenameTagsToCamelcaseHandler instance;

  public static Handler getInstance() {
    if (instance == null) {
      instance = new RenameTagsToCamelcaseHandler();
    }
    return instance;
  }

  public MenuItem createMenuItem() {
    MenuItem menuItem = new MenuItem(Messages.getString("menu.camelcasetags.label"));
    menuItem.setOnAction(this::action);
    //menuItem.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.ALT_DOWN));
    return menuItem;
  }

  public Button createToolbarButton() {
    return null;
  }

  private void action(ActionEvent actionEvent) {
    for (Song song : Main.theApp.getSelectedSongs()) {
      String neu = WordUtils.capitalizeFully(song.getArtist(), FILENAME_SEPARATORS);
      if (!song.getArtist().equals(neu)) {
        song.setArtist(neu);
        song.setDirty(true);
      }
      neu = WordUtils.capitalizeFully(song.getAlbum(), FILENAME_SEPARATORS);
      if (!song.getAlbum().equals(neu)) {
        song.setAlbum(neu);
        song.setDirty(true);
      }
      neu = WordUtils.capitalizeFully(song.getTitle(), FILENAME_SEPARATORS);
      if (!song.getTitle().equals(neu)) {
        song.setTitle(neu);
        song.setDirty(true);
      }
    }
    Main.theApp.songTable.refresh();
  }
}
