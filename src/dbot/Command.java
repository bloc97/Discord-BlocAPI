/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbot;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 *
 * @author bowen
 */
public interface Command {
    public boolean isTrigger(String verb);
    public boolean trigger(MessageReceivedEvent e, UserCommand c);
}
