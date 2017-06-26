/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbot;

import container.ContainerSettings;
import container.StringContainer;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 *
 * @author bowen
 */
public class BotCommandDefaultTrigger implements BotCommandTrigger {
    
    private final ContainerSettings settings;
    
    public BotCommandDefaultTrigger(ContainerSettings settings) {
        this.settings = settings;
    }
    
    
    @Override
    public boolean isMessageTrigger(JDA client, MessageReceivedEvent e) {
        if (e.getAuthor().isBot()) {
            return false;
        }
        if (e.getChannelType() == ChannelType.PRIVATE) {
            return true;
        }
        
        String rawString = e.getMessage().getContent();
        StringContainer container = new StringContainer(rawString, settings);
        String botName = client.getSelfUser().getName();
        String botNick = (e.getChannelType() == ChannelType.PRIVATE) ? botName : e.getGuild().getSelfMember().getEffectiveName();
        String botMention = "<@" + client.getSelfUser().getId()+ ">";
        return (container.get().equalsIgnoreCase(botName) || container.get().equalsIgnoreCase(botNick) || container.get().equals(botMention));

    }
    
    @Override
    public String preParse(JDA client, MessageReceivedEvent e) {
        String rawString = e.getMessage().getContent();
        
        rawString = rawString.trim();
        
        StringContainer container = new StringContainer(rawString, settings);
        
        boolean hasPrefix = false;
        if (!container.getPrefix().isEmpty()) {
            rawString = rawString.substring(container.getPrefix().length());
            hasPrefix = true;
        }
        /*
        boolean hasSuffix = false;
        if (!container.getSuffix().isEmpty()) {
            rawString = rawString.substring(0, rawString.length() - container.getSuffix().length());
            hasSuffix = true;
        }*/
        
        User botUser = client.getSelfUser();
        String botName = botUser.getName();
        String botNick = (e.getChannelType() == ChannelType.PRIVATE) ? botName : e.getGuild().getSelfMember().getEffectiveName();
        String botMention = "<@" + client.getSelfUser().getId()+ ">";
        
        rawString = rawString.trim();
        if (rawString.toLowerCase().startsWith(botName.toLowerCase())) {
            rawString = rawString.substring(botName.length());
        } else if (rawString.toLowerCase().startsWith(botNick.toLowerCase())) {
            rawString = rawString.substring(botNick.length());
        } else if (rawString.startsWith(botMention)) {
            rawString = rawString.substring(botMention.length());
        }
        
        rawString = rawString.trim();
        if (!rawString.startsWith(container.getPrefix()) && hasPrefix) { //Place back the symbol after removing user
            rawString = container.getPrefix() + rawString;
        }
        /*
        if (!rawString.endsWith(container.getSuffix()) && hasSuffix) { //Place back the symbol after removing user
            rawString = rawString + container.getSuffix();
        }*/
        return rawString;
    }
    
}
