package de.b4.jfx.components;

import de.b4.jfx.model.Song;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MultiSongGeneralForm extends SongForm {

  private Map<String, MultiEdit<String>> multiSongFields;
  private CheckBox coverChanged;

  private MultiSongGeneralForm() {}

  public static MultiSongGeneralForm newInstance(Dialog dialog, Song[] songs) {
    MultiSongGeneralForm grid = new MultiSongGeneralForm();
    grid.dialog = dialog;
    grid.songs = songs;
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 10, 10, 10));
    grid.multiSongFields = new HashMap<>();
    grid.multiSongFields.put(TITLE_FIELD, grid.createMultiTextField(grid, "column.title.label", 0, 0, 4, 1, false));
    grid.multiSongFields.put(ARTIST_FIELD, grid.createMultiTextField(grid, "column.artist.label", 0, 1, 4, 1, false));
    grid.multiSongFields.put(ALBUMARTIST_FIELD, grid.createMultiTextField(grid, "column.albumartist.label", 0, 2, 4, 1, false));
    grid.multiSongFields.put(ALBUM_FIELD, grid.createMultiTextField(grid, "column.album.label", 0, 3, 4, 1, false));

    grid.multiSongFields.put(GENRE_FIELD, grid.createMultiTextField(grid, "column.genre.label", 0, 4, 1, 1, false));
    grid.multiSongFields.put(YEAR_FIELD, grid.createMultiTextField(grid, "column.year.label", 3, 4, 1, 1, false));
    grid.multiSongFields.put(TRACK_FIELD, grid.createMultiTextField(grid, "column.track.label", 0, 5, 1, 1, false));
    grid.multiSongFields.put(CD_FIELD, grid.createMultiTextField(grid, "column.media.label", 3, 5, 1, 1, false));

    Separator separator = new Separator();
    GridPane.setConstraints(separator,0, 6, 6, 1);
    grid.add(separator, 0, 6);

    grid.multiSongFields.put(FILENAME_FIELD, grid.createMultiTextField(grid, "column.filename.label", 0, 7, 4, 1, true));
    grid.multiSongFields.put(DURATION_FIELD, grid.createMultiTextField(grid, "column.duration.label", 0, 8, 1, 1, true));
    grid.multiSongFields.put(BPM_FIELD, grid.createMultiTextField(grid, "column.bpm.label", 0, 9, 1, 1, true));
    grid.multiSongFields.put(BITRATE_FIELD, grid.createMultiTextField(grid, "column.bitrate.label", 0, 10, 1, 1, true));
    grid.multiSongFields.put(SAMPLERATE_FIELD, grid.createMultiTextField(grid, "column.samplerate.label", 0, 11, 1, 1, true));

    grid.add(grid.createCover(3, 8), 3, 8);
    grid.coverChanged = new CheckBox();
    grid.add(grid.coverChanged, 5, 8);
    //grid.setGridLinesVisible(true);

    return grid;
  }

  public void fillForm() {
    fillDistinctString(TITLE_FIELD, Song::getTitle);
    fillDistinctString(ARTIST_FIELD, Song::getArtist);
    fillDistinctString(ALBUMARTIST_FIELD, Song::getAlbumArtist);
    fillDistinctString(ALBUM_FIELD, Song::getAlbum);

    fillDistinctString(GENRE_FIELD, Song::getGenre);
    fillDistinctString(YEAR_FIELD, Song::getYear);
    fillDistinctString(TRACK_FIELD, Song::getTrack);
    fillDistinctString(CD_FIELD, Song::getCD);

    int h = Arrays.hashCode(songs[0].getCoverData());
    Image img = songs[0].getCover();
    for (int i = 1; i < songs.length; i++) {
      if (Arrays.hashCode(songs[i].getCoverData()) != h) {
        img = null;
        break;
      }
    }
    cover.setImage(img == null ? NOCOVER_IMAGE : img);
  }

  private void fillDistinctString(String fieldName, StringAttribute attribute) {
    MultiEdit multiEdit = multiSongFields.get(fieldName);

    multiEdit.setItems(Arrays.asList(songs).stream().map(attribute::getAttribute).collect(Collectors.toSet()));
  }

  public void saveForm() {
    for (Song song : songs) {
      if (multiSongFields.get(TITLE_FIELD).hasChanged() &&
              !song.getTitle().equals(multiSongFields.get(TITLE_FIELD).getComboBox().getEditor().getText())
      ) {
        song.setTitle(multiSongFields.get(TITLE_FIELD).getComboBox().getEditor().getText());
        song.setDirty(true);
      }

      if (multiSongFields.get(ARTIST_FIELD).hasChanged() &&
              !song.getArtist().equals(multiSongFields.get(ARTIST_FIELD).getComboBox().getEditor().getText())
      ) {
        song.setArtist(multiSongFields.get(ARTIST_FIELD).getComboBox().getEditor().getText());
        song.setDirty(true);
      }

      if (multiSongFields.get(ALBUM_FIELD).hasChanged() &&
              !song.getAlbum().equals(multiSongFields.get(ALBUM_FIELD).getComboBox().getEditor().getText())
      ) {
        song.setAlbum(multiSongFields.get(ALBUM_FIELD).getComboBox().getEditor().getText());
        song.setDirty(true);
      }

      if (multiSongFields.get(ALBUMARTIST_FIELD).hasChanged() &&
              !song.getAlbumArtist().equals(multiSongFields.get(ALBUMARTIST_FIELD).getComboBox().getEditor().getText())
      ) {
        song.setAlbumArtist(multiSongFields.get(ALBUMARTIST_FIELD).getComboBox().getEditor().getText());
        song.setDirty(true);
      }

      if (multiSongFields.get(GENRE_FIELD).hasChanged() &&
              !song.getGenre().equals(multiSongFields.get(GENRE_FIELD).getComboBox().getEditor().getText())
      ) {
        song.setGenre(multiSongFields.get(GENRE_FIELD).getComboBox().getEditor().getText());
        song.setDirty(true);
      }

      if (multiSongFields.get(YEAR_FIELD).hasChanged() &&
              !song.getYear().equals(multiSongFields.get(YEAR_FIELD).getComboBox().getEditor().getText())
      ) {
        song.setYear(multiSongFields.get(YEAR_FIELD).getComboBox().getEditor().getText());
        song.setDirty(true);
      }

      if (multiSongFields.get(TRACK_FIELD).hasChanged() &&
              !song.getTrack().equals(multiSongFields.get(TRACK_FIELD).getComboBox().getEditor().getText())
      ) {
        song.setTrack(multiSongFields.get(TRACK_FIELD).getComboBox().getEditor().getText());
        song.setDirty(true);
      }

      if (multiSongFields.get(CD_FIELD).hasChanged() &&
              !song.getCD().equals(multiSongFields.get(CD_FIELD).getComboBox().getEditor().getText())
      ) {
        song.setCD(multiSongFields.get(CD_FIELD).getComboBox().getEditor().getText());
        song.setDirty(true);
      }
    }
  }

  @FunctionalInterface
  public interface StringAttribute {
    String getAttribute(Song song);
  }
}
