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
import helpers.TextFormatter;
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
    
    public static boolean startsWithCaseless(String rawString, String string) {
        return rawString.toLowerCase().startsWith(string.toLowerCase());
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
            if (TextFormatter.containsCharacter(s, separatorList)) {
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
}
