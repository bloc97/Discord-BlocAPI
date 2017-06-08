/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbot;

import container.StringFastContainer;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IUser;

/**
 *
 * @author bowen
 */
public class BotCommandDefaultTrigger implements BotCommandTrigger {

    @Override
    public boolean isMessageTrigger(IDiscordClient client, MessageReceivedEvent e) {
        if (e.getAuthor().isBot()) {
            return false;
        }
        //int score = 0;
        if (e.getChannel().isPrivate()) {
            //score++;
            return true;
        }
        
        String rawString = e.getMessage().getContent();
        StringFastContainer container = new StringFastContainer(rawString, "!");
        if (container.get().equalsIgnoreCase(client.getOurUser().getName()) || container.get().equalsIgnoreCase(client.getOurUser().getDisplayName(e.getGuild()))) {
            //score++;
            return true;
        }
        return false;
    }
    
    @Override
    public String preParse(IDiscordClient client, MessageReceivedEvent e) {
        String rawString = e.getMessage().getContent();
        
        rawString = rawString.trim();
        boolean hasSymbol = false;
        if (rawString.charAt(0) == '!') {
            rawString = rawString.substring(1);
            hasSymbol = true;
        }
        
        IUser botUser = client.getOurUser();
        String botName = botUser.getName();
        String botNick = botUser.getDisplayName(e.getGuild());
        String botMention = "<@" + client.getOurUser().getLongID() + ">";
        
        rawString = rawString.trim();
        if (rawString.toLowerCase().startsWith(botName.toLowerCase())) {
            rawString = rawString.substring(botName.length());
        } else if (rawString.toLowerCase().startsWith(botNick.toLowerCase())) {
            rawString = rawString.substring(botNick.length());
        } else if (rawString.startsWith(botMention)) {
            rawString = rawString.substring(botMention.length());
        }
        
        rawString = rawString.trim();
        if (!rawString.startsWith("!") && hasSymbol) { //Place back the symbol after removing user
            rawString = "!" + rawString;
        }
        return rawString;
    }
    
}
