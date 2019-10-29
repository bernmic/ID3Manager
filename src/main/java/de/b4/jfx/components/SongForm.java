package de.b4.jfx.components;

import de.b4.jfx.Messages;
import de.b4.jfx.model.Song;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

public abstract class SongForm extends GridPane {
    final static String TITLE_FIELD = "title";
    final static String ARTIST_FIELD = "artist";
    final static String ALBUMARTIST_FIELD = "albunartist";
    final static String ALBUM_FIELD = "album";
    final static String GENRE_FIELD = "genre";
    final static String YEAR_FIELD = "year";
    final static String TRACK_FIELD = "track";
    final static String CD_FIELD = "cd";
    final static String FILENAME_FIELD = "filename";
    final static String DURATION_FIELD = "duration";
    final static String BPM_FIELD = "bpm";
    final static String BITRATE_FIELD = "bitrate";
    final static String SAMPLERATE_FIELD = "samplerate";
    final static Image NOCOVER_IMAGE = new Image("nocover.png");

    final static String COPYRIGHT_FIELD = "copyright";
    final static String COMPOSER_FIELD = "composer";
    final static String ORIGINALARTIST_FIELD = "originalartist";
    final static String ENCODER_FIELD = "encoder";
    final static String PUBLISHER_FIELD = "publisher";
    final static String COMPILATION_FIELD = "compilation";
    final static String GROUPING_FIELD = "grouping";

    Song[] songs;
    Dialog dialog;
    ImageView cover;

    public abstract void fillForm();
    public abstract void saveForm();

    TextField createTextField(GridPane grid, String title, int col, int row, int colspan, int rowspan,
                                      boolean disabled) {
        Label label = new Label(Messages.getString(title));
        GridPane.setConstraints(label, col, row, 1, 1);
        grid.add(label, col, row);
        TextField textField = new TextField();
        textField.setDisable(disabled);
        GridPane.setHgrow(textField, Priority.ALWAYS);
        GridPane.setConstraints(textField, col + 1, row, colspan, rowspan);
        grid.add(textField, col + 1, row);

        return textField;
    }


    MultiEdit<String> createMultiTextField(GridPane grid, String title, int col, int row, int colspan, int rowspan, boolean disabled) {
        Label label = new Label(Messages.getString(title));
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

    ImageView createCover(int columnIndex, int rowIndex) {
        cover = new ImageView(NOCOVER_IMAGE);
        cover.setFitHeight(200);
        cover.setFitWidth(200);
        GridPane.setConstraints(cover, columnIndex, rowIndex, 2, 4);
        cover.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle(Messages.getString("choosecover.label"));
                fileChooser.setInitialDirectory(Paths.get(songs[0].getPath()).toFile());
                fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter(
                        Messages.getString("images.label"), "*.jpeg", "*.jpg", "*.gif", "*.png"

                ));
                File f = fileChooser.showOpenDialog(this.dialog.getOwner());
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
                        alert.setTitle(Messages.getString("filenotfound.title"));
                        alert.setContentText(Messages.getString("filenotfound.message"));
                        alert.showAndWait();
                        ;
                    }
                }
            }
        });
        return cover;
    }
}