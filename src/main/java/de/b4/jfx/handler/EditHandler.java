package de.b4.jfx.handler;

import de.b4.jfx.Main;
import de.b4.jfx.Messages;
import de.b4.jfx.dialogs.ID3Dialog;
import de.b4.jfx.model.Song;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Optional;

public class EditHandler extends SelectedHandler {
    private static EditHandler instance;

    public static Handler getInstance() {
        if (instance == null) {
            instance = new EditHandler();
        }
        return instance;
    }

    @Override
    FontIcon getFontIcon() {
        return new FontIcon(getIconCode("fa-edit"));
    }

    @Override
    String getMenuItemText() {
        return Messages.getString("menu.edit.label");
    }

    @Override
    String getTooltipText() {
        return Messages.getString("menu.edit.tooltip");
    }

    @Override
    EventHandler<ActionEvent> getEventHandler() {
        return this::action;
    }

    @Override
    KeyCodeCombination getKeyCodeCombination() {
        return new KeyCodeCombination(KeyCode.E, KeyCombination.SHORTCUT_DOWN);
    }

    @Override
    boolean hasToolbarEntry() {
        return true;
    }

    private void action(ActionEvent actionEvent) {
        Song[] songs = Main.theApp.getSelectedSongs();
        if (songs.length > 0) {
            Optional<Song[]> result = ID3Dialog.newInstance(songs).showAndWait();
            if (result.isPresent()) {
                Main.theApp.songTable.refresh();
            }
        }
    }
}
