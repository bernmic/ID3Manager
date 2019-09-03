package de.b4.jfx.dialogs;

import de.b4.jfx.model.Song;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class RenameDialog extends Dialog<Song[]> {
    private Song[] songs;
    private TextField patternField;

    public static RenameDialog newInstance(Song[] songs) {

        RenameDialog dialog = new RenameDialog();
        dialog.songs = songs;
        dialog.setTitle("Edit songs");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        dialog.patternField = new TextField();
        grid.add(new Label("Pattern"), 0, 0);
        grid.add(dialog.patternField, 1, 0);
        dialog.getDialogPane().setContent(grid);
        return dialog;
    }
}
