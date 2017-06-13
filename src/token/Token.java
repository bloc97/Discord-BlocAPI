/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package token;

/**
 *
 * @author bowen
 * @param <T>
 */
public abstract class Token<T> {

    private final String rawString;
    public Token() {
        this("");
    }
    protected Token(String rawString) {
        this.rawString = rawString;
    }
    public String getString() {
        return rawString;
    };
    public <T extends Token> boolean isType(T emptyToken) {
        return isType(emptyToken.getClass());
    }
    public <T extends Token> boolean isType(Class<T> type) {
        return type.isInstance(this);
    }
    public <T extends Token> T getAsType(T emptyToken) {
        if (isType(emptyToken)) {
            return (T) this;
        }
        return (T) emptyToken;
    }
    
    public abstract T getContent();
    public abstract String getTokenType();
    //public abstract boolean isType(String string);
    public abstract String getContentAsString();
    public abstract String getContentAsReadableString();
    @Override
    public String toString() {
        return getString();
    }
    public String getDetailedString() {
        return  "(" + getTokenType() + ") " + getContentAsString();
    }
    
}
