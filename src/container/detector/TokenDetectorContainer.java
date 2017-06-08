/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package container.detector;

import container.TokenContainer;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author bowen
 */
public class TokenDetectorContainer {
    private final List<TokenDetector> content;
    
    public TokenDetectorContainer(TokenDetector... tokenDetectorList) {
        content = Arrays.asList(tokenDetectorList);
    }
    
    public boolean check(TokenContainer<?> container) {
        for (TokenDetector detector : content) {
            if (!detector.check(container.get())) {
                return false;
            }
            container.next();
        }
        return true;
    }
    
}
