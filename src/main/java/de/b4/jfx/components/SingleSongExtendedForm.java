package de.b4.jfx.components;

import de.b4.jfx.model.Song;
import javafx.geometry.Insets;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.Map;

public class SingleSongExtendedForm extends SongForm {

  private Map<String, TextField> singleSongFields;

  private SingleSongExtendedForm() {
  }

  public static SingleSongExtendedForm newInstance(Dialog dialog, Song[] songs) {
    SingleSongExtendedForm grid = new SingleSongExtendedForm();
    grid.dialog = dialog;
    grid.songs = songs;
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));

    grid.singleSongFields = new HashMap<>();
    grid.singleSongFields.put(ORIGINALARTIST_FIELD, grid.createTextField(grid, "column.originalartist.label", 0, 0, 1, 1, false));
    grid.singleSongFields.put(COMPOSER_FIELD, grid.createTextField(grid, "column.composer.label", 0, 1, 1, 1, false));
    grid.singleSongFields.put(PUBLISHER_FIELD, grid.createTextField(grid, "column.publisher.label", 0, 2, 1, 1, false));
    grid.singleSongFields.put(COPYRIGHT_FIELD, grid.createTextField(grid, "column.copyright.label", 0, 3, 1, 1, false));

    grid.singleSongFields.put(ENCODER_FIELD, grid.createTextField(grid, "column.encoder.label", 0, 4, 1, 1, false));
    grid.singleSongFields.put(GROUPING_FIELD, grid.createTextField(grid, "column.grouping.label", 0, 5, 1, 1, false));

    return grid;
  }

  @Override
  public void fillForm() {
    Song song = songs[0];
    singleSongFields.get(ORIGINALARTIST_FIELD).setText(song.getOriginalArtist());
    singleSongFields.get(COMPOSER_FIELD).setText(song.getComposer());
    singleSongFields.get(PUBLISHER_FIELD).setText(song.getPublisher());
    singleSongFields.get(COPYRIGHT_FIELD).setText(song.getCopyright());
    singleSongFields.get(ENCODER_FIELD).setText(song.getEncoder());
    singleSongFields.get(GROUPING_FIELD).setText(song.getGrouping());
  }

  @Override
  public void saveForm() {
    Song song = songs[0];
    if (song.getOriginalArtist() != null && !song.getOriginalArtist().equals(singleSongFields.get(ORIGINALARTIST_FIELD).getText())) {
      song.setOriginalArtist(singleSongFields.get(ORIGINALARTIST_FIELD).getText());
      song.setDirty(true);
    }
    if (song.getComposer() != null && !song.getComposer().equals(singleSongFields.get(COMPOSER_FIELD).getText())) {
      song.setComposer(singleSongFields.get(COMPOSER_FIELD).getText());
      song.setDirty(true);
    }
    if (song.getPublisher() != null && !song.getPublisher().equals(singleSongFields.get(PUBLISHER_FIELD).getText())) {
      song.setPublisher(singleSongFields.get(PUBLISHER_FIELD).getText());
      song.setDirty(true);
    }
    if (song.getCopyright() != null && !song.getCopyright().equals(singleSongFields.get(COPYRIGHT_FIELD).getText())) {
      song.setCopyright(singleSongFields.get(COPYRIGHT_FIELD).getText());
      song.setDirty(true);
    }
    if (song.getEncoder() != null && !song.getEncoder().equals(singleSongFields.get(ENCODER_FIELD).getText())) {
      song.setEncoder(singleSongFields.get(ENCODER_FIELD).getText());
      song.setDirty(true);
    }
    if (song.getGrouping() != null && !song.getGrouping().equals(singleSongFields.get(GROUPING_FIELD).getText())) {
      song.setGrouping(singleSongFields.get(GROUPING_FIELD).getText());
      song.setDirty(true);
    }
  }
}
