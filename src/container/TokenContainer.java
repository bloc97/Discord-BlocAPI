/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package container;

import java.util.LinkedList;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;
import token.Converter;
import token.DefaultConverter;
import token.Token;

/**
 *
 * @author bowen
 */
public class TokenContainer<T extends StringContainer> extends Container<Token> {
    
    private final T container;
    
    public TokenContainer(T container) {
        this(container, new DefaultConverter());
    }
    public TokenContainer(IDiscordClient client, MessageReceivedEvent event, T container) {
        this(client, event, container, new DefaultConverter());
    }
    public TokenContainer(T container, Converter converter) {
        super(container.getRawString(), container.getTrimmedString(), container.getPrefix(), container.getSuffix());
        this.container = container;
        
        LinkedList<Token> tokenContent = new LinkedList();
        
        for (String s : container.getContent()) {
            tokenContent.add(converter.convertToToken(s));
        }
        setContent(tokenContent);
    }
    
    public TokenContainer(IDiscordClient client, MessageReceivedEvent event, T container, Converter converter) {
        super(container.getRawString(), container.getTrimmedString(), container.getPrefix(), container.getSuffix());
        this.container = container;
        
        LinkedList<Token> tokenContent = new LinkedList();
        
        for (String s : container.getContent()) {
            tokenContent.add(converter.convertToToken(client, event, s));
        }
        setContent(tokenContent);
    }
    public T getStringContainer() {
        return container;
    }
    @Override
    public String toString() {
        return getContent().toString();
    }
}
