package de.b4.jfx.handler;

import de.b4.jfx.Main;
import de.b4.jfx.Messages;
import de.b4.jfx.model.Song;
import de.b4.jfx.util.StringUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCodeCombination;
import org.kordamp.ikonli.javafx.FontIcon;

public class RenameTagsToCamelcaseHandler extends SelectedHandler {
    private final static char[] FILENAME_SEPARATORS = new char[]{' ', '-', '_', '(', ')'};
    private static RenameTagsToCamelcaseHandler instance;

    public static Handler getInstance() {
        if (instance == null) {
            instance = new RenameTagsToCamelcaseHandler();
        }
        return instance;
    }

    @Override
    FontIcon getFontIcon() {
        return null;
    }

    @Override
    String getMenuItemText() {
        return Messages.getString("menu.camelcasetags.label");
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
        return null;
    }

    private void action(ActionEvent actionEvent) {
        for (Song song : Main.theApp.getSelectedSongs()) {
            String neu = StringUtils.capitalizeFully(song.getArtist(), FILENAME_SEPARATORS);
            if (!song.getArtist().equals(neu)) {
                song.setArtist(neu);
                song.setDirty(true);
            }
            neu = StringUtils.capitalizeFully(song.getAlbum(), FILENAME_SEPARATORS);
            if (!song.getAlbum().equals(neu)) {
                song.setAlbum(neu);
                song.setDirty(true);
            }
            neu = StringUtils.capitalizeFully(song.getTitle(), FILENAME_SEPARATORS);
            if (!song.getTitle().equals(neu)) {
                song.setTitle(neu);
                song.setDirty(true);
            }
        }
        Main.theApp.songTable.refresh();
    }
}
