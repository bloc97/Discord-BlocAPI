/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package container;

import helpers.TextFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author bowen
 */
public class StringContainer extends Container<String> {
    
    protected StringContainer(String rawString, String trimmedString, List<String> content, String prefix, String suffix) {
        super(rawString, trimmedString, prefix, suffix);
        setContent(content);
    }
    protected StringContainer(int index, int reverseIndex, String rawString, String trimmedString, List<String> content, String prefix, String suffix) {
        this(rawString, trimmedString, content, prefix, suffix);
        this.index = index;
        this.reverseIndex = reverseIndex;
    }
    
    public StringContainer(String command) {
        this(command, ' ');
    }
    
    public StringContainer(String command, char separator) {
        this(command, new char[] {separator}, new String[0], new String[0]);
    }
    
    public StringContainer(String command, char separator, String prefix, String suffix) {
        this(command, new char[] {separator}, new String[] {prefix}, new String[] {suffix});
    }
    
    public StringContainer(String rawString, char[] separatorList, String[] prefixList, String[] suffixList) {
        super(rawString, prefixList, suffixList);
        
        String trimmedString = getTrimmedString();
        if (trimmedString.isEmpty()) {
            return;
        }
        
        setContent(tokenizeAsString(trimmedString, separatorList));
        
    }
    
    
    public static List<String> tokenizeAsString(String command, char[] separatorList) {
        Set<Character> separators = new LinkedHashSet();
        for (Character c : separatorList) {
            if (c != '"' && c != '-') { //Ignore double quotes and -
                separators.add(c);
            }
        }
        
        LinkedList<String> parsedCommand = new LinkedList();
        
        String currentString = "";
        boolean isInQuotes = false;
        boolean isInListQuote = false;
        int listDepth = 0;
        
        for (int i=0; i<command.length(); i++) {
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
    
    
    @Deprecated
    public String getContentAsString() {
        return TextFormatter.join(getContent().toArray(new String[0]), ' ');
    }
    @Deprecated
    public String getRemainingContentAsString() {
        if (index > reverseIndex) {
            return "";
        }
        int i = TextFormatter.boundExcludeMax(this.index, 0, size());
        int ri = TextFormatter.boundExcludeMax(this.reverseIndex, 0, size());
        return TextFormatter.join(Arrays.copyOfRange(getContent().toArray(new String[0]), i, ri+1), ' ');
    }
    @Override
    public StringContainer clone() {
        return new StringContainer(getCurrentIndex(), getCurrentReverseIndex(), getRawString(), getTrimmedString(), getContent(), getPrefix(), getSuffix());
    }
    
}
