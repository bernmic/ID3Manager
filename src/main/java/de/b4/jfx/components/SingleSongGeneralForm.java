package de.b4.jfx.components;

import de.b4.jfx.model.Song;
import javafx.geometry.Insets;
import javafx.scene.control.Dialog;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.Map;

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

    public void fillForm() {
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

        cover.setImage(song.getCover() == null ? NOCOVER_IMAGE : song.getCover());
    }

    public void saveForm() {
        Song song = songs[0];
        if (!song.getTitle().equals(singleSongFields.get(TITLE_FIELD).getText())) {
            song.setTitle(singleSongFields.get(TITLE_FIELD).getText());
            song.setDirty(true);
        }
        if (!song.getArtist().equals(singleSongFields.get(ARTIST_FIELD).getText())) {
            song.setArtist(singleSongFields.get(ARTIST_FIELD).getText());
            song.setDirty(true);
        }
        if (!song.getAlbumArtist().equals(singleSongFields.get(ALBUMARTIST_FIELD).getText())) {
            song.setAlbumArtist(singleSongFields.get(ALBUMARTIST_FIELD).getText());
            song.setDirty(true);
        }
        if (!song.getAlbum().equals(singleSongFields.get(ALBUM_FIELD).getText())) {
            song.setAlbum(singleSongFields.get(ALBUM_FIELD).getText());
            song.setDirty(true);
        }
        if (!song.getGenre().equals(singleSongFields.get(GENRE_FIELD).getText())) {
            song.setGenre(singleSongFields.get(GENRE_FIELD).getText());
            song.setDirty(true);
        }
        if (!song.getYear().equals(singleSongFields.get(YEAR_FIELD).getText())) {
            song.setYear(singleSongFields.get(YEAR_FIELD).getText());
            song.setDirty(true);
        }
        if (!song.getTrack().equals(singleSongFields.get(TRACK_FIELD).getText())) {
            song.setTrack(singleSongFields.get(TRACK_FIELD).getText());
            song.setDirty(true);
        }
        if (!song.getCD().equals(singleSongFields.get(CD_FIELD).getText())) {
            song.setCD(singleSongFields.get(CD_FIELD).getText());
            song.setDirty(true);
        }
    }
}
