package de.b4.jfx.util;

import java.util.Arrays;

public class StringUtils {
    public static String replace(String str, String from, String to) {
        return str.replaceAll(from, to);
    }

    public static String capitalizeFully(String str, char... delimiters) {
        String del = " ";
        if (delimiters != null) {
            del = new String(delimiters);
        }
        StringBuilder result = new StringBuilder("");
        boolean inWord = false;
        int start = 0, end = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (del.indexOf(c) >= 0) {
                if (inWord) {
                    // word ends here, capitalize and append
                    inWord = false;
                    result.append(capitalize(str.substring(start, i)));
                    result.append(c);
                }
                else {
                    result.append(c);
                }
            }
            else {
                if (!inWord) {
                    // word starts here
                    inWord = true;
                    start = i;
                }
            }
        }
        if (inWord) {
            result.append(capitalize(str.substring(start, str.length())));
        }
        return result.toString();
    }

    private static String capitalize(String str) {
        if (str == null)
            return null;
        if (str.length() == 0)
            return str;
        return str.substring(0,1).toUpperCase() + str.substring(1).toLowerCase();
    }
}