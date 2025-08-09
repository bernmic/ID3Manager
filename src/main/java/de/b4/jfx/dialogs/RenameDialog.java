package de.b4.jfx.dialogs;

import de.b4.jfx.Main;
import de.b4.jfx.Messages;
import de.b4.jfx.model.Song;
import de.b4.jfx.util.StringUtils;
import de.b4.jfx.util.Util;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class RenameDialog extends Dialog<Song[]> {
    private Song[] songs;
    private TextField patternField;
    private Label example;
    private ContextMenu fieldsMenu;

    public static RenameDialog newInstance(Song[] songs) {
        RenameDialog dialog = new RenameDialog();
        dialog.getDialogPane().getScene().getRoot().setStyle(Main.theApp.getFontStyle());
        dialog.fieldsMenu = dialog.createFieldsMenu();
        dialog.getDialogPane().setMinWidth(4 * Main.theApp.getZoom());
        dialog.songs = songs;
        dialog.setTitle(Messages.getString("rename.title.label"));
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 10, 10));

        dialog.patternField = new TextField(Main.theApp.getConfiguration().getRenamePattern());
        GridPane.setHgrow(dialog.patternField, Priority.ALWAYS);
        grid.add(new Label(Messages.getString("pattern.label")), 0, 0);
        grid.add(dialog.patternField, 1, 0);

        grid.add(new Label(Messages.getString("example.label")), 0, 2);
        dialog.example = new Label(dialog.renameFile(dialog.patternField.getText(), songs[0]));
        GridPane.setConstraints(dialog.example, 0, 3, 2, 1);
        grid.add(dialog.example, 0, 3);
        // grid.setGridLinesVisible(true);
        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(param -> {
            if (param == ButtonType.OK) {
                dialog.renameSongs(dialog.patternField.getText());
                Main.theApp.getConfiguration().setRenamePattern(dialog.patternField.getText());
                return dialog.songs;
            }
            return null;
        });
        dialog.patternField.textProperty().addListener((observable, oldValue, newValue) -> {
            dialog.example.setText(dialog.renameFile(newValue, songs[0]));
        });
        dialog.patternField.setContextMenu(dialog.fieldsMenu);
        dialog.initOwner(Main.theApp.rootStage);

        return dialog;
    }

    private ContextMenu createFieldsMenu() {
        ContextMenu contextMenu = new ContextMenu();
        contextMenu.getItems().add(createFieldMenuItem(Messages.getString("column.track.label"), "%n"));
        contextMenu.getItems().add(createFieldMenuItem(Messages.getString("column.artist.label"), "%a"));
        contextMenu.getItems().add(createFieldMenuItem(Messages.getString("column.albumartist.label"), "%A"));
        contextMenu.getItems().add(createFieldMenuItem(Messages.getString("column.album.label"), "%b"));
        contextMenu.getItems().add(createFieldMenuItem(Messages.getString("column.title.label"), "%t"));
        contextMenu.getItems().add(createFieldMenuItem(Messages.getString("column.genre.label"), "%g"));
        contextMenu.getItems().add(createFieldMenuItem(Messages.getString("column.year.label"), "%y"));
        contextMenu.getItems().add(createFieldMenuItem(Messages.getString("column.media.label"), "%m"));

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

    private void renameSongs(String pattern) {
        for (Song song : songs) {
            String s = renameFile(pattern, song);
            if (!song.getFilename().equals(s)) {
                song.setFilename(s);
            }
        }
    }

    private String renameFile(String patternText, Song song) {
        String p = patternText;
        String tracks = "";
        if (song.getTrack() != null) {
            int track = Util.getTrack(song.getTrack());
            tracks = String.format("%02d", track);
        }
        p = StringUtils.replace(p, "%n", tracks);
        p = StringUtils.replace(p, "%a", sanitizeFilename(song.getArtist()));
        p = StringUtils.replace(p, "%A", sanitizeFilename(song.getAlbumArtist()));
        p = StringUtils.replace(p, "%b", sanitizeFilename(song.getAlbum()));
        p = StringUtils.replace(p, "%t", sanitizeFilename(song.getTitle()));
        p = StringUtils.replace(p, "%g", sanitizeFilename(song.getGenre()));
        p = StringUtils.replace(p, "%y", song.getYear());
        p = StringUtils.replace(p, "%m", song.getCD());
        p += ("." + song.getFileType());
        p = p.replaceAll("[/:]", " ").replaceAll("[ ]+", " ");

        System.out.println("Rename " + song.getFilename() + " to " + p);

        return p;
    }

    private String sanitizeFilename(String name) {
        return name.replaceAll("[:\\\\/*?|<>.]", "_");
    }

    public static class Field {
        final private String title;
        final private String key;

        public Field(String key, String title) {
            this.title = title;
            this.key = key;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}
