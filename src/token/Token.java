/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package token;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;

/**
 *
 * @author bowen
 * @param <T>
 */
public abstract class Token<T> {

    public static Token convertToToken(String token) {
        if (ListToken.isType(token)) {
            return new ListToken(token);
        } else if (DateToken.isType(token)) {
            return new DateToken(token);
        } else if (NumberToken.isType(token)) {
            return new NumberToken(token);
        } else {
            return new StringToken(token);
        }
    }

    public static Token convertToToken(IDiscordClient client, IMessage message, String token) {
        if (ListToken.isType(token)) {
            return new ListToken(token);
        } else if (DateToken.isType(token)) {
            return new DateToken(token);
        } else if (MentionToken.isType(token)) {
            return new MentionToken(client, message, token);
        } else if (NumberToken.isType(token)) {
            return new NumberToken(token);
        } else {
            return new StringToken(token);
        }
    }
    private final String rawString;
    protected Token(String rawString) {
        this.rawString = rawString;
    }
    public String getRawString() {
        return rawString;
    };
    public abstract T getContent();
    public abstract String getTokenType();
    //public abstract boolean isType(String string);
    public abstract String getContentAsString();
    public abstract String getContentAsReadableString();
    @Override
    public String toString() {
        return  "(" + getTokenType() + ") " + getContentAsString();
    }
}
