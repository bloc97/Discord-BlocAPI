/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package container.detector;

import token.StringToken;
import token.Token;

/**
 *
 * @author bowen
 */
public class TokenStringDetector extends TokenContentDetector<StringToken> {
    
    private final String string;
    
    public TokenStringDetector(String string) {
        this.string = string;
    }
    
    @Override
    public boolean contentCheck(StringToken token) {
        return token.getContent().equals(string);
    }
    
}
