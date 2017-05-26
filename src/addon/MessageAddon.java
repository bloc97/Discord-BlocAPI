/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package addon;

import dbot.UserCommand;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.Event;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 *
 * @author bowen
 */
public interface MessageAddon extends Addon<Event> {
    public boolean isTrigger(IDiscordClient client, MessageReceivedEvent e, UserCommand c);
    public boolean trigger(IDiscordClient client, MessageReceivedEvent e, UserCommand c);
}
