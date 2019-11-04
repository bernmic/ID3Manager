package de.b4.jfx.dialogs;

import de.b4.jfx.Main;
import de.b4.jfx.Messages;
import de.b4.jfx.model.Song;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javafx.util.StringConverter;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.MalformedURLException;

public class MediaplayerDialog extends Dialog<Song> {
  final static Image NOCOVER_IMAGE = new Image("nocover.png");

  private Song song;
  private MediaPlayer player;

  private MediaplayerDialog() {}

  public static MediaplayerDialog newInstance(Song song) {
    MediaplayerDialog dialog = new MediaplayerDialog();
    dialog.song = song;
    dialog.setTitle(Messages.getString("player.title.label"));
    dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 10, 10, 10));

    ImageView cover = new ImageView();
    cover.setFitHeight(200);
    cover.setFitWidth(200);
    GridPane.setConstraints(cover, 0, 0, 1, 4);
    cover.setImage(song.getCover() == null ? NOCOVER_IMAGE : song.getCover());
    grid.add(cover, 0, 0);

    grid.add(new Label(Messages.getString("column.title.label")), 1, 0);
    grid.add(new Label(song.getTitle()), 2, 0);
    grid.add(new Label(Messages.getString("column.artist.label")), 1, 1);
    grid.add(new Label(song.getArtist()), 2, 1);
    grid.add(new Label(Messages.getString("column.album.label")), 1, 2);
    grid.add(new Label(song.getAlbum()), 2, 2);
    Label durationLabel = new Label(Messages.getString("column.duration.label"));

    GridPane.setValignment(durationLabel, VPos.TOP);
    Label duration = new Label(song.getLength());
    GridPane.setValignment(duration, VPos.TOP);
    grid.add(durationLabel, 1, 3);
    grid.add(duration, 2, 3);

    dialog.getDialogPane().setContent(grid);
    dialog.initOwner(Main.theApp.rootStage);

    try {
      Media media = new Media(song.getFile().toURI().toURL().toExternalForm());
      dialog.player = new MediaPlayer(media);

      dialog.addPlayerControls(grid);
      dialog.player.play();
    } catch (MalformedURLException e) {
      System.out.println("Error playing song");
    }
    // stop player, when dialog will be closed
    dialog.setOnCloseRequest(e -> {
      if (dialog.player != null) {
        dialog.player.stop();
      }
    });
    return dialog;
  }

  private void addPlayerControls(GridPane grid) {
    Label volumeLabel = new Label(getVolumeLabelText());
    Slider volumeSlider = new Slider(0, 1, 1);
    volumeSlider.setMajorTickUnit(0.5);
    volumeSlider.setShowTickLabels(true);
    volumeSlider.setLabelFormatter(new StringConverter<Double>() {
      @Override
      public String toString(Double aDouble) {
        return String.format("%d", (int)(aDouble * 100f));
      }

      @Override
      public Double fromString(String s) {
        return null;
      }
    });
    this.player.volumeProperty().bind(volumeSlider.valueProperty());
    this.player.volumeProperty().addListener(e -> {
      volumeLabel.setText(getVolumeLabelText());
    });

    Label rateLabel = new Label(getRateLabelText());
    Slider rateSlider = new Slider(0, 5, 1);
    rateSlider.setMajorTickUnit(0.5);
    rateSlider.setShowTickLabels(true);
    this.player.rateProperty().bind(rateSlider.valueProperty());
    this.player.rateProperty().addListener(e -> {
      rateLabel.setText(getRateLabelText());
    });

    Label balanceLabel = new Label(getBalanceLabelText());
    Slider balanceSlider = new Slider(-1, 1, 0);
    balanceSlider.setMajorTickUnit(1.0);
    balanceSlider.setShowTickLabels(true);
    this.player.balanceProperty().bind(balanceSlider.valueProperty());
    this.player.balanceProperty().addListener(e -> {
      balanceLabel.setText(getBalanceLabelText());
    });

    GridPane.setConstraints(volumeLabel, 0, 6, 3, 1);
    GridPane.setConstraints(volumeSlider, 0,7, 3, 1);
    GridPane.setConstraints(rateLabel, 0, 8, 3, 1);
    GridPane.setConstraints(rateSlider, 0,9, 3, 1);
    GridPane.setConstraints(balanceLabel, 0, 10, 3, 1);
    GridPane.setConstraints(balanceSlider, 0,11, 3, 1);

    grid.getChildren().addAll(
            createTimeBar(),
            createToolBar(),
            volumeLabel,
            volumeSlider,
            rateLabel,
            rateSlider, balanceLabel,
            balanceSlider);

    //grid.setGridLinesVisible(true);
  }

  private ToolBar createToolBar() {
    Button play = new Button();
    play.setGraphic(new FontIcon(Main.theApp.getConfiguration().getIconCode("fa-play")));

    GridPane.setHalignment(play, HPos.LEFT);
    play.setOnAction(e -> {
      player.play();
    });
    Button pause = new Button();
    pause.setGraphic(new FontIcon(Main.theApp.getConfiguration().getIconCode("fa-pause")));
    GridPane.setHalignment(pause, HPos.LEFT);
    pause.setOnAction(e -> {
      player.pause();
    });
    Button stop = new Button();
    stop.setGraphic(new FontIcon(Main.theApp.getConfiguration().getIconCode("fa-stop")));
    GridPane.setHalignment(stop, HPos.LEFT);
    stop.setOnAction(e -> {
      player.stop();
    });
    ToolBar toolBar = new ToolBar(play, pause, stop);
    GridPane.setConstraints(toolBar, 0, 5, 3, 1);

    return toolBar;
  }

  private HBox createTimeBar() {
    Label timeLabel = new Label();
    ProgressBar time = new ProgressBar();
    time.setMaxWidth(Double.MAX_VALUE);
    HBox.setHgrow(time, Priority.ALWAYS);
    // update progressbar and label
    this.player.currentTimeProperty().addListener(e -> {
      double d = this.player.getCurrentTime().toMillis() / this.player.getTotalDuration().toMillis();
      time.setProgress(d);
      timeLabel.setText(Song.getDurationAsString((int)this.player.getCurrentTime().toSeconds()));
    });
    HBox timeBox = new HBox(time, timeLabel);
    timeBox.setSpacing(10);
    timeBox.setMaxWidth(Double.MAX_VALUE);
    GridPane.setConstraints(timeBox, 0, 4, 3, 1);
    // at end of media reset progressbar and label
    player.setOnEndOfMedia(() -> {
      time.setProgress(0);
      timeLabel.setText(Song.getDurationAsString(0));
    });
    // if user click on progressbar, seek the position
    time.setOnMouseClicked(e -> {
      if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 1) {
        double d = e.getX() / time.getWidth();
        Duration du = new Duration(player.getTotalDuration().toMillis() * d);
        player.seek(du);
      }
    });
    return timeBox;
  }

  private String getVolumeLabelText() {
    return String.format(Messages.getString("volume.label"), (int)(player.getVolume() * 100));
  }

  private String getRateLabelText() {
    return String.format(Messages.getString("rate.label"), (int)(player.getRate() * 100));
  }

  private String getBalanceLabelText() {
    return String.format(Messages.getString("balance.label"), (int)(player.getBalance() * 100));
  }
}
