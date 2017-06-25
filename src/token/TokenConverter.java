/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package token;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;


/**
 *
 * @author bowen
 */
public interface TokenConverter {
    
    public Token convertToToken(String token);
    public Token convertToToken(JDA client, MessageReceivedEvent event, String token);
    
    public static Token convertToPrimitiveToken(String token) {
        if (NumberToken.isType(token)) {
            return new NumberToken(token);
        } else {
            return new StringToken(token);
        }
    }
    public static TokenConverter getDefault() {
        return new DefaultTokenConverter();
    }
}
