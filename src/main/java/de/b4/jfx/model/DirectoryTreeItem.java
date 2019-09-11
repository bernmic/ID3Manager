package de.b4.jfx.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.io.File;
import java.util.Arrays;

public class DirectoryTreeItem extends TreeItem<Directory> {
  private boolean firstTimeChildren = true;
  private boolean hasChildren = true;

  public DirectoryTreeItem(Directory directory) {
    super(directory);
  }

  @Override
  public ObservableList<TreeItem<Directory>> getChildren() {
    if (firstTimeChildren) {
      firstTimeChildren = false;
      super.getChildren().addAll(buildChildrenList());
    }
    return super.getChildren();
  }

  private ObservableList<TreeItem<Directory>> buildChildrenList() {
    ObservableList<TreeItem<Directory>> children = FXCollections.observableArrayList();
    if (this.getValue() != null && this.getValue().getFile() != null) {
      File[] files = this.getValue().getFile().listFiles();
      if (files != null) {
        Arrays.sort(files);
        for (File f : files) {
          if (f.isDirectory() && !f.isHidden()) {
            children.add(new DirectoryTreeItem(new Directory(f, f.getName())));
          }
        }
      }
    }
    if (children.isEmpty()) {
      hasChildren = false;
    }
    return children;
  }

  @Override
  public boolean isLeaf() {
    return !hasChildren;
  }
}
