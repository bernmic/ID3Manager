package de.b4.jfx.dialogs;

import de.b4.jfx.Main;
import de.b4.jfx.model.Song;
import de.b4.jfx.util.Util;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.util.HashMap;
import java.util.Map;

public class ParseDialog extends Dialog<Song[]> {
  private Song[] songs;
  private TextField patternField;
  private ContextMenu fieldsMenu;
  private Label trackLabel;
  private Label artistLabel;
  private Label titleLabel;
  private Label albumLabel;
  private Label albumartistLabel;
  private Label genreLabel;
  private Label yearLabel;
  private Label mediaLabel;

  public static ParseDialog newInstance(Song[] songs) {
    ParseDialog dialog = new ParseDialog();
    dialog.fieldsMenu = dialog.createFieldsMenu();
    dialog.getDialogPane().setMinWidth(400);
    dialog.songs = songs;
    dialog.setTitle("Parse songs");
    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 10, 10, 10));

    String parsePattern = Main.theApp.getConfiguration().getParsePattern();

    dialog.patternField = new TextField(parsePattern);
    GridPane.setHgrow(dialog.patternField, Priority.ALWAYS);
    grid.add(new Label("Pattern"), 0, 0);
    grid.add(dialog.patternField, 1, 0);

    grid.add(new Label("Example"), 0, 2);
    grid.add(new Label("Track"), 0, 3);
    grid.add(new Label("Artist"), 0, 4);
    grid.add(new Label("Title"), 0, 5);
    grid.add(new Label("Album"), 0, 6);
    grid.add(new Label("Album artist"), 0, 7);
    grid.add(new Label("Genre"), 0, 8);
    grid.add(new Label("Year"), 0, 9);
    grid.add(new Label("Media"), 0, 10);

    dialog.trackLabel = dialog.createLabel(grid, 1, 3);
    dialog.artistLabel = dialog.createLabel(grid, 1, 4);
    dialog.titleLabel = dialog.createLabel(grid, 1, 5);
    dialog.albumLabel = dialog.createLabel(grid, 1, 6);
    dialog.albumartistLabel = dialog.createLabel(grid, 1, 7);
    dialog.genreLabel = dialog.createLabel(grid, 1, 8);
    dialog.yearLabel = dialog.createLabel(grid, 1, 9);
    dialog.mediaLabel = dialog.createLabel(grid, 1, 10);
    dialog.fillExample(parsePattern);
    dialog.getDialogPane().setContent(grid);
    dialog.setResultConverter(param -> {
      if (param == ButtonType.OK) {
        dialog.parseSongs(dialog.patternField.getText());
        Main.theApp.getConfiguration().setParsePattern(dialog.patternField.getText());
        return dialog.songs;
      }
      return null;
    });
    dialog.patternField.textProperty().addListener((observable, oldValue, newValue) -> {
      dialog.fillExample(newValue);
    });
    dialog.patternField.setContextMenu(dialog.fieldsMenu);
    return dialog;
  }

  private Label createLabel(GridPane grid, int col, int row) {
    Label label = new Label();
    grid.add(label, col, row);
    return label;
  }

  private ContextMenu createFieldsMenu() {
    ContextMenu contextMenu = new ContextMenu();
    contextMenu.getItems().add(createFieldMenuItem("Track", "%n"));
    contextMenu.getItems().add(createFieldMenuItem("Artist", "%a"));
    contextMenu.getItems().add(createFieldMenuItem("Album artist", "%A"));
    contextMenu.getItems().add(createFieldMenuItem("Album", "%b"));
    contextMenu.getItems().add(createFieldMenuItem("Title", "%t"));
    contextMenu.getItems().add(createFieldMenuItem("Genre", "%g"));
    contextMenu.getItems().add(createFieldMenuItem("Year", "%y"));
    contextMenu.getItems().add(createFieldMenuItem("Media", "%m"));

    return contextMenu;
  }

  private MenuItem createFieldMenuItem(String title, String pattern) {
    MenuItem menuItem = new MenuItem(title);
    menuItem.setUserData(pattern);
    menuItem.setOnAction(e -> {
      int pos = patternField.getCaretPosition();
      if (pos >= 0) {
        String p1 = patternField.getText().substring(0, pos);
        String p2 = patternField.getText().substring(pos);
        patternField.setText(p1 + menuItem.getUserData() + p2);
        patternField.positionCaret(pos + 2);
      }
    });
    return menuItem;
  }

  private void fillExample(String pattern) {
    Map<String, String> fields = parseFilename(pattern, songs[0].getFilename());
    trackLabel.setText(fields.get("track"));
    artistLabel.setText(fields.get("artist"));
    albumartistLabel.setText(fields.get("albumartist"));
    titleLabel.setText(fields.get("title"));
    albumLabel.setText(fields.get("album"));
    genreLabel.setText(fields.get("genre"));
    yearLabel.setText(fields.get("year"));
    mediaLabel.setText(fields.get("media"));
  }

  private void parseSongs(String patternText) {
    for (Song song : songs) {

      Map<String, String> data = parseFilename(patternText, song.getFilename());
      if (isChanged(data.get("track"), song.getTrack())) {
        song.setTrack(data.get("track"));
        song.setDirty(true);
      }

      if (isChanged(data.get("artist"), song.getArtist())) {
        song.setArtist(data.get("artist"));
        song.setDirty(true);
      }

      if (isChanged(data.get("album"), song.getAlbum())) {
        song.setAlbum(data.get("album"));
        song.setDirty(true);
      }

      if (isChanged(data.get("albumartist"), song.getAlbumArtist())) {
        song.setAlbumArtist(data.get("albumartist"));
        song.setDirty(true);
      }

      if (isChanged(data.get("title"), song.getTitle())) {
        song.setTitle(data.get("title"));
        song.setDirty(true);
      }

      if (isChanged(data.get("year"), song.getYear())) {
        song.setYear(data.get("year"));
        song.setDirty(true);
      }

      if (isChanged(data.get("genre"), song.getGenre())) {
        song.setGenre(data.get("genre"));
        song.setDirty(true);
      }

      if (isChanged(data.get("media"), song.getCD())) {
        song.setCD(data.get("media"));
        song.setDirty(true);
      }
    }
  }

  private Map<String, String> parseFilename(String pattern, String filename) {
    // %n - %a - %t
    Map<String, String> data = new HashMap<String, String>();
    filename = Util.removeExtension(filename);
    pattern = pattern.replaceAll("%", "%#");
    String[] parts = pattern.split("%");
    for (String part : parts) {
      if (!part.startsWith("#") || part.length() == 1) {
        if (!filename.startsWith(part)) {
          // no match. leave.
          break;
        }
        filename = filename.substring(part.length());
        if (filename.length() == 0) {
          break;
        }
        continue;
      }
      char t = part.charAt(1);
      part = part.substring(2);

      int cutpos = (part.length() > 0) ? filename.indexOf(part) : filename.length();
      if (cutpos >= 0) {
        // last part. copy content
        switch (t) {
          case 'n':
            data.put("track", filename.substring(0, cutpos));
            break;
          case 'a':
            data.put("artist", filename.substring(0, cutpos));
            break;
          case 'A':
            data.put("albumartist", filename.substring(0, cutpos));
            break;
          case 'b':
            data.put("album", filename.substring(0, cutpos));
            break;
          case 't':
            data.put("title", filename.substring(0, cutpos));
            break;
          case 'g':
            data.put("genre", filename.substring(0, cutpos));
            break;
          case 'y':
            data.put("year", filename.substring(0, cutpos));
            break;
          case 'm':
            data.put("media", filename.substring(0, cutpos));
            break;
          case 'x':
            // ignore, fallback to default
          default:
            break;
        }
        filename = filename.substring(cutpos);
      }

      if (!filename.startsWith(part)) {
        // no match. leave.
        break;
      }
      filename = filename.substring(part.length());
      if (filename.length() == 0) {
        break;
      }
    }
    return data;
  }

  private boolean isChanged(String neu, String old) {
    if (neu == null)
      return false;
    return (old != null && !neu.equals(old)) || (old == null && neu.length() > 0);
  }
}
