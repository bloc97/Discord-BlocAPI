/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules;

import addon.Addon;
import container.StringFastContainer;
import dbot.Module;
import dbot.ModuleLoader;
import modules.colour.RandomColour;
import modules.colour.SearchColour;
import modules.help.LoaderAccessor;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.Event;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 *
 * @author bowen
 */
public class Colour extends Module {
    
    public interface ColourAddon {
        public boolean triggerMessage(IDiscordClient client, MessageReceivedEvent e);
    }
    
    public Colour() {
        add(new RandomColour());
        add(new SearchColour());
    }
    @Override
    public String getFullName() {
        return "Colour";
    }

    @Override
    public String getFullDescription() {
        return "";
    }

    @Override
    public String getFullInfo() {
        return "";
    }

    @Override
    public String getShortName() {
        return "Colour";
    }

    @Override
    public String getShortDescription() {
        return "";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String getAuthor() {
        return "Bloc97";
    }

    @Override
    public long getUid() {
        return -9123564l;
    }

    @Override
    public boolean onOtherEvent(Event e) {
        return false;
    }

    @Override
    public boolean onReady(ReadyEvent e) {
        return false;
    }

    @Override
    public boolean onMessage(MessageReceivedEvent e) {
        if (e.getAuthor().isBot()) {
            return false;
        }
        StringFastContainer c = new StringFastContainer(e.getMessage().getContent(), "!");
        
        if (e.getChannel().isPrivate() || c.get().equalsIgnoreCase(botClient.getOurUser().getName()) || (e.getMessage().getMentions() != null && e.getMessage().getMentions().contains(botClient.getOurUser()))) {
            for (Addon addon : addons) {
                if (addon.hasPermissions(e.getAuthor(), e.getChannel(), e.getGuild())) {
                    
                    if (((ColourAddon)addon).triggerMessage(botClient, e)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
}
