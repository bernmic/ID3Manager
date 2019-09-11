package de.b4.jfx.util;

public class Util {
  public static Integer getTrack(String s) {
    if (s == null || s.length() == 0)
      return new Integer(0);
    String[] sp = s.split("/");
    if (sp.length == 0)
      return new Integer(0);
    return Integer.valueOf(sp[0]);
  }
}
