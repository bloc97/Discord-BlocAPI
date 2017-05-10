/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.util.Date;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.api.internal.json.objects.EmbedObject.FooterObject;
import sx.blah.discord.handle.impl.obj.Embed;
import sx.blah.discord.handle.impl.obj.Embed.EmbedFooter;

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
            if (isTextASCII(cArr[i])) {
                formattedString += cArr[i];
            }
        }
        return formattedString;
    }
    
    public static boolean isTextASCII(char c) {
        return (c == 10 || c == 12 || c == 13 || (c >= 32 && c <= 126));
        //10, '\n' newline
        //12, '\f' newpage
        //13, '\r' return to beginning of line
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
    public static FooterObject getSummonerEmbedFooter(long summonerId, long accountId, long lastDate) {
        return new FooterObject(summonerId + " | " + accountId + "\u2003\u2003" + "Last Activity: " + new Date(lastDate).toString(), "", "");
    }
    
}
