/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package token;

import container.StringContainer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;

/**
 *
 * @author bowen
 */
public class ListToken extends Token<List<Token>> {
    private final List<Token> content;
    public ListToken(String rawString) {
        super(rawString);
        if (!isType(rawString)) {
            throw new IllegalArgumentException("Cannot parse string into a ListToken");
        }
        String string = rawString.substring(1, rawString.length() - 1);
        List<String> stringArr = StringContainer.tokenizeAsString(string, new char[] {','});
        LinkedList<Token> tempContent = new LinkedList();
        
        for (String s : stringArr) {
            tempContent.add(Token.convertToToken(s.trim()));
        }
        content = new ArrayList(tempContent);
        
    }
    
    public ListToken(IDiscordClient client, IMessage message, String rawString) {
        super(rawString);
        if (!isType(rawString)) {
            throw new IllegalArgumentException("Cannot parse string into a ListToken");
        }
        String string = rawString.substring(1, rawString.length() - 1);
        List<String> stringArr = StringContainer.tokenizeAsString(string, new char[] {','});
        LinkedList<Token> tempContent = new LinkedList();
        
        for (String s : stringArr) {
            tempContent.add(Token.convertToToken(client, message, s.trim()));
        }
        content = new ArrayList(tempContent);
        
    }

    @Override
    public List<Token> getContent() {
        return new ArrayList(content);
    }
    
    public int size() {
        return content.size();
    }
    
    public Token get(int i) {
        if (isOob(i)) {
            return new StringToken("");
        }
        return content.get(i);
    }
    public boolean isOob(int i) {
        return i < 0 && i >= size();
    }
    
    @Override
    public String getTokenType() {
        return "ListToken";
    }

    @Override
    public String getContentAsString() {
        return content.toString();
    }

    @Override
    public String getContentAsReadableString() {
        return content.toString();
    }
    
    public static boolean isType(String string) {
        if (string.startsWith("[") && string.endsWith("]") && !string.substring(1, string.length() - 1).isEmpty()) {
            return true;
        }
        return false;
    }

}
