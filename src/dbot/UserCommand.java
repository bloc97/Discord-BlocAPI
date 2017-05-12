/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author bowen
 */
public class UserCommand {
    private int currentIndex;
    private int currentReverseIndex;
    private final ArrayList<String> content;
    private final char symbol;
    private static final List<Character> TRIGGERSYMBOLS = Arrays.asList('!', '$', '%', '&', '~', '\\');
    
    public UserCommand(String command) {
        
        if (command.isEmpty()) {
            content = new ArrayList<>();
            symbol = 0;
            return;
        }
        if (TRIGGERSYMBOLS.contains(command.charAt(0))) {
            symbol = command.charAt(0);
            command = command.substring(1);
        } else {
            content = new ArrayList<>();
            symbol = 0;
            return;
        }
        
        //System.out.println(command);
        
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
                if (c == ' ') {
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
        currentIndex = 0;
        content = new ArrayList<>(parsedCommand);
        currentReverseIndex = content.size()-1;
        //System.out.println(Arrays.toString(content.toArray(new String[0])));
    }
    
    public ArrayList<String> getContent() {
        return content;
    }
    public int getCurrentIndex() {
        return currentIndex;
    }
    public int size() {
        return content.size();
    }
    public String get(int i) {
        if (isOob(i)) {
            return "";
        }
        return content.get(i);
    }
    public String get() {
        if (isOob(currentIndex)) {
            return "";
        }
        return content.get(currentIndex);
    }
    public String getReverse() {
        if (isOob(currentReverseIndex)) {
            return "";
        }
        return content.get(currentReverseIndex);
    }
    public String getNext() {
        if (isOob(currentIndex+1)) {
            return "";
        }
        return content.get(currentIndex+1);
    }
    public String getNextReverse() {
        if (isOob(currentReverseIndex-1)) {
            return "";
        }
        return content.get(currentReverseIndex-1);
    }
    public String getAllTokensString() {
        String string = "";
        for (String s : content) {
            string += s + " ";
        }
        return string.substring(0, string.length()-1);
    }
    public String getTokensString() {
        String string = "";
        if (currentIndex > currentReverseIndex) {
            return "";
        }
        for (int i=currentIndex; i<=currentReverseIndex; i++) {
            string += content.get(i) + " ";
        }
        return string.substring(0, string.length()-1);
    }
    public boolean hasNext() {
        return !isOob(currentIndex+1);
    }
    public boolean hasNextReverse() {
        return !isOob(currentReverseIndex-1);
    }
    public boolean isOob(int i) {
        return (i) >= content.size() || (i) < 0;
    }
    public void next() {
        currentIndex++;
    }
    public void previous() {
        currentIndex--;
    }
    public void nextReverse() {
        currentReverseIndex--;
    }
    public void previousReverse() {
        currentReverseIndex++;
    }
    public char getSymbol() {
        return symbol;
    }
    public String getSymbolAsString() {
        return "" + symbol;
    }
}
