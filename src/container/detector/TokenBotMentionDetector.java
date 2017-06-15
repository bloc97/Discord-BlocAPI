/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package container.detector;

import token.MentionToken;

/**
 *
 * @author bowen
 */
public class TokenBotMentionDetector extends TokenContentDetector<MentionToken> {
    
    @Override
    public boolean contentCheck(MentionToken token) {
        return token.isBotMentionned();
    }
    
}
