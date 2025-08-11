package de.b4.jfx.handler;

import de.b4.jfx.Main;
import de.b4.jfx.Messages;
import de.b4.jfx.dialogs.RenameDialog;
import de.b4.jfx.model.Song;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Optional;

public class RenameHandler extends SelectedHandler {
    private static RenameHandler instance;

    public static Handler getInstance() {
        if (instance == null) {
            instance = new RenameHandler();
        }
        return instance;
    }

    @Override
    FontIcon getFontIcon() {
        return new FontIcon(getIconCode("fa-exchange"));
    }

    @Override
    String getMenuItemText() {
        return Messages.getString("menu.rename.label");
    }

    @Override
    String getTooltipText() {
        return Messages.getString("menu.rename.tooltip");
    }

    @Override
    EventHandler<ActionEvent> getEventHandler() {
        return this::action;
    }

    @Override
    KeyCodeCombination getKeyCodeCombination() {
        return new KeyCodeCombination(KeyCode.R, KeyCombination.ALT_DOWN);
    }

    @Override
    boolean hasToolbarEntry() {
        return true;
    }

    private void action(ActionEvent actionEvent) {
        Song[] songs = Main.theApp.getSelectedSongs();
        if (songs.length > 0) {
            Optional<Song[]> result = RenameDialog.newInstance(songs).showAndWait();
            if (result.isPresent()) {
                Main.theApp.songTable.refresh();
            }
        }
    }
}
