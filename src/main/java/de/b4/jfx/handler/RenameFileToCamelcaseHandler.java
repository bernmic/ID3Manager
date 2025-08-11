package de.b4.jfx.handler;

import de.b4.jfx.Main;
import de.b4.jfx.Messages;
import de.b4.jfx.model.Song;
import de.b4.jfx.util.StringUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCodeCombination;
import org.kordamp.ikonli.javafx.FontIcon;

public class RenameFileToCamelcaseHandler extends SelectedHandler {
    private final static char[] FILENAME_SEPARATORS = new char[]{' ', '-', '_', '(', ')'};
    private static RenameFileToCamelcaseHandler instance;

    public static Handler getInstance() {
        if (instance == null) {
            instance = new RenameFileToCamelcaseHandler();
        }
        return instance;
    }

    @Override
    FontIcon getFontIcon() {
        return null;
    }

    @Override
    String getMenuItemText() {
        return Messages.getString("menu.camelcasefilename.label");
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
            String neu = StringUtils.capitalizeFully(song.getFilename(), FILENAME_SEPARATORS);
            if (!song.getFilename().equals(neu)) {
                song.setFilename(neu);
                song.setDirty(true);
            }
        }
        Main.theApp.songTable.refresh();
    }
}
