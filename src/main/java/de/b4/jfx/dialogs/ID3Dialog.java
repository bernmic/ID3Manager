package de.b4.jfx.dialogs;

import de.b4.jfx.components.MultiEdit;
import de.b4.jfx.model.Song;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ID3Dialog extends Dialog<Song[]> {
  private final static String TITLE_FIELD = "title";
  private final static String ARTIST_FIELD = "artist";
  private final static String ALBUMARTIST_FIELD = "albunartist";
  private final static String ALBUM_FIELD = "album";
  private final static String GENRE_FIELD = "genre";
  private final static String YEAR_FIELD = "year";
  private final static String TRACK_FIELD = "track";
  private final static String CD_FIELD = "cd";
  private final static String FILENAME_FIELD = "filename";
  private final static String DURATION_FIELD = "duration";
  private final static String BPM_FIELD = "bpm";
  private final static String BITRATE_FIELD = "bitrate";
  private final static String SAMPLERATE_FIELD = "samplerate";

  private Song[] songs;
  private Map<String,TextField> singleSongFields;
  private Map<String, MultiEdit<String>> multiSongFields;
  private ImageView cover;
  private CheckBox coverChanged;

  private ID3Dialog() {}

  public static ID3Dialog newInstance(Song[] songs) {

    ID3Dialog dialog = new ID3Dialog();
    dialog.songs = songs;
    dialog.setTitle("Edit songs");
    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

    VBox vbox = new VBox(new TabPane(dialog.createGeneralTab(), dialog.createExtendedTab()));
    vbox.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
    dialog.getDialogPane().setContent(vbox);
    dialog.getDialogPane().setMinWidth(600);
    dialog.setResultConverter(param -> {
      if (param == ButtonType.OK) {
        saveFormToSongs(dialog);
        return dialog.songs;
      }
      return null;
    });
    dialog.fillFields();
    return dialog;
  }

  private Tab createGeneralTab() {
    if (songs.length > 1)
      return new Tab("General", createMultiSongGeneralForm());
    return new Tab("General", createSingleSongGeneralForm());
  }

  private GridPane createMultiSongGeneralForm() {
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 10, 10, 10));
    multiSongFields = new HashMap<>();
    multiSongFields.put(TITLE_FIELD, createMultiTextField(grid, "Title", 0, 0, 4, 1, false));
    multiSongFields.put(ARTIST_FIELD, createMultiTextField(grid, "Artist", 0, 1, 4, 1, false));
    multiSongFields.put(ALBUMARTIST_FIELD, createMultiTextField(grid, "Album Artist", 0, 2, 4, 1, false));
    multiSongFields.put(ALBUM_FIELD, createMultiTextField(grid, "Album", 0, 3, 4, 1, false));

    multiSongFields.put(GENRE_FIELD, createMultiTextField(grid, "Genre", 0, 4, 1, 1, false));
    multiSongFields.put(YEAR_FIELD, createMultiTextField(grid, "Year", 3, 4, 1, 1, false));
    multiSongFields.put(TRACK_FIELD, createMultiTextField(grid, "Track", 0, 5, 1, 1, false));
    multiSongFields.put(CD_FIELD, createMultiTextField(grid, "CD", 3, 5, 1, 1, false));

    Separator separator = new Separator();
    GridPane.setConstraints(separator,0, 6, 6, 1);
    grid.add(separator, 0, 6);

    multiSongFields.put(FILENAME_FIELD, createMultiTextField(grid, "Filename", 0, 7, 4, 1, true));
    multiSongFields.put(DURATION_FIELD, createMultiTextField(grid, "Duration", 0, 8, 1, 1, true));
    multiSongFields.put(BPM_FIELD, createMultiTextField(grid, "BPM", 0, 9, 1, 1, true));
    multiSongFields.put(BITRATE_FIELD, createMultiTextField(grid, "Bitrate", 0, 10, 1, 1, true));
    multiSongFields.put(SAMPLERATE_FIELD, createMultiTextField(grid, "Samplerate", 0, 11, 1, 1, true));

    grid.add(createCover(3, 8), 3, 8);
    coverChanged = new CheckBox();
    grid.add(coverChanged, 5, 8);
    return grid;
  }

  private MultiEdit<String> createMultiTextField(GridPane grid, String title, int col, int row, int colspan, int rowspan, boolean disabled) {
    Label label = new Label(title);
    GridPane.setConstraints(label,col, row, 1, 1);
    grid.add(label, col, row);

    MultiEdit<String> multiEdit = new MultiEdit();
    multiEdit.setDisable(disabled);
    GridPane.setHgrow(multiEdit.getComboBox(), Priority.ALWAYS);
    GridPane.setConstraints(multiEdit.getComboBox(),col + 1, row, colspan, rowspan);
    GridPane.setConstraints(multiEdit.getCheckBox(),col + 1 + colspan, row);
    grid.add(multiEdit.getComboBox(), col + 1, row);
    grid.add(multiEdit.getCheckBox(), col + 1 + colspan, row);
    return multiEdit;
  }

  private GridPane createSingleSongGeneralForm(){
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 20, 10, 10));
    singleSongFields = new HashMap<>();
    singleSongFields.put(TITLE_FIELD, createTextField(grid, "Title", 0, 0, 3, 1, false));
    singleSongFields.put(ARTIST_FIELD, createTextField(grid, "Artist", 0, 1, 3, 1, false));
    singleSongFields.put(ALBUMARTIST_FIELD, createTextField(grid, "Album Artist", 0, 2, 3, 1, false));
    singleSongFields.put(ALBUM_FIELD, createTextField(grid, "Album", 0, 3, 3, 1, false));

    singleSongFields.put(GENRE_FIELD, createTextField(grid, "Genre", 0, 4, 1, 1, false));
    singleSongFields.put(YEAR_FIELD, createTextField(grid, "Year", 2, 4, 1, 1, false));
    singleSongFields.put(TRACK_FIELD, createTextField(grid, "Track", 0, 5, 1, 1, false));
    singleSongFields.put(CD_FIELD, createTextField(grid, "CD", 2, 5, 1, 1, false));

    Separator separator = new Separator();
    GridPane.setConstraints(separator,0, 6, 4, 1);
    grid.add(separator, 0, 6);

    singleSongFields.put(FILENAME_FIELD, createTextField(grid, "Filename", 0, 7, 3, 1, true));
    singleSongFields.put(DURATION_FIELD, createTextField(grid, "Duration", 0, 8, 1, 1, true));
    singleSongFields.put(BPM_FIELD, createTextField(grid, "BPM", 0, 9, 1, 1, true));
    singleSongFields.put(BITRATE_FIELD, createTextField(grid, "Bitrate", 0, 10, 1, 1, true));
    singleSongFields.put(SAMPLERATE_FIELD, createTextField(grid, "Samplerate", 0, 11, 1, 1, true));

    grid.add(createCover(2, 8), 2, 8);
    return grid;
  }

  private ImageView createCover(int columnIndex, int rowIndex) {
    cover = new ImageView("nocover.png");
    cover.setFitHeight(200);
    cover.setFitWidth(200);
    GridPane.setConstraints(cover, columnIndex, rowIndex, 2, 4);
    cover.setOnMouseClicked(e -> {
      if (e.getClickCount() == 2) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose cover image");
        fileChooser.setSelectedExtensionFilter(
                new FileChooser.ExtensionFilter(
                        "Images",
                        "*.jpeg",
                        "*.jpg",
                        "*.gif",
                        "*.png"

                ));
        File f = fileChooser.showOpenDialog(this.getOwner());
        if (f != null) {
          try {
            Image newImage = new Image(new FileInputStream(f));
            cover.setImage(newImage);
            for (Song song : songs) {
              song.setCover(f.getAbsolutePath());
              song.setDirty(true);
            }
          } catch (FileNotFoundException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("File not found");
            alert.setContentText("The selected file was not found!");
            alert.showAndWait();;
          }
        }
      }
    });
    return cover;
  }

  private TextField createTextField(GridPane grid, String title, int col, int row, int colspan, int rowspan, boolean disabled) {
    Label label = new Label(title);
    GridPane.setConstraints(label,col, row, 1, 1);
    grid.add(label, col, row);
    TextField textField = new TextField();
    textField.setDisable(disabled);
    GridPane.setHgrow(textField, Priority.ALWAYS);
    GridPane.setConstraints(textField,col + 1, row, colspan, rowspan);
    grid.add(textField, col + 1, row);

    return textField;
  }

  private Tab createExtendedTab() {
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));

    grid.add(new Label("MUH: " + songs.length), 0, 0);

    return new Tab("Extended", grid);
  }

  private void fillFields() {
    if (songs.length == 1) {
      Song song = songs[0];
      singleSongFields.get(TITLE_FIELD).setText(song.getTitle());
      singleSongFields.get(ARTIST_FIELD).setText(song.getArtist());
      singleSongFields.get(ALBUMARTIST_FIELD).setText(song.getAlbumArtist());
      singleSongFields.get(ALBUM_FIELD).setText(song.getAlbum());

      singleSongFields.get(GENRE_FIELD).setText(song.getGenre());
      singleSongFields.get(YEAR_FIELD).setText(song.getYear());
      singleSongFields.get(TRACK_FIELD).setText(song.getTrack());
      singleSongFields.get(CD_FIELD).setText(song.getCD());

      singleSongFields.get(FILENAME_FIELD).setText(song.getPath() + song.getFilename());
      singleSongFields.get(DURATION_FIELD).setText(song.getLength());
      singleSongFields.get(BPM_FIELD).setText(song.getBPM());
      singleSongFields.get(BITRATE_FIELD).setText(String.valueOf(song.getBitrate()));
      singleSongFields.get(SAMPLERATE_FIELD).setText(String.valueOf(song.getSamplingrate()));

      cover.setImage(song.getCover());
    }
    else {
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
      cover.setImage(img);
    }
  }

  @FunctionalInterface
  public interface StringAttribute {
    String getAttribute(Song song);
  }

  private void fillDistinctString(String fieldName, StringAttribute attribute) {
    MultiEdit multiEdit = multiSongFields.get(fieldName);

    multiEdit.setItems(Arrays.asList(songs).stream().map(attribute::getAttribute).collect(Collectors.toSet()));
  }

  private static void saveFormToSongs(ID3Dialog dialog) {
    if (dialog.songs.length == 1) {
      Song song = dialog.songs[0];
      if (!song.getTitle().equals(dialog.singleSongFields.get(TITLE_FIELD).getText())) {
        song.setTitle(dialog.singleSongFields.get(TITLE_FIELD).getText());
        song.setDirty(true);
      }
      if (!song.getArtist().equals(dialog.singleSongFields.get(ARTIST_FIELD).getText())) {
        song.setArtist(dialog.singleSongFields.get(ARTIST_FIELD).getText());
        song.setDirty(true);
      }
      if (!song.getAlbumArtist().equals(dialog.singleSongFields.get(ALBUMARTIST_FIELD).getText())) {
        song.setAlbumArtist(dialog.singleSongFields.get(ALBUMARTIST_FIELD).getText());
        song.setDirty(true);
      }
      if (!song.getAlbum().equals(dialog.singleSongFields.get(ALBUM_FIELD).getText())) {
        song.setAlbum(dialog.singleSongFields.get(ALBUM_FIELD).getText());
        song.setDirty(true);
      }
      if (!song.getGenre().equals(dialog.singleSongFields.get(GENRE_FIELD).getText())) {
        song.setGenre(dialog.singleSongFields.get(GENRE_FIELD).getText());
        song.setDirty(true);
      }
      if (!song.getYear().equals(dialog.singleSongFields.get(YEAR_FIELD).getText())) {
        song.setYear(dialog.singleSongFields.get(YEAR_FIELD).getText());
        song.setDirty(true);
      }
      if (!song.getTrack().equals(dialog.singleSongFields.get(TRACK_FIELD).getText())) {
        song.setTrack(dialog.singleSongFields.get(TRACK_FIELD).getText());
        song.setDirty(true);
      }
      if (!song.getCD().equals(dialog.singleSongFields.get(CD_FIELD).getText())) {
        song.setCD(dialog.singleSongFields.get(CD_FIELD).getText());
        song.setDirty(true);
      }
    }
    else {
      for (Song song : dialog.songs) {
        if (dialog.multiSongFields.get(TITLE_FIELD).hasChanged() &&
                !song.getTitle().equals(dialog.multiSongFields.get(TITLE_FIELD).getComboBox().getEditor().getText())
        ) {
          song.setTitle(dialog.multiSongFields.get(TITLE_FIELD).getComboBox().getEditor().getText());
          song.setDirty(true);
        }

        if (dialog.multiSongFields.get(ARTIST_FIELD).hasChanged() &&
                !song.getArtist().equals(dialog.multiSongFields.get(ARTIST_FIELD).getComboBox().getEditor().getText())
        ) {
          song.setArtist(dialog.multiSongFields.get(ARTIST_FIELD).getComboBox().getEditor().getText());
          song.setDirty(true);
        }

        if (dialog.multiSongFields.get(ALBUM_FIELD).hasChanged() &&
                !song.getAlbum().equals(dialog.multiSongFields.get(ALBUM_FIELD).getComboBox().getEditor().getText())
        ) {
          song.setAlbum(dialog.multiSongFields.get(ALBUM_FIELD).getComboBox().getEditor().getText());
          song.setDirty(true);
        }

        if (dialog.multiSongFields.get(ALBUMARTIST_FIELD).hasChanged() &&
                !song.getAlbumArtist().equals(dialog.multiSongFields.get(ALBUMARTIST_FIELD).getComboBox().getEditor().getText())
        ) {
          song.setAlbumArtist(dialog.multiSongFields.get(ALBUMARTIST_FIELD).getComboBox().getEditor().getText());
          song.setDirty(true);
        }

        if (dialog.multiSongFields.get(GENRE_FIELD).hasChanged() &&
                !song.getGenre().equals(dialog.multiSongFields.get(GENRE_FIELD).getComboBox().getEditor().getText())
        ) {
          song.setGenre(dialog.multiSongFields.get(GENRE_FIELD).getComboBox().getEditor().getText());
          song.setDirty(true);
        }

        if (dialog.multiSongFields.get(YEAR_FIELD).hasChanged() &&
                !song.getYear().equals(dialog.multiSongFields.get(YEAR_FIELD).getComboBox().getEditor().getText())
        ) {
          song.setYear(dialog.multiSongFields.get(YEAR_FIELD).getComboBox().getEditor().getText());
          song.setDirty(true);
        }

        if (dialog.multiSongFields.get(TRACK_FIELD).hasChanged() &&
                !song.getTrack().equals(dialog.multiSongFields.get(TRACK_FIELD).getComboBox().getEditor().getText())
        ) {
          song.setTrack(dialog.multiSongFields.get(TRACK_FIELD).getComboBox().getEditor().getText());
          song.setDirty(true);
        }

        if (dialog.multiSongFields.get(CD_FIELD).hasChanged() &&
                !song.getCD().equals(dialog.multiSongFields.get(CD_FIELD).getComboBox().getEditor().getText())
        ) {
          song.setCD(dialog.multiSongFields.get(CD_FIELD).getComboBox().getEditor().getText());
          song.setDirty(true);
        }
      }
    }
  }
}
