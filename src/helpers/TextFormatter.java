/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.util.Date;
import sx.blah.discord.api.internal.json.objects.EmbedObject.FooterObject;

/**
 *
 * @author bowen
 */
public abstract class TextFormatter {
    public static String formatCapitalUnderscore(String s) {
        String string = "";
        String[] sArr = s.split("_");
        for (String ss : sArr) {
            string += ss.substring(0, 1) + ss.substring(1).toLowerCase() + " ";
        }
        if (string.length() < 1) {
            return "None";
        }
        if (string.endsWith(" ")) {
            string = string.substring(0, string.length()-1);
        }
        return string;
    }
    public static String formatInput(String s) {
        char[] cArr = s.toCharArray();
        String formattedString = "";
        for (int i=0; i<cArr.length; i++) {
            if (isCharTextASCII(cArr[i])) {
                formattedString += cArr[i];
            }
        }
        return formattedString;
    }
    
    public static boolean isCharTextASCII(char c) {
        return (c == 10 || c == 12 || c == 13 || (c >= 32 && c <= 126));
        //10, '\n' newline
        //12, '\f' newpage
        //13, '\r' return to beginning of line
    }
    
    public static boolean isCharLetterASCII(char c) {
        return ((c >= 65 && c <= 90) || (c >= 97 && c <= 122));
    }
    
    public static boolean isCharNumberASCII(char c) {
        return ((c >= 48 && c <= 57));
    }
    
    public static String formatNounOutput(String s) { //Formats a unformatted noun for output (capitalize first letter)
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
    
    public static String formatOutput(String s) {
        if (s.isEmpty()) {
            return "None";
        }
        return s;
    }
    
    public static String join(String[] array) {
        String string = "";
        for (String s : array) {
            string = string.concat(s);
        }
        return string;
    }
    public static String join(String[] array, Character c) {
        if (c == null) {
            c = ' ';
        }
        String string = "";
        for (String s : array) {
            string = string.concat(s + c);
        }
        return string.substring(0, string.length()-1);
    }
    
    public static int bound(int i, int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Minimum value cannot be bigger than maximum.");
        }
        if (i < min) {
            i = min;
        }
        if (i > max) {
            i = max;
        }
        return i;
    }
    
    public static int boundExclude(int i, int min, int limit) {
        if (min >= limit) {
            throw new IllegalArgumentException("Minimum value cannot be bigger or equal than limit.");
        }
        if (i < min) {
            i = min;
        }
        if (i >= limit) {
            i = limit - 1;
        }
        return i;
    }
    
}
