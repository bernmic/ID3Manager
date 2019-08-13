package de.b4.jfx.dialogs;

import de.b4.jfx.model.Song;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class ID3Dialog extends Dialog<Song[]> {
  private Song[] songs;
  private TextField titleField;
  private TextField artistField;
  private TextField albumartistField;
  private TextField albumField;
  private TextField genreField;
  private TextField yearField;
  private TextField trackField;
  private TextField cdField;
  private TextField pathField;
  private TextField durationField;
  private TextField bpmField;
  private TextField bitrateField;
  private TextField samplerateField;
  private ImageView cover;

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
        return dialog.songs;
      }
      return null;
    });
    dialog.fillFields();
    return dialog;
  }

  private Tab createGeneralTab() {
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 20, 10, 10));

    titleField = createTextField(grid, "Title", 0, 0, 3, 1, false);
    artistField = createTextField(grid, "Artist", 0, 2, 3, 1, false);
    albumartistField = createTextField(grid, "Album Artist", 0, 2, 3, 1, false);
    albumField = createTextField(grid, "Album", 0, 3, 3, 1, false);

    genreField = createTextField(grid, "Genre", 0, 4, 1, 1, false);
    yearField = createTextField(grid, "Year", 2, 4, 1, 1, false);
    trackField = createTextField(grid, "Track", 0, 5, 1, 1, false);
    cdField = createTextField(grid, "CD", 2, 5, 1, 1, false);

    Separator separator = new Separator();
    GridPane.setConstraints(separator,0, 6, 4, 1);
    grid.add(separator, 0, 6);

    pathField = createTextField(grid, "Path", 0, 7, 3, 1, true);
    durationField = createTextField(grid, "Duration", 0, 8, 1, 1, true);
    bpmField = createTextField(grid, "BPM", 0, 9, 1, 1, true);
    bitrateField = createTextField(grid, "Bitrate", 0, 10, 1, 1, true);
    samplerateField = createTextField(grid, "Samplerate", 0, 11, 1, 1, true);

    cover = new ImageView("nocover.png");
    cover.setFitHeight(200);
    cover.setFitWidth(200);
    GridPane.setConstraints(cover, 2, 8, 2, 4);
    grid.add(cover, 2, 8);
    return new Tab("General", grid);
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
      titleField.setText(song.getTitle());
      artistField.setText(song.getArtist());
      albumartistField.setText(song.getAlbumArtist());
      albumField.setText(song.getAlbum());

      genreField.setText(song.getGenre());
      yearField.setText(song.getYear());
      trackField.setText(song.getTrack());
      cdField.setText(song.getCD());

      pathField.setText(song.getPath());
      durationField.setText(song.getLength());
      bpmField.setText(song.getBPM());
      bitrateField.setText(String.valueOf(song.getBitrate()));
      samplerateField.setText(String.valueOf(song.getSamplingrate()));

      cover.setImage(song.getCover());
    }
  }
}
