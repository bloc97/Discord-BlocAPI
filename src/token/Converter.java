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
public interface Converter {
    
    public Token convertToToken(String token);
    public Token convertToToken(IDiscordClient client, IMessage message, String token);
    
    public static Token convertToPrimitiveToken(String token) {
        if (NumberToken.isType(token)) {
            return new NumberToken(token);
        } else {
            return new StringToken(token);
        }
    }
}
