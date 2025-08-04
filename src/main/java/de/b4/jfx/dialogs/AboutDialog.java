package de.b4.jfx.dialogs;

import de.b4.jfx.Main;
import de.b4.jfx.Messages;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class AboutDialog extends Dialog<Void> {
  private static final String VERSION = "2.0.0-SNAPSHOT";

  private AboutDialog() {}

  public static AboutDialog newInstance() {
    AboutDialog dialog = new AboutDialog();
    dialog.getDialogPane().getScene().getRoot().setStyle(Main.theApp.getFontStyle());
    dialog.setTitle(Messages.getString("menu.about.label"));
    dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));

    grid.add(new Label("ID3Manager " + VERSION), 0, 0);
    grid.add(new Label("(c) 2019 Michael Bernards"), 0, 1);

    dialog.getDialogPane().setContent(grid);
    dialog.initOwner(Main.theApp.rootStage);

    return dialog;
  }
}
