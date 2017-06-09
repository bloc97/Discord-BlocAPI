/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package token;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

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
    public Token convertToToken(IDiscordClient client, MessageReceivedEvent event, String token) {
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
