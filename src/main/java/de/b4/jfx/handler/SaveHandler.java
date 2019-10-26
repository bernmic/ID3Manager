package de.b4.jfx.handler;

import de.b4.jfx.Main;
import de.b4.jfx.Messages;
import de.b4.jfx.dialogs.ID3Dialog;
import de.b4.jfx.model.Song;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;
import java.util.Optional;

public class SaveHandler extends Handler {
  private static SaveHandler instance;

  public static Handler getInstance() {
    if (instance == null) {
      instance = new SaveHandler();
    }
    return instance;
  }

  public MenuItem createMenuItem() {
    MenuItem menuItem = new MenuItem(Messages.getString("menu.save.label"));
    menuItem.setGraphic(new FontIcon(getIconCode("fa-save")));
    menuItem.setOnAction(this::action);
    menuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN));
    return menuItem;
  }

  public Button createToolbarButton() {
    Button button = new Button();
    button.setGraphic(new FontIcon(getIconCode("fa-save")));
    button.setOnAction(this::action);
    button.setTooltip(new Tooltip(Messages.getString("menu.save.tooltip")));
    return button;
  }

  private void action(ActionEvent actionEvent) {
    Label title = new Label("");
    ProgressBar pb = new ProgressBar();
    Label status = new Label("");
    status.setMinWidth(300);
    VBox box = new VBox(title, pb, status);
    box.setAlignment(Pos.CENTER);

    Main.theApp.showOverlay(box);
    SaveService service = new SaveService();
    service.setOnSucceeded(e -> {
      Main.theApp.hideOverlay(box);
    });
    title.textProperty().bind(service.titleProperty());
    pb.progressProperty().bind(service.progressProperty());
    status.textProperty().bind(service.messageProperty());
    service.start();
  }

  private static class SaveService extends Service<Void> {

    @Override
    protected Task<Void> createTask() {
      return new Task<Void>() {
        @Override
        protected Void call() throws Exception {
          updateTitle(Messages.getString("saving.label"));
          final long count = Main.theApp.songTable.getItems().size();
          final long[] i = {0};
          Main.theApp.songTable.getItems().forEach(s -> {
            if (s.isDirty()) {
              System.out.println("Save " + s.getPath() + s.getFilename());
              updateMessage(s.getFilename());
              s.save();
              ++i[0];
              updateProgress(i[0], count);
            }
          });
          return null;
        }
      };
    }
  }
}
