/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules.help;

import addon.MessageAddon;
import container.UserCommand;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.Event;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

/**
 *
 * @author bowen
 */
public class Commands implements MessageAddon {

    @Override
    public boolean isTrigger(IDiscordClient client, MessageReceivedEvent e, UserCommand c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean trigger(IDiscordClient client, MessageReceivedEvent e, UserCommand c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getFullName() {
        return "Commands Addon";
    }

    @Override
    public String getFullDescription() {
        return "Displays the command list when called.";
    }

    @Override
    public String getFullHelp() {
        return "!commands <page> - Displays the command list.";
    }

    @Override
    public String getShortName() {
        return "Commands";
    }

    @Override
    public String getShortHelp() {
        return "!commands <page>";
    }

    @Override
    public short getUid() {
        return 1;
    }

    @Override
    public boolean hasPermissions(IUser user, IChannel channel, IGuild guild) {
        return true;
    }

    @Override
    public boolean isTrigger(IDiscordClient client, Event e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean trigger(IDiscordClient client, Event e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
