package dbot;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import helpers.OtherUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author bowen
 */
public class UserCommand {
    private int currentIndex;
    private int currentReverseIndex;
    private final List<String> content;
    private final char symbol;
    private static final Set<Character> TRIGGERSYMBOLS = new HashSet<>(Arrays.asList('!', '$', '%', '&', '~', '\\'));
    private final LinkedHashSet<Character> separators;
    
    private final Set<String> flags; //flags start with -, eg -f, -l, -help
    private final Map<String, String> parameters; //parameters start with --, and ends until another parameter or flag is detected or end of string, eg --get 123
    
    public UserCommand(int size) { //For test generation
        content = new ArrayList<>();
        separators = new LinkedHashSet<>();
        flags = new HashSet<>();
        parameters = new HashMap<>();
        symbol = 0;
        currentIndex = 0;
        currentReverseIndex = size - 1;
    }
    public UserCommand(String[] content) {
        this.content = new ArrayList<>(Arrays.asList(content));
        this.separators = new LinkedHashSet<>();
        separators.add(' ');
        this.flags = new HashSet<>();
        this.parameters = new HashMap<>();
        symbol = 0;
        currentIndex = 0;
        currentReverseIndex = content.length-1;
    }
    
    private UserCommand(int currentIndex, int currentReverseIndex, List<String> content, char symbol, Set<Character> separators, Set<String> flags, Map<String, String> parameters) {
        this.content = new ArrayList<>(content);
        this.separators = new LinkedHashSet<>(separators);
        this.flags = new HashSet<>(flags);
        this.parameters = new HashMap<>(parameters);
        this.symbol = symbol;
        this.currentIndex = currentIndex;
        this.currentReverseIndex = currentReverseIndex;
    }
    
    public UserCommand(String command) {
        this(command, ' ');
    }
    
    public UserCommand(String command, char... separatorsList) {
        this.separators = new LinkedHashSet<>();
        for (Character c : separatorsList) {
            if (c != '"' && c != '-' && !TRIGGERSYMBOLS.contains(c)) { //Ignore double quotes, - and triggersymbols
                separators.add(c);
            }
        }
        if (command.isEmpty()) {
            content = new ArrayList<>();
            flags = new HashSet<>();
            parameters = new HashMap<>();
            symbol = 0;
            return;
        }
        if (TRIGGERSYMBOLS.contains(command.charAt(0))) {
            symbol = command.charAt(0);
            command = command.substring(1);
        } else if (OtherUtils.isCharLetterASCII(command.charAt(0)) || OtherUtils.isCharNumberASCII(command.charAt(0))) {
            symbol = 0;
        } else {
            content = new ArrayList<>();
            flags = new HashSet<>();
            parameters = new HashMap<>();
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
        
        int i = 0;
        boolean isInParameter = false;
        for (String token : parsedCommand) {
            if (token.startsWith("--")) {
                if (!isInParameter) {
                    isInParameter = true;
                }
            } else if (token.startsWith("-")) {
                
            } else {
                
            }
            i++;
        }
        
        
        currentIndex = 0;
        content = new ArrayList<>(parsedCommand);
        currentReverseIndex = content.size()-1;
        //System.out.println(Arrays.toString(content.toArray(new String[0])));
        
        
        this.flags = new HashSet();
        parameters = new HashMap();
    }
    
    public List<String> getContent() {
        return content;
    }
    public int getCurrentIndex() {
        return currentIndex;
    }
    public int getCurrentReverseIndex() {
        return currentReverseIndex;
    }
    public int size() {
        return content.size();
    }
    public int remainingSize() {
        if (currentIndex > currentReverseIndex) {
            return 0;
        }
        return currentReverseIndex - currentIndex + 1;
    }
    public void reset() {
        currentIndex = 0;
        currentReverseIndex = content.size()-1;
    }
    public String get(int i) {
        if (isOob(i)) {
            return "";
        }
        return content.get(i);
    }
    public String get() {
        return get(currentIndex);
    }
    public String getReverse() {
        return get(currentReverseIndex);
    }
    public String getNext() {
        return get(currentIndex+1);
    }
    public String getNextReverse() {
        return get(currentReverseIndex-1);
    }
    public String getAllTokensString() {
        return OtherUtils.join(content.toArray(new String[0]), new LinkedList<>(separators).getFirst());
    }
    public String[] getAllTokensArray() {
        return content.toArray(new String[0]);
    }
    public String getTokensString() {
        if (currentIndex > currentReverseIndex) {
            return "";
        }
        int index = OtherUtils.boundExcludeMax(currentIndex, 0, size());
        int reverseIndex = OtherUtils.boundExcludeMax(currentReverseIndex, 0, size());
        return OtherUtils.join(Arrays.copyOfRange(content.toArray(new String[0]), index, reverseIndex+1), new LinkedList<>(separators).getFirst());
    }
    public String[] getTokensArray() {
        if (currentIndex > currentReverseIndex) {
            return new String[] {""};
        }
        int index = OtherUtils.boundExcludeMax(currentIndex, 0, size());
        int reverseIndex = OtherUtils.boundExcludeMax(currentReverseIndex, 0, size());
        return Arrays.copyOfRange(content.toArray(new String[0]), index, reverseIndex+1);
    }
    public boolean hasNext() {
        return !isOob(currentIndex+1);
    }
    public boolean hasNextReverse() {
        return !isOob(currentReverseIndex-1);
    }
    public boolean isOob(int i) {
        return i >= content.size() || i < 0;
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
    @Override
    public UserCommand clone() {
        return new UserCommand(currentIndex, currentReverseIndex, content, symbol, separators, flags, parameters);
    }
}
