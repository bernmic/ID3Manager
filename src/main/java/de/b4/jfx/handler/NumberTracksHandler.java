package de.b4.jfx.handler;

import de.b4.jfx.Main;
import de.b4.jfx.Messages;
import de.b4.jfx.model.Song;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import org.kordamp.ikonli.javafx.FontIcon;

public class NumberTracksHandler extends SelectedHandler {
    private static NumberTracksHandler instance;

    public static Handler getInstance() {
        if (instance == null) {
            instance = new NumberTracksHandler();
        }
        return instance;
    }

    @Override
    FontIcon getFontIcon() {
        return null;
    }

    @Override
    String getMenuItemText() {
        return Messages.getString("menu.numbertracks.label");
    }

    @Override
    String getTooltipText() {
        return null;
    }

    @Override
    EventHandler<ActionEvent> getEventHandler() {
        return this::action;
    }

    @Override
    KeyCodeCombination getKeyCodeCombination() {
        return new KeyCodeCombination(KeyCode.N, KeyCombination.ALT_DOWN);
    }

    private void action(ActionEvent actionEvent) {
        int cnt = 1;
        for (Song song : Main.theApp.getSelectedSongs()) {
            song.setTrack(String.format("%02d", cnt++));
            song.setDirty(true);
        }
        Main.theApp.songTable.refresh();
    }
}
