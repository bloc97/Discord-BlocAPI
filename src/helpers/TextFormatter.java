/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

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
}
