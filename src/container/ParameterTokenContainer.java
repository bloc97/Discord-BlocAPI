/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package container;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import token.TokenConverter;

/**
 *
 * @author bowen
 */
public class ParameterTokenContainer extends TokenContainer {
    
    public ParameterTokenContainer(ParameterStringContainer container, TokenConverter converter) {
        super(container, converter);
    }
    
    public ParameterTokenContainer(JDA client, MessageReceivedEvent event, ParameterStringContainer container, TokenConverter converter) {
        super(client, event, container, converter);
    }
    
}
