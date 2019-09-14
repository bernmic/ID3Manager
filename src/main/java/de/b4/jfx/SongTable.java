package de.b4.jfx;

import de.b4.jfx.handler.EditHandler;
import de.b4.jfx.model.Directory;
import de.b4.jfx.model.Song;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SongTable extends TableView<Song> {
    private Directory currentDirectory;

    private SongTable() {
    }

    public static SongTable newInstance() {
        SongTable tableView = new SongTable();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView.getColumns().addAll(
                tableView.createTableColumn("Filename", "filename", 15),
                tableView.createTableColumn("Title", "title", 20),
                tableView.createTableColumn("Artist", "artist", 20),
                tableView.createTableColumn("Album", "album", 20),
                tableView.createTableColumn("Track", "track", 4),
                tableView.createTableColumn("Year", "year", 5),
                tableView.createTableColumn("Genre", "genre", 10),
                tableView.createTableColumn("ID3Version", "iD3Version", 10)
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

        return tableView;
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
            ProgressBar pb = new ProgressBar();
            Label status = new Label("");
            status.setMinWidth(300);
            VBox box = new VBox(pb, status);
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
                    List<Song> songs = new ArrayList<>();
                    if (directory != null && directory.getFile() != null) {
                        File[] files = directory.getFile().listFiles();
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
    }
}
