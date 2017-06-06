/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package container;

import java.util.LinkedList;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;
import token.DateToken;
import token.MentionToken;
import token.NumberToken;
import token.StringToken;
import token.Token;

/**
 *
 * @author bowen
 */
public class TokenContainer extends Container<Token> {
    
    public TokenContainer(StringContainer container) {
        super(container.getRawString(), container.getTrimmedString(), container.getPrefix(), container.getSuffix());
        
        LinkedList<Token> tokenContent = new LinkedList();
        
        for (String s : container.getContent()) {
            tokenContent.add(Token.convertToToken(s));
        }
        setContent(tokenContent);
    }
    
    public TokenContainer(IDiscordClient client, IMessage message, StringContainer container) {
        super(container.getRawString(), container.getTrimmedString(), container.getPrefix(), container.getSuffix());
        
        LinkedList<Token> tokenContent = new LinkedList();
        
        for (String s : container.getContent()) {
            tokenContent.add(Token.convertToToken(client, message, s));
        }
        setContent(tokenContent);
    }
    @Override
    public String toString() {
        return getContent().toString();
    }
}
