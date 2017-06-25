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
public abstract class ExtendedTokenConverter extends DefaultTokenConverter {
    
    @Override
    public Token convertToToken(String token) {
        if (isExtendedType(token)) {
            return convertToTokenExtended(token);
        } else {
            return super.convertToToken(token);
        }
    }

    @Override
    public Token convertToToken(JDA client, MessageReceivedEvent event, String token) {
        if (isExtendedType(token)) {
            return convertToTokenExtended(client, event, token);
        } else {
            return super.convertToToken(client, event, token);
        }
    }
    
    public abstract boolean isExtendedType(String token);
    public abstract Token convertToTokenExtended(String token);
    public abstract Token convertToTokenExtended(JDA client, MessageReceivedEvent event, String token);
}
