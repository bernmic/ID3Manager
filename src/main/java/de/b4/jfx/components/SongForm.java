package de.b4.jfx.components;

import de.b4.jfx.Messages;
import de.b4.jfx.model.Song;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

public abstract class SongForm extends GridPane {
    public final static String TITLE_FIELD = "title";
    public final static String ARTIST_FIELD = "artist";
    public final static String ALBUMARTIST_FIELD = "albunartist";
    public final static String ALBUM_FIELD = "album";
    public final static String GENRE_FIELD = "genre";
    public final static String YEAR_FIELD = "year";
    public final static String TRACK_FIELD = "track";
    public final static String CD_FIELD = "cd";
    public final static String FILENAME_FIELD = "filename";
    public final static String DURATION_FIELD = "duration";
    public final static String BPM_FIELD = "bpm";
    public final static String BITRATE_FIELD = "bitrate";
    public final static String SAMPLERATE_FIELD = "samplerate";
    public final static Image NOCOVER_IMAGE = new Image("nocover.png");

    protected Song[] songs;
    protected Dialog dialog;
    protected ImageView cover;

    protected ImageView createCover(int columnIndex, int rowIndex) {
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