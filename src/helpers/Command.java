/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author bowen
 */
public class Command {
    private int currentIndex;
    private final ArrayList<String> content;
    private static final char SYMBOL = '!';
    
    public Command(String command) {
        LinkedList<String> parsedCommand = new LinkedList<>();
        
        //System.out.println(command);
        if (command.charAt(0) != SYMBOL) {
            currentIndex = 0;
            content = new ArrayList<>();
            return;
        }
        command = command.substring(1);
        
        //System.out.println(command);
        
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
    public String getNext() {
        if (isOob(currentIndex+1)) {
            return "";
        }
        return content.get(currentIndex+1);
    }
    public boolean hasNext() {
        return isOob(currentIndex+1);
    }
    public boolean isOob(int i) {
        return (i) >= content.size();
    }
    public void next() {
        currentIndex++;
    }
}
