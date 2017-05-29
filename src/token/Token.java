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
    protected Token(String rawString) {
        this.rawString = rawString;
    }
    public String getRawString() {
        return rawString;
    };
    public abstract T getContent();
    public abstract String getContentAsString();
    public abstract String getContentAsReadableString();
}
