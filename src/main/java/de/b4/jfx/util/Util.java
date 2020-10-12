package de.b4.jfx.util;

public class Util {
  public static Integer getTrack(String s) {
    if (s == null || s.length() == 0)
      return 0;
    String[] sp = s.split("/");
    if (sp.length == 0)
      return 0;
    return Integer.valueOf(sp[0]);
  }

  public static String removeExtension(String s) {

    String separator = System.getProperty("file.separator");
    String filename;

    // Remove the path upto the filename.
    int lastSeparatorIndex = s.lastIndexOf(separator);
    if (lastSeparatorIndex == -1) {
      filename = s;
    } else {
      filename = s.substring(lastSeparatorIndex + 1);
    }

    // Remove the extension.
    int extensionIndex = filename.lastIndexOf(".");
    if (extensionIndex == -1)
      return filename;

    return filename.substring(0, extensionIndex);
  }
}
