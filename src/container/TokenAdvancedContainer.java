/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package container;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import token.TokenConverter;

/**
 *
 * @author bowen
 */
public class TokenAdvancedContainer extends TokenContainer<StringAdvancedContainer> {
    
    private final Set<String> flags; //flags start with -, eg -f, -l, -help, but the - is not saved in the string
    private final List<ParameterTokenContainer> parameters; //parameters start with --, and ends until another parameter or flag is detected or end of string, eg --get 123
    
    public TokenAdvancedContainer(StringAdvancedContainer container) {
        this(container, TokenConverter.getDefault());
    }
    
    public TokenAdvancedContainer(StringAdvancedContainer container, TokenConverter converter) {
        super(container, converter);
        
        flags = container.getFlags();
        parameters = new LinkedList();
        
        for (ParameterStringContainer parameter : container.getParameters()) {
            parameters.add(new ParameterTokenContainer(parameter, getTokenConverter()));
        }
    }
    
    public TokenAdvancedContainer(JDA client, MessageReceivedEvent event, StringAdvancedContainer container) {
        this(client, event, container, TokenConverter.getDefault());
    }
    
    public TokenAdvancedContainer(JDA client, MessageReceivedEvent event, StringAdvancedContainer container, TokenConverter converter) {
        super(client, event, container, converter);
        
        flags = container.getFlags();
        parameters = new LinkedList();
        
        for (ParameterStringContainer parameter : container.getParameters()) {
            parameters.add(new ParameterTokenContainer(client, event, parameter, getTokenConverter()));
        }
    }
    
}
