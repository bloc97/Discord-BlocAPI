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
public class TokenTypeDetector<T extends Token> extends TokenContentDetector<T> {
    
    public TokenTypeDetector(Class<T> t) {
    }

    @Override
    public boolean contentCheck(T token) {
        return true;
    }
    
}
