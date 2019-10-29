package de.b4.jfx.components;

import java.util.Map;
import java.util.HashMap;

import de.b4.jfx.Messages;
import de.b4.jfx.model.Song;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class SingleSongGeneralForm extends SongForm {

    private Map<String, TextField> singleSongFields;

    private SingleSongGeneralForm() {
    }

    public static SingleSongGeneralForm newInstance(Dialog dialog, Song[] songs) {
        SingleSongGeneralForm grid = new SingleSongGeneralForm();
        grid.dialog = dialog;
        grid.songs = songs;
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 10, 10));
        grid.singleSongFields = new HashMap<>();
        grid.singleSongFields.put(TITLE_FIELD, grid.createTextField(grid, "column.title.label", 0, 0, 3, 1, false));
        grid.singleSongFields.put(ARTIST_FIELD, grid.createTextField(grid, "column.artist.label", 0, 1, 3, 1, false));
        grid.singleSongFields.put(ALBUMARTIST_FIELD, grid.createTextField(grid, "column.albumartist.label", 0, 2, 3, 1, false));
        grid.singleSongFields.put(ALBUM_FIELD, grid.createTextField(grid, "column.album.label", 0, 3, 3, 1, false));

        grid.singleSongFields.put(GENRE_FIELD, grid.createTextField(grid, "column.genre.label", 0, 4, 1, 1, false));
        grid.singleSongFields.put(YEAR_FIELD, grid.createTextField(grid, "column.year.label", 2, 4, 1, 1, false));
        grid.singleSongFields.put(TRACK_FIELD, grid.createTextField(grid, "column.track.label", 0, 5, 1, 1, false));
        grid.singleSongFields.put(CD_FIELD, grid.createTextField(grid, "column.media.label", 2, 5, 1, 1, false));

        Separator separator = new Separator();
        GridPane.setConstraints(separator, 0, 6, 4, 1);
        grid.add(separator, 0, 6);

        grid.singleSongFields.put(FILENAME_FIELD, grid.createTextField(grid, "column.filename.label", 0, 7, 3, 1, true));
        grid.singleSongFields.put(DURATION_FIELD, grid.createTextField(grid, "column.duration.label", 0, 8, 1, 1, true));
        grid.singleSongFields.put(BPM_FIELD, grid.createTextField(grid, "column.bpm.label", 0, 9, 1, 1, true));
        grid.singleSongFields.put(BITRATE_FIELD, grid.createTextField(grid, "column.bitrate.label", 0, 10, 1, 1, true));
        grid.singleSongFields.put(SAMPLERATE_FIELD, grid.createTextField(grid, "column.samplerate.label", 0, 11, 1, 1, true));

        grid.add(grid.createCover(2, 8), 2, 8);
        return grid;
    }

    private TextField createTextField(GridPane grid, String title, int col, int row, int colspan, int rowspan,
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
}
