package de.b4.jfx.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Configuration {

    public static final String CONFIG_FILE = "config.properties";
    public static final String THEME_LIGHT = "light";
    public static final String THEME_DARK = "dark";
    public static final String FONT_SIZE_SMALL = "small";
    public static final String FONT_SIZE_NORMAL = "normal";
    public static final String FONT_SIZE_LARGE = "large";
    private boolean defaultValues = true;
    private int iconSize = 16;
    private String iconColor = "";
    private int width = 800, height = 800;
    private String currentPath = "";
    private String renamePattern = "";
    private String parsePattern = "";
    private String skin = "";
    private String theme = THEME_LIGHT; // one of dark or light
    private String fontSize = FONT_SIZE_NORMAL; // one of small, normal or large
    private boolean readRecursive = false;

    public static Configuration newInstance() {
        Configuration configuration = new Configuration();

        Properties properties = null;
        if (Files.exists(Paths.get(CONFIG_FILE))) {
            properties = configuration.load(CONFIG_FILE);
            configuration.defaultValues = false;
        }
        if (
            properties == null &&
            Thread.currentThread().getContextClassLoader().getResource("") !=
            null
        ) {
            String rootPath = Thread.currentThread()
                .getContextClassLoader()
                .getResource("")
                .getPath();
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
        this.setWidth(
                Integer.parseInt(properties.getProperty("window.width", "800"))
            );
        this.setHeight(
                Integer.parseInt(properties.getProperty("window.height", "600"))
            );
        this.setIconSize(
                Integer.parseInt(properties.getProperty("icon.size", "24"))
            );
        this.setIconColor(properties.getProperty("icon.color", ""));
        this.setCurrentPath(properties.getProperty("path", ""));
        this.setRenamePattern(properties.getProperty("pattern.rename", ""));
        this.setParsePattern(properties.getProperty("pattern.parse", ""));
        this.setSkin(properties.getProperty("skin", ""));
        this.setTheme(properties.getProperty("theme", THEME_LIGHT));
        this.setFontSize(properties.getProperty("font.size", FONT_SIZE_NORMAL));
        this.setReadRecursive(
                Boolean.parseBoolean(
                    properties.getProperty("recursive", "false")
                )
            );
    }

    public void save() {
        Properties properties = new Properties();
        properties.setProperty("window.width", String.valueOf(getWidth()));
        properties.setProperty("window.height", String.valueOf(getHeight()));
        properties.setProperty("icon.size", String.valueOf(getIconSize()));
        properties.setProperty("icon.color", getIconColor());
        properties.setProperty("path", getCurrentPath());
        properties.setProperty("pattern.rename", getRenamePattern());
        properties.setProperty("pattern.parse", getParsePattern());
        properties.setProperty("skin", getSkin());
        properties.setProperty("theme", getTheme());
        properties.setProperty("font.size", getFontSize());
        properties.setProperty("recursive", String.valueOf(isReadRecursive()));
        try {
            properties.store(
                new FileOutputStream(CONFIG_FILE),
                " JFX config file"
            );
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

    public String getRenamePattern() {
        return renamePattern;
    }

    public void setRenamePattern(String renamePattern) {
        this.renamePattern = renamePattern;
    }

    public String getParsePattern() {
        return parsePattern;
    }

    public void setParsePattern(String parsePattern) {
        this.parsePattern = parsePattern;
    }

    public boolean isReadRecursive() {
        return readRecursive;
    }

    public void setReadRecursive(boolean b) {
        this.readRecursive = b;
    }

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getFontSize() {
        return fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }
}
