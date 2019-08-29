package de.b4.jfx;

import de.b4.jfx.model.Directory;
import de.b4.jfx.model.DirectoryTreeItem;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class DirectoryTree extends TreeView<Directory> implements ObservableValue<Directory> {
  private Set<InvalidationListener> invalidationListener = new HashSet<>();
  private Set<ChangeListener<? super Directory>> selectionChangeListener = new HashSet<>();
  private Directory currentSelected = null;

  private DirectoryTree() {
  }

  private DirectoryTree(TreeItem<Directory> treeItem) {
    super(treeItem);
  }

  public static DirectoryTree newInstance() {
    DirectoryTree treeView = new DirectoryTree(new TreeItem<>(new Directory(null, "root")));
    treeView.setShowRoot(false);
    for (File f : File.listRoots()) {
      String name = FileSystemView.getFileSystemView().getSystemDisplayName(f);
      if (name == null || "".equals(name)) {
        name = f.getAbsolutePath();
      }
      DirectoryTreeItem item = new DirectoryTreeItem(new Directory(f, name));
      treeView.getRoot().getChildren().add(item);
    }
    treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<Directory>>() {
      @Override
      public void changed(ObservableValue<? extends TreeItem<Directory>> observableValue, TreeItem<Directory> oldItem, TreeItem<Directory> newItem) {
        if (newItem != null) {
          for (InvalidationListener i : treeView.invalidationListener) {
            i.invalidated(treeView);
          }
          for (ChangeListener<? super Directory> c : treeView.selectionChangeListener) {
            c.changed(treeView, treeView.currentSelected, newItem.getValue());
          }
          treeView.currentSelected = newItem.getValue();
          Main.theApp.getConfiguration().setCurrentPath(treeView.currentSelected.getFile().getAbsolutePath());
        } else {
          treeView.currentSelected = null;
          Main.theApp.getConfiguration().setCurrentPath("");
        }
      }
    });
    return treeView;
  }

  @Override
  public void addListener(InvalidationListener invalidationListener) {
    this.invalidationListener.add(invalidationListener);
  }

  @Override
  public void removeListener(InvalidationListener invalidationListener) {
    this.invalidationListener.remove(invalidationListener);
  }

  @Override
  public void addListener(ChangeListener<? super Directory> changeListener) {
    this.selectionChangeListener.add(changeListener);
  }

  @Override
  public void removeListener(ChangeListener<? super Directory> changeListener) {
    this.selectionChangeListener.remove(changeListener);
  }

  @Override
  public Directory getValue() {
    return currentSelected;
  }

  public void expandToPath(String path) {
    expandPath(this.getRoot(), path);
  }

  private void expandPath(TreeItem<Directory> item, String path) {
    for (TreeItem<Directory> child : item.getChildren()) {
      String p = child.getValue().getFile().getAbsolutePath();
      if (p.equals(path)) {
        this.getSelectionModel().select(child);
        return;
      }
      if (path.startsWith(p)) {
        child.setExpanded(true);
        expandPath(child, path);
      }
    }
  }
}
