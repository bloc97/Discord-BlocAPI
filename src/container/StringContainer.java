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
public class StringContainer {
    protected int index;
    protected int reverseIndex;
    private final String rawString;
    protected List<String> content;
    private final String prefix;
    private final String suffix;
    
    
    protected StringContainer(int index, int reverseIndex, String rawString, List<String> content, String prefix, String suffix) {
        this.index = index;
        this.reverseIndex = reverseIndex;
        this.rawString = rawString;
        this.content = new ArrayList<>(content);
        this.prefix = prefix;
        this.suffix = suffix;
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
    
    public StringContainer(String command, char[] separatorList, String[] prefixList, String[] suffixList) {
        rawString = command;
        
        command = command.trim();
        if (command.isEmpty()) {
            content = new ArrayList<>();
            prefix = "";
            suffix = "";
            return;
        }
        String pre = "";
        String suf = "";
        for (String s : prefixList) {
            s = s.toLowerCase(); //Do not care about case
            if (command.toLowerCase().startsWith(s)) { //Do not care about case
                pre = s;
                command = command.substring(s.length());
                break;
            }
        }
        for (String s : suffixList) {
            s = s.toLowerCase(); //Do not care about case
            if (command.toLowerCase().endsWith(s)) { //Do not care about case
                suf = s;
                command = command.substring(0, command.length()-s.length());
                break;
            }
        }
        prefix = pre;
        suffix = suf;
        
        List<String> parsedCommand = tokenizeAsString(command, separatorList);
        content = parsedCommand;
        
        index = 0;
        reverseIndex = content.size()-1;
    }
    
    public static List<String> tokenizeAsString(String command, char[] separatorList) {
        Set<Character> separators = new LinkedHashSet<>();
        for (Character c : separatorList) {
            if (c != '"' && c != '-') { //Ignore double quotes and -
                separators.add(c);
            }
        }
        
        LinkedList<String> parsedCommand = new LinkedList<>();
        
        String currentString = "";
        boolean isInQuotes = false;
        
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
            } else {
                if (separators.contains(c)) {
                    if (currentString.length() > 0) {
                        parsedCommand.addLast(currentString);
                        currentString = "";
                    }
                } else if (c == '"') {
                    isInQuotes = true;
                } else {
                    currentString = currentString + c;
                }
            }
        }
        if (currentString.length() > 0) {
            parsedCommand.addLast(currentString);
            currentString = "";
        }
        return new ArrayList<>(parsedCommand);
    }
    
    public String getRawString() {
        return rawString;
    }
    @Override
    public String toString() {
        return rawString;
    }
    
    public List<String> getContent() {
        return new ArrayList<>(content);
    }
    public int getCurrentIndex() {
        return index;
    }
    public int getCurrentReverseIndex() {
        return reverseIndex;
    }
    public int size() {
        return content.size();
    }
    public int remainingSize() {
        if (index > reverseIndex) {
            return 0;
        }
        return reverseIndex - index + 1;
    }
    public void reset() {
        index = 0;
        reverseIndex = content.size()-1;
    }
    public String get(int i) {
        if (isOob(i)) {
            return "";
        }
        return content.get(i);
    }
    public String get() {
        return get(index);
    }
    public String reverseGet() {
        return get(reverseIndex);
    }
    public String getNext() {
        return get(index+1);
    }
    public String reverseGetNext() {
        return get(reverseIndex-1);
    }
    public String getContentAsString() {
        return TextFormatter.join(content.toArray(new String[0]), ' ');
    }
    public String[] getContentAsArray() {
        return content.toArray(new String[0]);
    }
    public String getRemainingContentAsString() {
        if (index > reverseIndex) {
            return "";
        }
        int i = TextFormatter.boundExclude(this.index, 0, size());
        int ri = TextFormatter.boundExclude(this.reverseIndex, 0, size());
        return TextFormatter.join(Arrays.copyOfRange(content.toArray(new String[0]), i, ri+1), ' ');
    }
    public String[] getRemainingContentAsArray() {
        if (index > reverseIndex) {
            return new String[] {""};
        }
        int i = TextFormatter.boundExclude(this.index, 0, size());
        int ri = TextFormatter.boundExclude(this.reverseIndex, 0, size());
        return Arrays.copyOfRange(content.toArray(new String[0]), i, ri+1);
    }
    public List<String> getRemainingContent() {
        if (index > reverseIndex) {
            return new ArrayList<>();
        }
        
        int i = TextFormatter.boundExclude(this.index, 0, size());
        int ri = TextFormatter.boundExclude(this.reverseIndex, 0, size());
        return content.subList(i, ri+1);
    }
    public boolean hasNext() {
        return !isOob(index+1);
    }
    public boolean reverseHasNext() {
        return !isOob(reverseIndex-1);
    }
    public boolean isOob(int i) {
        return i >= content.size() || i < 0;
    }
    public void next() {
        index++;
    }
    public void previous() {
        index--;
    }
    public void reverseNext() {
        reverseIndex--;
    }
    public void reversePrevious() {
        reverseIndex++;
    }
    public String getPrefix() {
        return prefix;
    }
    public String getSuffix() {
        return suffix;
    }
    @Override
    public StringContainer clone() {
        return new StringContainer(index, reverseIndex, rawString, content, prefix, suffix);
    }
    
}
