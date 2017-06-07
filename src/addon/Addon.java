/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package addon;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.Event;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

/**
 *
 * @author bowen
 */
public interface Addon {
    
    public abstract String getName();
    public abstract String getDescription();
    
    /**
     * Returns a full help page, used by the default Help addon.
     * @return
     */
    public abstract String getFullHelp();

    /**
     * Returns a single help sentence, used by the default Commands addon.
     * @return
     */
    public abstract String getShortHelp();
    
    public abstract int getColour();
    
    public abstract short getUid();
    
    /**
     * Determines if addon should be shown. Used for displaying the help page.
     * @param user
     * @param channel
     * @param guild
     * @return
     */
    public abstract boolean hasPermissions(IUser user, IChannel channel, IGuild guild);
    
    /**
     * Determines if addon should be triggered. Used for displaying the help page.
     * @param client
     * @param e
     * @return
     */
    public boolean isTrigger(IDiscordClient client, Event e);
    
}
