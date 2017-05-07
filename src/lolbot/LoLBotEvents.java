/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lolbot;

import helpers.Command;
import net.rithms.riot.api.RiotApiException;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

/**
 *
 * @author bowen
 */
public class LoLBotEvents {
    
    LoLBotApi lolApi;
    
    public LoLBotEvents() {
        lolApi = new LoLBotApi();
    }
    
    @EventSubscriber
    public void onReady(ReadyEvent e) {
        System.out.println("Bot Ready.");
        //e.getClient().online("Not Connected");
        e.getClient().online("League of Legends Dev API");
    }
    
    @EventSubscriber
    public void onMessageReceived(MessageReceivedEvent e) {
        IChannel channel = e.getChannel();
        IUser user = e.getAuthor();
        IMessage message = e.getMessage();
        
        if (user.isBot()) {
            return;
        }
        
        Command command = new Command(message.getContent());
        try {
            if (command.get().equals("lol")) {
                command.next();
                lolApi.parseMessage(e, command);
            } else if (channel.getName().equals("leagueoflegends")) {
                lolApi.parseMessage(e, command);
            }
        } catch (RiotApiException ex) {
            System.out.println(ex);
        }
        
    }
}
