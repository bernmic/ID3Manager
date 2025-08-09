package de.b4.jfx;

import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;
import de.b4.jfx.handler.*;
import de.b4.jfx.model.Configuration;
import de.b4.jfx.model.Song;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {

    private boolean initialization = true;
    final String os = System.getProperty("os.name");
    public static Main theApp;
    public Stage rootStage;
    private StackPane root;
    private DirectoryTree directoryTree;
    public SongTable songTable;
    private Configuration configuration;
    public static final BooleanProperty somethingSelected =
        new SimpleBooleanProperty(false);

    public Main() {
        Main.theApp = this;
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void init() throws Exception {
        System.out.println("Init...");
        System.out.println(
            "Java   version: " + System.getProperty("java.version")
        );
        System.out.println(
            "JavaFX version: " + System.getProperty("javafx.version")
        );
        System.out.println(
            "JavaFX Runtime: " + System.getProperty("javafx.runtime.version")
        );
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
        rootStage = stage;
        System.out.println("Start...");
        stage.setTitle("ID3Manager 2");

        root = new StackPane(createApplicationFrame());
        Scene scene = new Scene(
            root,
            getConfiguration().getWidth(),
            getConfiguration().getHeight()
        );
        scene.getRoot().setStyle(getFontStyle());
        stage.setScene(scene);
        stage.setOnShowing(e -> {
            directoryTree.expandToPath(
                Main.theApp.getConfiguration().getCurrentPath()
            );
        });
        switch (getConfiguration().getTheme()) {
            case Configuration.THEME_DARK:
                Application.setUserAgentStylesheet(
                    new PrimerDark().getUserAgentStylesheet()
                );
                break;
            default:
                Application.setUserAgentStylesheet(
                    new PrimerLight().getUserAgentStylesheet()
                );
                break;
        }
        /*
        if (!"".equals(getConfiguration().getSkin())) {
            File f = new File(getConfiguration().getSkin());
            if (f.exists()) {
                System.out.println("Set skin to " + f.getAbsolutePath());
                scene
                    .getStylesheets()
                    .add("file:///" + f.getAbsolutePath().replace("\\", "/"));
            }
        }
        */
        initialization = false;
        stage.show();
    }

    private VBox createApplicationFrame() {
        VBox main = new VBox();
        main
            .getChildren()
            .addAll(
                createMenubar(),
                createToolbar(),
                createMainArea(),
                createStatusbar()
            );

        return main;
    }

    private MenuBar createMenubar() {
        Menu file = new Menu(Messages.getString("file.label"));
        if (os != null && os.startsWith("Mac")) {
            file.getItems().addAll(SaveHandler.getInstance().getMenuItem());
        } else {
            file
                .getItems()
                .addAll(
                    SaveHandler.getInstance().getMenuItem(),
                    new SeparatorMenuItem(),
                    QuitHandler.getInstance().getMenuItem()
                );
        }
        Menu edit = new Menu(Messages.getString("edit.label"));
        edit
            .getItems()
            .addAll(
                UndoHandler.getInstance().getMenuItem(),
                RedoHandler.getInstance().getMenuItem(),
                new SeparatorMenuItem(),
                CopyHandler.getInstance().getMenuItem(),
                CutHandler.getInstance().getMenuItem(),
                PasteHandler.getInstance().getMenuItem(),
                new SeparatorMenuItem(),
                SelectAllHandler.getInstance().getMenuItem()
            );

        Menu song = new Menu(Messages.getString("song.label"));
        song
            .getItems()
            .addAll(
                EditHandler.getInstance().getMenuItem(),
                RenameHandler.getInstance().getMenuItem(),
                ParseHandler.getInstance().getMenuItem(),
                new SeparatorMenuItem(),
                RemoveID3V1Handler.getInstance().getMenuItem(),
                RemoveID3V2Handler.getInstance().getMenuItem(),
                RemoveCommentHandler.getInstance().getMenuItem(),
                RemoveUnderscoreFromFilenameHandler.getInstance().getMenuItem(),
                new SeparatorMenuItem(),
                RenameFileToCamelcaseHandler.getInstance().getMenuItem(),
                RenameTagsToCamelcaseHandler.getInstance().getMenuItem(),
                NumberTracksHandler.getInstance().getMenuItem(),
                new SeparatorMenuItem(),
                PlayHandler.getInstance().getMenuItem()
            );

        Menu help = new Menu(Messages.getString("help.label"));
        help.getItems().addAll(AboutHandler.getInstance().getMenuItem());

        Menu view = createViewMenu();
        Menu theme = new Menu(Messages.getString("theme.label"));
        theme
            .getItems()
            .addAll(
                LightThemeHandler.getInstance().getMenuItem(),
                DarkThemeHandler.getInstance().getMenuItem()
            );
        Menu fontSize = new Menu(Messages.getString("font.size.label"));
        fontSize
            .getItems()
            .addAll(
                SmallFontHandler.getInstance().getMenuItem(),
                NormalFontHandler.getInstance().getMenuItem(),
                LargeFontHandler.getInstance().getMenuItem()
            );
        view.getItems().addAll(theme, fontSize);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(file, edit, view, song, help);

        if (os != null && os.startsWith("Mac")) menuBar
            .useSystemMenuBarProperty()
            .set(true);

        return menuBar;
    }

    private Menu createViewMenu() {
        Menu view = new Menu(Messages.getString("view.label"));
        Menu skins = new Menu(Messages.getString("skins.label"));
        String[] files = new File("skins").list();
        for (int i = 0; i < files.length; i++) {
            if (files[i].endsWith(".css")) {
                String skinName = files[i].substring(0, files[i].length() - 4);
                MenuItem skin = SkinHandler.getInstance(skinName).getMenuItem();
                skins.getItems().add(skin);
            }
        }
        view.getItems().addAll(skins);
        return view;
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
            ParseHandler.getInstance().getToolbarButton(),
            new Separator(),
            PlayHandler.getInstance().getToolbarButton()
        );
    }

    private HBox createStatusbar() {
        HBox statusBar = new HBox();
        statusBar.setPadding(new Insets(10, 10, 10, 10));
        Pane gap = new Pane();
        HBox.setHgrow(gap, Priority.ALWAYS);
        Label zoomLabel = new Label(String.format("%d%%", configuration.getZoom()));
        zoomLabel.setPadding(new Insets(0, 10, 0, 10));
        Slider zoomSlider = new Slider(50, 300, configuration.getZoom());
        zoomSlider.setPadding(new Insets(10, 0,0,0));
        zoomSlider.setBlockIncrement(1.0);
        zoomSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            zoomLabel.setText(String.format("%d%%", newValue.intValue()));
            configuration.setZoom(newValue.intValue());
            root.setStyle(getFontStyle());
        });
        statusBar.getChildren().addAll(new Label("Ready."), gap, zoomSlider, zoomLabel);
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
        if (initialization) return;
        root.getChildren().get(0).setDisable(true);
        root.getChildren().add(node);
    }

    public void hideOverlay(Node node) {
        if (initialization) return;
        root.getChildren().remove(node);
        root.getChildren().getFirst().setDisable(false);
    }

    public Configuration getConfiguration() {
        if (configuration == null) configuration = Configuration.newInstance();
        return configuration;
    }

    public Song[] getSelectedSongs() {
        List<Song> songs =
            this.songTable.getSelectionModel()
                .getSelectedItems()
                .stream()
                .collect(Collectors.toList());
        return songs.toArray(new Song[songs.size()]);
    }

    public String getFontStyle() {
        return String.format("-fx-font-size: %d.%dem", configuration.getZoom()/100, configuration.getZoom()%100);
    }

    public int getZoom() {
        return configuration.getZoom();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
