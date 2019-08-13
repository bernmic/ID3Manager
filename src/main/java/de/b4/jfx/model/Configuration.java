package de.b4.jfx.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Configuration {
  public final static String CONFIG_FILE = "config.properties";

  private boolean defaultValues = true;
  private int iconSize = 16;
  private String iconColor = "";
  private int width = 800, height = 800;
  private String currentPath = "";

  public static Configuration newInstance() {
    Configuration configuration = new Configuration();

    Properties properties = null;
    if (Files.exists(Paths.get(CONFIG_FILE))) {
      properties = configuration.load(CONFIG_FILE);
      configuration.defaultValues = false;
    }
    if (properties == null) {
      String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
      String appConfigPath = rootPath + CONFIG_FILE;
      properties = configuration.load(appConfigPath);
    }
    if (properties != null) {
      configuration.parse(properties);
    }
    return configuration;
  }

  private Properties load(String filename) {
    System.out.println("Load config.properties from " + filename);
    Properties properties = new Properties();
    try {
      properties.load(new FileInputStream(filename));
    } catch (IOException e) {
      System.out.println("config.prooperties not found at " + filename);
      return null;
    }
    return properties;
  }

  private void parse(Properties properties) {
    this.setWidth(Integer.parseInt(properties.getProperty("window.width", "800")));
    this.setHeight(Integer.parseInt(properties.getProperty("window.height", "600")));
    this.setIconSize(Integer.parseInt(properties.getProperty("icon.size", "24")));
    this.setIconColor(properties.getProperty("icon.color", ""));
    this.setCurrentPath(properties.getProperty("path", ""));
  }

  public void save() {
    Properties properties = new Properties();
    properties.setProperty("window.width", String.valueOf(getWidth()));
    properties.setProperty("window.height", String.valueOf(getHeight()));
    properties.setProperty("icon.size", String.valueOf(getIconSize()));
    properties.setProperty("icon.color", getIconColor());
    properties.setProperty("path", getCurrentPath());
    try {
      properties.store(new FileOutputStream(CONFIG_FILE), " JFX config file");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public String getIconCode(String icon) {
    String iconCode = String.format("%s:%d", icon, iconSize);
    if (iconColor != null && iconColor.length() > 0) {
      iconCode += (":" + iconColor);
    }
    return iconCode;
  }

  public int getIconSize() {
    return iconSize;
  }

  public void setIconSize(int iconSize) {
    this.iconSize = iconSize;
  }

  public String getIconColor() {
    return iconColor;
  }

  public void setIconColor(String iconColor) {
    this.iconColor = iconColor;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public String getCurrentPath() {
    return currentPath;
  }

  public void setCurrentPath(String currentPath) {
    this.currentPath = currentPath;
  }
}
