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
public class Help implements MessageAddon {

    @Override
    public boolean isTrigger(IDiscordClient client, MessageReceivedEvent e, UserCommand c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean trigger(IDiscordClient client, MessageReceivedEvent e, UserCommand c) {
        if (c.get().equalsIgnoreCase("help")) {
        } else if ()
        
    }

    @Override
    public String getFullName() {
        return "Help Addon";
    }

    @Override
    public String getFullDescription() {
        return "Displays the help page.";
    }

    @Override
    public String getFullHelp() {
        return "!help";
    }

    @Override
    public String getShortName() {
        return "Help";
    }

    @Override
    public String getShortHelp() {
        return "!help";
    }

    @Override
    public short getUid() {
        return 0;
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
