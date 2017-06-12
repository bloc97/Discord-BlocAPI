/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import container.StringAdvancedContainer;
import container.ParameterStringContainer;
import container.StringContainer;
import container.TokenContainer;
import helpers.NumberUtils;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author bowen
 */
public abstract class ParserUtils {
    
    /**
     * Checks if string can be represented as a number.
     * @param string
     * @return
     */
    public static boolean isDecimalNumber(String string) {
        try {
            Double.parseDouble(string);
            Long.parseLong(string);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
    
    /**
     * Checks if string can be represented as a number.
     * @param string
     * String to be parsed
     * @param radix
     * 
     * @return
     */
    public static boolean isRadixNumber(String string, int radix) {
        try {
            Long.parseLong(string, radix);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
    
    /**
     * Checks if a string starts with another string, and does not 
     * @param rawString
     * @param string
     * @return
     */
    public static boolean startsWithCaseless(String rawString, String string) {
        return rawString.toLowerCase().startsWith(string.toLowerCase());
    }
    
    /**
     * Gets the first string in stringList found in the string.
     * @param string
     * Main String used in search
     * @param stringList
     * Smaller strings used to search in the main string
     * @return
     */
    public static String getFirst(String string, String[] stringList) {
        
        int lowestIndex = Integer.MAX_VALUE;
        String found = "";
        
        for (String s : stringList) {
            int thisIndex = string.indexOf(s);
            if (thisIndex >= 0 && thisIndex < lowestIndex) {
                lowestIndex = thisIndex;
                found = s;
            }
        }
        return found;
    }
    
    public static String getFirstCaseless(String string, String[] stringList) {
        string = string.toLowerCase(); //caseless
        int lowestIndex = Integer.MAX_VALUE;
        String found = "";
        
        for (String s : stringList) {
            s = s.toLowerCase(); //caseless
            int thisIndex = string.indexOf(s);
            if (thisIndex >= 0 && thisIndex < lowestIndex) {
                lowestIndex = thisIndex;
                found = s;
            }
        }
        return found;
    }
    
    
    public static int findPrefix(String string, String[] prefixList) {
        return findPrefix(string, prefixList, false);
    }

    public static int findSuffix(String string, String[] suffixList) {
        return findSuffix(string, suffixList, false);
    }
    
    public static String getPrefix(String string, String[] prefixList) {
        return getPrefix(string, prefixList, false);
    }

    public static String getSuffix(String string, String[] suffixList) {
        return getSuffix(string, suffixList, false);
    }
    
    public static boolean checkPrefix(String string, String[] prefixList) {
        return checkPrefix(string, prefixList, false);
    }

    public static boolean checkSuffix(String string, String[] suffixList) {
        return checkSuffix(string, suffixList, false);
    }
    
    public static String trimPrefixSuffix(String string, String[] prefixList, String[] suffixList) {
        return trimPrefixSuffix(string, prefixList, suffixList, false);
    }
    
    public static int findPrefix(String string, String[] prefixList, boolean isCaseless) {
        if (isCaseless) string = string.toLowerCase(); //Do not care about case
        for (int i=0; i<prefixList.length; i++) {
            String s = prefixList[i];
            if (isCaseless) s = s.toLowerCase(); //Do not care about case
            if (string.startsWith(s)) {
                return i;
            }
        }
        return -1;
    }
    
    public static int findSuffix(String string, String[] suffixList, boolean isCaseless) {
        if (isCaseless) string = string.toLowerCase(); //Check caseless
        for (int i=0; i<suffixList.length; i++) {
            String s = suffixList[i];
            if (isCaseless) s = s.toLowerCase(); //Check caseless
            if (string.endsWith(s)) {
                return i;
            }
        }
        return -1;
    }

    public static String getPrefix(String string, String[] prefixList, boolean isCaseless) {
        int i = findPrefix(string, prefixList, isCaseless);
        return (i >= 0) ? prefixList[i] : "";
    }

    public static String getSuffix(String string, String[] suffixList, boolean isCaseless) {
        int i = findSuffix(string, suffixList, isCaseless);
        return (i >= 0) ? suffixList[i] : "";
    }

    public static boolean checkPrefix(String string, String[] prefixList, boolean isCaseless) {
        if (prefixList.length == 0) {
            return true;
        }
        int i = findPrefix(string, prefixList, isCaseless);
        return i >= 0;
    }

    public static boolean checkSuffix(String string, String[] suffixList, boolean isCaseless) {
        if (suffixList.length == 0) {
            return true;
        }
        int i = findSuffix(string, suffixList, isCaseless);
        return i >= 0;
    }
    
    public static String trimPrefixSuffix(String string, String[] prefixList, String[] suffixList, boolean isCaseless) {
        int pl = getPrefix(string, prefixList, isCaseless).length();
        int sl = getSuffix(string, suffixList, isCaseless).length();

        return trimPrefixSuffixLength(string, pl, sl);
    }
    
    public static String trimPrefixSuffixLength(String string, int prefixLength, int suffixLength) {
        return string.substring(prefixLength, string.length() - suffixLength);
    }

    public static List<String> tokenizeString(String command, char[] separatorList) {
        Set<Character> separators = new LinkedHashSet();
        for (Character c : separatorList) {
            if (c != '"' && c != '-') {
                //Ignore double quotes and -
                separators.add(c);
            }
        }
        LinkedList<String> parsedCommand = new LinkedList();
        String currentString = "";
        boolean isInQuotes = false;
        boolean isInListQuote = false;
        int listDepth = 0;
        for (int i = 0; i < command.length(); i++) {
            char c = command.charAt(i);
            if (isInQuotes) {
                if (c == '"') {
                    isInQuotes = false;
                    parsedCommand.addLast(currentString);
                    currentString = "";
                } else {
                    currentString = currentString + c;
                }
            } else if (listDepth > 0) {
                if (isInListQuote) {
                    if (c == '"') {
                        isInListQuote = false;
                    }
                    currentString = currentString + c;
                } else {
                    if (c == ']') {
                        listDepth--;
                        currentString = currentString + c;
                        if (listDepth == 0) {
                            parsedCommand.addLast(currentString);
                            currentString = "";
                        }
                    } else if (c == '[') {
                        listDepth++;
                        currentString = currentString + c;
                    } else if (c == '"') {
                        isInListQuote = true;
                        currentString = currentString + c;
                    } else {
                        currentString = currentString + c;
                    }
                }
            } else {
                if (separators.contains(c)) {
                    if (currentString.length() > 0) {
                        parsedCommand.addLast(currentString);
                        currentString = "";
                    }
                } else if (c == '"') {
                    isInQuotes = true;
                } else if (c == '[') {
                    listDepth++;
                    currentString = currentString + c;
                } else {
                    currentString = currentString + c;
                }
            }
        }
        if (currentString.length() > 0) {
            parsedCommand.addLast(currentString);
            currentString = "";
        }
        return new ArrayList(parsedCommand);
    }

    public static String untokenizeString(List<String> content, char[] separatorList) {
        String string = "--";
        for (String s : content) {
            if (containsCharacter(s, separatorList)) {
                string = string + " \"" + s + "\"";
            } else {
                string = string + " " + s;
            }
        }
        return string;
    }
    
    public static List<String> retrieveParameters(List<String> content, char[] separatorList, Set<String> flags, List<ParameterStringContainer> parameters) {
        
        LinkedList<String> contentsToRemove = new LinkedList();
        
        boolean isInParameter = false;
        LinkedList<String> lastParameter = new LinkedList();
        
        for (String stringToken : content) {
            if (isInParameter) {
                if (stringToken.startsWith("--")) {
                    parameters.add(new ParameterStringContainer(lastParameter, separatorList));
                    lastParameter = new LinkedList();
                    lastParameter.add(stringToken.substring(2));
                    contentsToRemove.add(stringToken);
                } else if (stringToken.startsWith("-")) {
                    isInParameter = false;
                    parameters.add(new ParameterStringContainer(lastParameter, separatorList));
                    lastParameter = new LinkedList();
                    flags.add(stringToken.substring(1));
                    contentsToRemove.add(stringToken);
                } else {
                    lastParameter.add(stringToken);
                    contentsToRemove.add(stringToken);
                }
                
            } else {
                if (stringToken.startsWith("--")) {
                    isInParameter = true;
                    lastParameter.add(stringToken.substring(2));
                    contentsToRemove.add(stringToken);
                } else if (stringToken.startsWith("-")) {
                    flags.add(stringToken.substring(1));
                    contentsToRemove.add(stringToken);
                }
            }
        }
        
        LinkedList<String> newContent = new LinkedList(content);
        
        for (String toRemove : contentsToRemove) {
            newContent.remove(toRemove);
        }
        return new ArrayList(newContent);
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
        return string.substring(0, string.length() - 1);
    }

    public static String fillBegin(String string, char c, int finalLength) {
        while (string.length() < finalLength) {
            string = c + string;
        }
        return string;
    }

    public static boolean containsCharacter(String string, char[] characters) {
        for (char c : characters) {
            if (string.indexOf(c) != -1) {
                return true;
            }
        }
        return false;
    }

    public static String formatNounOutput(String s) {
        //Formats a unformatted noun for output (capitalize first letter)
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    public static String repeatString(String s, int n) {
        String string = "";
        for (int i = 0; i < n; i++) {
            string += s;
        }
        return string;
    }

    public static String fillEnd(String string, char c, int finalLength) {
        while (string.length() < finalLength) {
            string = string + c;
        }
        return string;
    }

    public static boolean isCharNumberASCII(char c) {
        return c >= 48 && c <= 57;
    }

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
            string = string.substring(0, string.length() - 1);
        }
        return string;
    }

    public static String formatInput(String s) {
        char[] cArr = s.toCharArray();
        String formattedString = "";
        for (int i = 0; i < cArr.length; i++) {
            if (isCharTextASCII(cArr[i])) {
                formattedString += cArr[i];
            }
        }
        return formattedString;
    }

    public static String formatOutput(String s) {
        if (s.isEmpty()) {
            return "None";
        }
        return s;
    }

    public static boolean isCharLetterASCII(char c) {
        return (c >= 65 && c <= 90) || (c >= 97 && c <= 122);
    }

    public static boolean isCharTextASCII(char c) {
        return c == 10 || c == 12 || c == 13 || (c >= 32 && c <= 126);
        //10, '\n' newline
        //12, '\f' newpage
        //13, '\r' return to beginning of line
    }
}
