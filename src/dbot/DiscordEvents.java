/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbot;

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
public class DiscordEvents {

    @EventSubscriber
    public void onReady(ReadyEvent e) {
        System.out.println("Bot Ready.");
        e.getClient().online("Not Connected");
        //bot.online("League of Legends Dev API");
        
    }
    @EventSubscriber
    public void onMessageReceived(MessageReceivedEvent e) {
        IChannel channel = e.getChannel();
        IUser user = e.getAuthor();
        IMessage message = e.getMessage();
        
        if (user.isBot()) {
            return;
        }
        
        UserCommand command = new UserCommand(message.getContent());
        
    }
    
}
