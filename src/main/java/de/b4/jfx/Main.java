package de.b4.jfx;

import de.b4.jfx.handler.*;
import de.b4.jfx.model.Configuration;
import de.b4.jfx.model.Song;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class Main extends Application {
  private boolean initialization = true;
  final String os = System.getProperty("os.name");
  public static Main theApp;
  private StackPane root;
  private DirectoryTree directoryTree;
  public SongTable songTable;
  private Configuration configuration;
  public static final BooleanProperty somethingSelected = new SimpleBooleanProperty(false);

  public Main() {
    Main.theApp = this;
  }

  @SuppressWarnings("RedundantThrows")
  @Override
  public void init() throws Exception {
    System.out.println("Init...");
  }

  @SuppressWarnings("RedundantThrows")
  @Override
  public void stop() throws Exception {
    configuration.setWidth((int) root.getWidth());
    configuration.setHeight((int) root.getHeight());
    configuration.save();
    System.out.println("Stop...");
  }

  @SuppressWarnings("RedundantThrows")
  @Override
  public void start(Stage stage) throws Exception {
    System.out.println("Start...");
    stage.setTitle("ID3Manager 2");
    root = new StackPane(createApplicationFrame());
    stage.setScene(new Scene(root, getConfiguration().getWidth(), getConfiguration().getHeight()));
    stage.setOnShowing(e -> {
      directoryTree.expandToPath(Main.theApp.getConfiguration().getCurrentPath());
    });
    initialization = false;
    stage.show();
  }

  private VBox createApplicationFrame() {
    VBox main = new VBox();
    main.getChildren().addAll(createMenubar(), createToolbar(), createMainArea(), createStatusbar());

    return main;
  }

  private MenuBar createMenubar() {
    Menu file = new Menu("_File");
    if (os != null && os.startsWith("Mac")) {
      file.getItems().addAll(
              SaveHandler.getInstance().getMenuItem());
    } else {
      file.getItems().addAll(
              SaveHandler.getInstance().getMenuItem(),
              new SeparatorMenuItem(),
              QuitHandler.getInstance().getMenuItem());
    }
    Menu edit = new Menu("_Edit");
    edit.getItems().addAll(
            UndoHandler.getInstance().getMenuItem(),
            RedoHandler.getInstance().getMenuItem(),
            new SeparatorMenuItem(),
            CopyHandler.getInstance().getMenuItem(),
            CutHandler.getInstance().getMenuItem(),
            PasteHandler.getInstance().getMenuItem());

    Menu song = new Menu("_Songs");
    song.getItems().addAll(
            EditHandler.getInstance().getMenuItem(),
            RenameHandler.getInstance().getMenuItem(),
            ParseHandler.getInstance().getMenuItem(),
            new SeparatorMenuItem(),
            RemoveID3V1Handler.getInstance().getMenuItem(),
            RemoveID3V2Handler.getInstance().getMenuItem());

    Menu help = new Menu("_Help");
    help.getItems().addAll(AboutHandler.getInstance().getMenuItem());

    MenuBar menuBar = new MenuBar();
    menuBar.getMenus().addAll(file, edit, song, help);

    if (os != null && os.startsWith("Mac"))
      menuBar.useSystemMenuBarProperty().set(true);

    return menuBar;
  }

  private ToolBar createToolbar() {
    return new ToolBar(
            SaveHandler.getInstance().getToolbarButton(),
            new Separator(),
            UndoHandler.getInstance().getToolbarButton(),
            RedoHandler.getInstance().getToolbarButton(),
            new Separator(),
            CopyHandler.getInstance().getToolbarButton(),
            CutHandler.getInstance().getToolbarButton(),
            PasteHandler.getInstance().getToolbarButton(),
            new Separator(),
            EditHandler.getInstance().getToolbarButton(),
            RenameHandler.getInstance().getToolbarButton(),
            ParseHandler.getInstance().getToolbarButton());
  }

  private HBox createStatusbar() {
    HBox statusBar = new HBox();
    statusBar.getChildren().addAll(new Label("Ready."));
    return statusBar;
  }

  private SplitPane createMainArea() {
    directoryTree = createTreeView();
    songTable = createTableView();
    songTable.connectTree(directoryTree);
    SplitPane mainArea = new SplitPane();
    mainArea.getItems().addAll(directoryTree, songTable);
    VBox.setVgrow(mainArea, Priority.ALWAYS);
    mainArea.setDividerPosition(0, 0.33);

    return mainArea;
  }

  private SongTable createTableView() {
    return SongTable.newInstance();
  }

  private DirectoryTree createTreeView() {
    return DirectoryTree.newInstance();
  }

  public void showOverlay(Node node) {
    if (initialization)
      return;
    root.getChildren().get(0).setDisable(true);
    root.getChildren().add(node);
  }

  public void hideOverlay(Node node) {
    if (initialization)
      return;
    root.getChildren().remove(node);
    root.getChildren().get(0).setDisable(false);
  }

  public Configuration getConfiguration() {
    if (configuration == null)
      configuration = Configuration.newInstance();
    return configuration;
  }

  public Song[] getSelectedSongs() {
    List<Song> songs = this.songTable.getSelectionModel().getSelectedItems().stream().collect(Collectors.toList());
    return songs.toArray(new Song[songs.size()]);
  }

  public static void main(String[] args) {
    launch(args);
  }
}
