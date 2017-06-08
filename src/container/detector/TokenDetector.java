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
 */
public interface TokenDetector {
    public boolean check(Token token);
}
