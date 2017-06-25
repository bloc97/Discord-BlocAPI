/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbot;

import container.ContainerSettings;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import token.DefaultTokenConverter;

/**
 *
 * @author bowen
 */
public interface BotCommandTrigger {
    public boolean isMessageTrigger(JDA client, MessageReceivedEvent e);
    public String preParse(JDA client, MessageReceivedEvent e);
    
    public static BotCommandTrigger getDefault(ContainerSettings settings) {
        return new BotCommandDefaultTrigger(settings);
    }
}
