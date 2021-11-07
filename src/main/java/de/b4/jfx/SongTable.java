package de.b4.jfx;

import de.b4.jfx.handler.*;
import de.b4.jfx.model.Directory;
import de.b4.jfx.model.Song;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SongTable extends TableView<Song> {
    private Directory currentDirectory;

    private SongTable() {
    }

    public static SongTable newInstance() {
        SongTable tableView = new SongTable();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView.getColumns().addAll(
                tableView.createTableColumn(Messages.getString("column.filename.label"), "filename", 15),
                tableView.createTableColumn(Messages.getString("column.title.label"), "title", 20),
                tableView.createTableColumn(Messages.getString("column.artist.label"), "artist", 20),
                tableView.createTableColumn(Messages.getString("column.album.label"), "album", 20),
                tableView.createTableColumn(Messages.getString("column.track.label"), "track", 4),
                tableView.createTableColumn(Messages.getString("column.year.label"), "year", 5),
                tableView.createTableColumn(Messages.getString("column.genre.label"), "genre", 10),
                tableView.createTableColumn(Messages.getString("column.id3version.label"), "iD3Version", 10)
        );
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            Main.somethingSelected.setValue(!tableView.getSelectionModel().isEmpty());
        });

        tableView.setRowFactory(tv -> {
            TableRow<Song> tableRow = new TableRow<>() {
                @Override
                protected void updateItem(Song song, boolean b) {
                    super.updateItem(song, b);
                    if (song == null) {
                        setStyle("");
                    } else if (!this.isSelected() && song.isDirty()) {
                        setStyle("-fx-background-color: lightyellow;");
                    } else {
                        setStyle("");
                    }
                }
            };
            tableRow.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!tableRow.isEmpty())) {
                    EditHandler.getInstance().fireEvent();
                }
            });
            return tableRow;
        });
        tableView.setContextMenu(tableView.createContextMenu());
        return tableView;
    }

    private ContextMenu createContextMenu() {
        ContextMenu menu = new ContextMenu(
                ((EditHandler)EditHandler.getInstance()).createMenuItem(),
                ((RenameHandler)RenameHandler.getInstance()).createMenuItem(),
                ((ParseHandler)ParseHandler.getInstance()).createMenuItem(),
                new SeparatorMenuItem(),
                ((RemoveID3V1Handler)RemoveID3V1Handler.getInstance()).createMenuItem(),
                ((RemoveID3V2Handler)RemoveID3V2Handler.getInstance()).createMenuItem(),
                ((RemoveCommentHandler)RemoveCommentHandler.getInstance()).createMenuItem(),
                ((RemoveUnderscoreFromFilenameHandler)RemoveUnderscoreFromFilenameHandler.getInstance()).createMenuItem(),
                new SeparatorMenuItem(),
                ((RenameFileToCamelcaseHandler)RenameFileToCamelcaseHandler.getInstance()).createMenuItem(),
                ((RenameTagsToCamelcaseHandler)RenameTagsToCamelcaseHandler.getInstance()).createMenuItem(),
                ((NumberTracksHandler)NumberTracksHandler.getInstance()).createMenuItem(),
                new SeparatorMenuItem(),
                ((PlayHandler)PlayHandler.getInstance()).createMenuItem());
        return menu;
    }

    private TableColumn<Song, String> createTableColumn(String title, String property, int width) {
        TableColumn<Song, String> column = new TableColumn<>(title);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        column.setMaxWidth(1f * Integer.MAX_VALUE * width);
        return column;
    }

    public void connectTree(DirectoryTree tree) {
        tree.addListener((observableValue, directory, t1) -> {
            currentDirectory = t1;

            //ProgressIndicator pi = new ProgressIndicator();
            Label title = new Label("");
            ProgressBar pb = new ProgressBar();
            Label status = new Label("");
            status.setMinWidth(300);
            VBox box = new VBox(title, pb, status);
            box.setAlignment(Pos.CENTER);

            Main.theApp.showOverlay(box);
            SelectionChangedService service = new SelectionChangedService();
            service.setDirectory(currentDirectory);
            service.setOnSucceeded(e -> {
                List<Song> songs = (List<Song>) e.getSource().getValue();
                getItems().clear();
                getItems().addAll(songs);
                Main.theApp.hideOverlay(box);
            });
            title.textProperty().bind(service.titleProperty());
            pb.progressProperty().bind(service.progressProperty());
            status.textProperty().bind(service.messageProperty());
            service.start();
        });
    }

    private static class SelectionChangedService extends Service<List<Song>> {
        private Directory directory;

        public void setDirectory(Directory directory) {
            this.directory = directory;
        }

        @Override
        protected Task<List<Song>> createTask() {
            return new Task<>() {
                @Override
                protected List<Song> call() throws Exception {
                    updateTitle(Messages.getString("loading.label") + " " + directory.getName());
                    List<Song> songs = new ArrayList<>();
                    if (directory != null && directory.getFile() != null) {
                        File[] files = getSongFiles(); // directory.getFile().listFiles();
                        if (files != null) {
                            Arrays.sort(files);
                            int i = 0, count = files.length;
                            for (File f : files) {
                                updateMessage(f.getName());
                                updateProgress(i, count);
                                i++;
                                if (f.isFile() && f.getName().toLowerCase().endsWith(".mp3")) {
                                    songs.add(new Song(f));
                                }
                            }
                        }
                    }
                    return songs;
                }
            };
        }
        private File[] getSongFiles() throws IOException {
            if (Main.theApp.getConfiguration().isReadRecursive()) {
                List<File> files = new ArrayList<>();
                Files.find(Paths.get(directory.getFile().getAbsolutePath()), 2, (filePath, fileAttr) -> {
                    return fileAttr.isRegularFile() && filePath.toString().toLowerCase().endsWith(".mp3");
                }).forEach(p -> files.add(p.toFile()));
                Collections.sort(files);
                return files.toArray(new File[0]);
            }
            else {
                return directory.getFile().listFiles();
            }
        }
    }
}
