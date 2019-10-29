package de.b4.jfx.components;

import de.b4.jfx.model.Song;
import javafx.geometry.Insets;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Map;

public class MultiSongExtendedForm extends SongForm {

  private Map<String, TextField> multiSongFields;

  private MultiSongExtendedForm() {
  }

  public static MultiSongExtendedForm newInstance(Dialog dialog, Song[] songs) {
    MultiSongExtendedForm grid = new MultiSongExtendedForm();
    grid.dialog = dialog;
    grid.songs = songs;
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));

    grid.add(new Label("MÄH: " + songs.length), 0, 0);

    return grid;
  }

  @Override
  public void fillForm() {

  }

  @Override
  public void saveForm() {

  }
}
