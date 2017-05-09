/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lolbot;

import dbot.Command;
import dbot.UserCommand;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 *
 * @author bowen
 */
public abstract class LoLCommand implements Command {
    public enum LoLCommandType {
        NULL, SEARCHSUMMONERNAME
    }
    public final String triggerVerb;
    public final LoLCommandType type;
    public LoLCommand(String triggerVerb, LoLCommandType type) {
        this.triggerVerb = triggerVerb;
        this.type = type;
    }
    public LoLCommandType getType() {
        return type;
    }
    @Override
    public boolean isTrigger(String verb) {
        return (triggerVerb.equals(verb));
    }
    @Override
    public final boolean trigger(MessageReceivedEvent e, UserCommand c) {
        throw new IllegalStateException("Cannot parse command without RiotApi object.");
    }
    public abstract boolean trigger(MessageReceivedEvent e, UserCommand c, RiotDatabase db);
}
