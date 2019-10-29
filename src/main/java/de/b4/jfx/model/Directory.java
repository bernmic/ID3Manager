package de.b4.jfx.model;

import java.io.File;

public class Directory {
  private File file;
  private String name;

  public Directory(File file, String name) {
    this.file = file;
    this.name = name;
  }

  public File getFile() {
    return file;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return name;
  }
}
