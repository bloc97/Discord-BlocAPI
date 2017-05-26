/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package addon;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.Event;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

/**
 *
 * @author bowen
 * @param <T>
 */
public interface Addon <T extends Event> {
    
    public abstract String getFullName();
    public abstract String getFullDescription();
    public abstract String getFullHelp();
    public abstract String getShortName();
    public abstract String getShortHelp();
    public abstract short getUid();
    
    public abstract boolean hasPermissions(IUser user, IChannel channel, IGuild guild);
    
    public boolean isTrigger(IDiscordClient client, T e);
    public boolean trigger(IDiscordClient client, T e);
}
