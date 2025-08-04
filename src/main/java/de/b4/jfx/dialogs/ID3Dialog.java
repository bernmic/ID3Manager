package de.b4.jfx.dialogs;

import de.b4.jfx.Main;
import de.b4.jfx.Messages;
import de.b4.jfx.components.*;
import de.b4.jfx.model.Song;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class ID3Dialog extends Dialog<Song[]> {

  private Song[] songs;

  private SongForm generalForm;
  private SongForm extendedForm;

  private ID3Dialog() {}

  public static ID3Dialog newInstance(Song[] songs) {

    ID3Dialog dialog = new ID3Dialog();
    dialog.getDialogPane().getScene().getRoot().setStyle(Main.theApp.getFontStyle());
    dialog.songs = songs;
    dialog.setTitle(Messages.getString("edit.title.label"));
    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

    VBox vbox = new VBox(new TabPane(dialog.createGeneralTab(), dialog.createExtendedTab()));
    vbox.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
    dialog.getDialogPane().setContent(vbox);
    dialog.getDialogPane().setMinWidth(600 * Main.theApp.getZoom() / 100);
    dialog.setResultConverter(param -> {
      if (param == ButtonType.OK) {
        dialog.generalForm.saveForm();
        dialog.extendedForm.saveForm();
        return dialog.songs;
      }
      return null;
    });
    dialog.generalForm.fillForm();
    dialog.extendedForm.fillForm();
    dialog.initOwner(Main.theApp.rootStage);
    return dialog;
  }

  private Tab createGeneralTab() {
    if (songs.length == 1) {
      generalForm = SingleSongGeneralForm.newInstance(this, songs);
    }
    else {
      generalForm = MultiSongGeneralForm.newInstance(this, songs);
    }
    return new Tab(Messages.getString("general.label"), generalForm);
  }

  private Tab createExtendedTab() {
    if (songs.length == 1) {
      extendedForm = SingleSongExtendedForm.newInstance(this, songs);
    }
    else {
      extendedForm = MultiSongExtendedForm.newInstance(this, songs);
    }
    return new Tab(Messages.getString("extended.label"), extendedForm);
  }
}
