/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package container;

import java.util.LinkedList;
import java.util.List;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import token.DefaultTokenConverter;
import token.NumberToken;
import token.StringToken;
import token.Token;
import token.TokenConverter;

/**
 *
 * @author bowenimport token.TokenConverter;
 * @param <T>

 */
public class TokenContainer<T extends StringContainer> extends Container<Token<?>> {
    
    private final T container;
    public final TokenConverter tokenConverter;
    
    public TokenContainer(T container) {
        this(container, TokenConverter.getDefault());
    }
    public TokenContainer(JDA client, MessageReceivedEvent event, T container) {
        this(client, event, container, TokenConverter.getDefault());
    }
    public TokenContainer(T container, TokenConverter converter) {
        super(container.getRawString(), container.getTrimmedString(), container.getPrefix(), container.getSuffix());
        this.container = container;
        this.tokenConverter = converter;
        
        LinkedList<Token<?>> tokenContent = new LinkedList();
        
        for (String s : container.getContent()) {
            tokenContent.add(converter.convertToToken(s));
        }
        setContent(tokenContent);
    }
    
    public TokenContainer(JDA client, MessageReceivedEvent event, T container, TokenConverter converter) {
        super(container.getRawString(), container.getTrimmedString(), container.getPrefix(), container.getSuffix());
        this.container = container;
        this.tokenConverter = converter;
        
        LinkedList<Token<?>> tokenContent = new LinkedList();
        
        for (String s : container.getContent()) {
            tokenContent.add(converter.convertToToken(client, event, s));
        }
        setContent(tokenContent);
    }
    public T getStringContainer() {
        return container;
    }
    public TokenConverter getTokenConverter() {
        return tokenConverter;
    }
    
    @Override
    public Token getEmptyContent() {
        return new StringToken("");
    }
    
    public <T extends Token> boolean hasTokenOfType(T emptyToken, int fromIndex) {
        return hasTokenOfType(emptyToken.getClass(), fromIndex);
    }
    public <T extends Token> boolean hasTokenOfType(Class<T> type, int fromIndex) {
        return (!isOob(indexOfTokenOfType(type, fromIndex)));
    }
    public <T extends Token> int indexOfTokenOfType(T emptyToken, int fromIndex) {
        return indexOfTokenOfType(emptyToken.getClass(), fromIndex);
    }
    public <T extends Token> int indexOfTokenOfType(Class<T> type, int fromIndex) {
        int i = 0;
        for (Token token : getContent()) {
            if (i < fromIndex) {
                i++;
                continue;
            }
            if (token.isType(type)) {
                return i;
            }
            i++;
        }
        return -1;
    }
    public <T extends Token> T getTokenOfType(T emptyToken, int fromIndex) {
        return getAsType(emptyToken, indexOfTokenOfType(emptyToken, fromIndex));
    }
    public <T extends Token> boolean reverseHasTokenOfType(T emptyToken, int fromIndex) {
        return reverseHasTokenOfType(emptyToken.getClass(), fromIndex);
    }
    public <T extends Token> boolean reverseHasTokenOfType(Class<T> type, int fromIndex) {
        return (!isOob(lastIndexOfTokenOfType(type, fromIndex)));
    }
    public <T extends Token> int lastIndexOfTokenOfType(T emptyToken, int fromIndex) {
        return lastIndexOfTokenOfType(emptyToken.getClass(), fromIndex);
    }
    public <T extends Token> int lastIndexOfTokenOfType(Class<T> type, int fromIndex) {
        for (int i = fromIndex; i>=0; i--) {
            if (get(i).isType(type)) {
                return i;
            }
        }
        return -1;
    }
    public <T extends Token> T reverseGetTokenOfType(T emptyToken, int fromIndex) {
        return getAsType(emptyToken, lastIndexOfTokenOfType(emptyToken, fromIndex));
    }
    
    
    public <T extends Token> boolean hasNextTokenOfType(T emptyToken) {
        return hasNextTokenOfType(emptyToken.getClass());
    }
    public <T extends Token> boolean hasNextTokenOfType(Class<T> type) {
        return hasTokenOfType(type, getCurrentIndex());
    }
    public <T extends Token> int indexOfNextTokenOfType(T emptyToken) {
        return indexOfNextTokenOfType(emptyToken.getClass());
    }
    public <T extends Token> int indexOfNextTokenOfType(Class<T> type) {
        return indexOfTokenOfType(type, getCurrentIndex());
    }
    public <T extends Token> T getNextTokenOfType(T emptyToken) {
        return getTokenOfType(emptyToken, getCurrentIndex());
    }
    
    public <T extends Token> boolean reverseHasNextTokenOfType(T emptyToken) {
        return reverseHasNextTokenOfType(emptyToken.getClass());
    }
    public <T extends Token> boolean reverseHasNextTokenOfType(Class<T> type) {
        return reverseHasTokenOfType(type, reverseGetCurrentIndex());
    }
    public <T extends Token> int reverseIndexOfNextTokenOfType(T emptyToken) {
        return reverseIndexOfNextTokenOfType(emptyToken.getClass());
    }
    public <T extends Token> int reverseIndexOfNextTokenOfType(Class<T> type) {
        return lastIndexOfTokenOfType(type, reverseGetCurrentIndex());
    }
    public <T extends Token> T reverseGetNextTokenOfType(T emptyToken) {
        return reverseGetTokenOfType(emptyToken, reverseGetCurrentIndex());
    }
    
    public <T extends Token> boolean isType(T emptyToken) {
        return get().isType(emptyToken);
    }
    public <T extends Token> boolean isType(Class<T> type) {
        return get().isType(type);
    }
    public <T extends Token> boolean isNextType(T emptyToken) {
        return getNext().isType(emptyToken);
    }
    public <T extends Token> boolean isNextType(Class<T> type) {
        return getNext().isType(type);
    }
    public <T extends Token> boolean reverseIsType(T emptyToken) {
        return reverseGet().isType(emptyToken);
    }
    public <T extends Token> boolean reverseIsType(Class<T> type) {
        return reverseGet().isType(type);
    }
    public <T extends Token> boolean reverseIsNextType(T emptyToken) {
        return reverseGetNext().isType(emptyToken);
    }
    public <T extends Token> boolean reverseIsNextType(Class<T> type) {
        return reverseGetNext().isType(type);
    }
    public <T extends Token> boolean isType(T emptyToken, int i) {
        return get(i).isType(emptyToken);
    }
    public <T extends Token> boolean isType(Class<T> type, int i) {
        return get(i).isType(type);
    }
    public <T extends Token> T getAsType(T emptyToken) {
        return get().getAsType(emptyToken);
    }
    public <T extends Token> T getNextAsType(T emptyToken) {
        return getNext().getAsType(emptyToken);
    }
    public <T extends Token> T reverseGetAsType(T emptyToken) {
        return reverseGet().getAsType(emptyToken);
    }
    public <T extends Token> T reverseGetNextAsType(T emptyToken) {
        return reverseGetNext().getAsType(emptyToken);
    }
    public <T extends Token> T getAsType(T emptyToken, int i) {
        return get(i).getAsType(emptyToken);
    }
    
    public String getAsString() {
        return get().getString();
    }
    public String getNextAsString() {
        return getNext().getString();
    }
    public String reverseGetAsString() {
        return reverseGet().getString();
    }
    public String reverseGetNextAsString() {
        return reverseGetNext().getString();
    }
    public String getAsString(int i) {
        return get(i).getString();
    }
    public Double getAsNumber() {
        return getAsNumber(getCurrentIndex());
    }
    public Double getNextAsNumber() {
        return getAsNumber(getCurrentIndex()+1);
    }
    public Double reverseGetAsNumber() {
        return getAsNumber(reverseGetCurrentIndex());
    }
    public Double reverseGetNextAsNumber() {
        return getAsNumber(reverseGetCurrentIndex()-1);
    }
    public Double getAsNumber(int i) {
        return (new NumberToken(getAsString(i))).getContent().doubleValue();
    }
    
    @Override
    public String toString() {
        return getContent().toString();
    }

}
