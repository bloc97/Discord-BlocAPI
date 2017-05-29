/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package token;

/**
 *
 * @author bowen
 */
public class StringToken extends Token<String> {
    private final String content;
    
    public StringToken(String string) {
        super(string);
        content = string;
    }

    @Override
    public String getContentAsString() {
        return content;
    }

    @Override
    public String getContentAsReadableString() {
        return content;
    }

    @Override
    public String getContent() {
        return content;
    }
    
}
