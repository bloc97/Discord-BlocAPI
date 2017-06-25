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
public class DefaultTokenConverter implements TokenConverter {

    @Override
    public Token convertToToken(String token) {
        if (ListToken.isType(token)) {
            return new ListToken(token, this, new char[] {','});
        } else if (DateToken.isType(token)) {
            return new DateToken(token);
        } else {
            return TokenConverter.convertToPrimitiveToken(token);
        }
    }

    @Override
    public Token convertToToken(JDA client, MessageReceivedEvent event, String token) {
        if (ListToken.isType(token)) {
            return new ListToken(token, this, new char[] {','});
        } else if (DateToken.isType(token)) {
            return new DateToken(token);
        } else if (MentionToken.isType(token)) {
            return new MentionToken(client, event.getMessage(), token);
        } else {
            return TokenConverter.convertToPrimitiveToken(token);
        }
    }
    
}
