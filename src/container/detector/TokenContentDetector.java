/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package container.detector;

import token.Token;

/**
 *
 * @author bowen
 * @param <T>
 */
public abstract class TokenContentDetector<T extends Token> implements TokenDetector {

    public TokenContentDetector() {
    }
    
    public TokenContentDetector(Class<T> t) {
    }
    
    @Override
    public boolean check(Token token) {
        try {
            T t = (T) token;
            return contentCheck(t);
        } catch (ClassCastException ex) {
            return false;
        }
    }
    
    public abstract boolean contentCheck(T token);
    
}
