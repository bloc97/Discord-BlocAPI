/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package token;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;

/**
 *
 * @author bowen
 */
public abstract class ExtendedConverter implements Converter {
    
    DefaultConverter defaultConverter = new DefaultConverter();
    
    @Override
    public Token convertToToken(String token) {
        if (isExtendedType(token)) {
            return convertToTokenExtended(token);
        } else {
            return defaultConverter.convertToToken(token);
        }
    }

    @Override
    public Token convertToToken(IDiscordClient client, IMessage message, String token) {
        if (isExtendedType(token)) {
            return convertToTokenExtended(client, message, token);
        } else {
            return defaultConverter.convertToToken(client, message, token);
        }
    }
    
    public abstract boolean isExtendedType(String token);
    public abstract Token convertToTokenExtended(String token);
    public abstract Token convertToTokenExtended(IDiscordClient client, IMessage message, String token);
}
