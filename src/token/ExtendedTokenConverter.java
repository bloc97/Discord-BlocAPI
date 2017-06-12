/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package token;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;

/**
 *
 * @author bowen
 */
public interface ExtendedTokenConverter extends TokenConverter {
    
    static final DefaultTokenConverter DEFAULTCONVERTER = new DefaultTokenConverter();
    
    @Override
    public default Token convertToToken(String token) {
        if (isExtendedType(token)) {
            return convertToTokenExtended(token);
        } else {
            return DEFAULTCONVERTER.convertToToken(token);
        }
    }

    @Override
    public default Token convertToToken(IDiscordClient client, MessageReceivedEvent event, String token) {
        if (isExtendedType(token)) {
            return convertToTokenExtended(client, event, token);
        } else {
            return DEFAULTCONVERTER.convertToToken(client, event, token);
        }
    }
    
    public abstract boolean isExtendedType(String token);
    public abstract Token convertToTokenExtended(String token);
    public abstract Token convertToTokenExtended(IDiscordClient client, MessageReceivedEvent event, String token);
}