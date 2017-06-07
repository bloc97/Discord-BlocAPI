/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules.help;

import addon.Addon;
import container.StringFastContainer;
import dbot.Module;
import dbot.ModuleLoader;
import helpers.ParserUtils;
import java.util.LinkedList;
import java.util.List;
import modules.Help.HelpAddon;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.Event;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

/**
 *
 * @author bowen
 */
public class Commands implements Addon, HelpAddon {

    @Override
    public String getName() {
        return "Commands";
    }

    @Override
    public String getDescription() {
        return "Displays the command list when called.";
    }

    @Override
    public String getFullHelp() {
        return "**!commands** <page> - *Shows the command list*";
    }

    @Override
    public String getShortHelp() {
        return "**!commands** <page> - *Shows the command list*";
    }
    
    @Override
    public int getColour() {
        return 5563639;
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
        if (e instanceof MessageReceivedEvent) {
            MessageReceivedEvent em = (MessageReceivedEvent) e;
            String rawString = em.getMessage().getContent();
            String botName = client.getOurUser().getName();
            String commandName = "commands";
            return ParserUtils.startsWithCaseless(rawString, "!" + botName + " " + commandName) || ParserUtils.startsWithCaseless(rawString, "!" + commandName) ;
        }
        return false;
    }

    @Override
    public boolean triggerMessage(IDiscordClient client, MessageReceivedEvent e, ModuleLoader moduleLoader) {
        
        StringFastContainer c = new StringFastContainer(e.getMessage().getContent(), "!");
        if (c.get().equalsIgnoreCase(client.getOurUser().getName())) {
            c.next();
        }
        if (c.get().equalsIgnoreCase("commands")) {
            
            EmbedObject eo = new EmbedObject();
            
            List<Addon> addons = new LinkedList();
            
            for (Module module : moduleLoader.getEnabledModules()) {
                for (Addon addon : module.getAddons()) {
                    if (addon.hasPermissions(e.getAuthor(), e.getChannel(), e.getGuild())) {
                        addons.add(addon);
                    }
                }
            }
            
            
            c.next();
            int pageNum = 1;
            int commandsPerPage = 8;
            int pageTotal = (int)Math.ceil((double)addons.size()/commandsPerPage);
            try {
                pageNum = Integer.parseInt(c.get());
            } catch (NumberFormatException ex) {
                
            }
            if (pageNum > pageTotal) {
                pageNum = pageTotal;
            } else if (pageNum < 1) {
                pageNum = 1;
            }
            int maxAddonIndex = Math.min(pageNum*8, addons.size());
            List<Addon> shownAddons = addons.subList((pageNum-1)*8, maxAddonIndex);
            
            
            String finalString = "";
            if (e.getChannel().isPrivate()) {
                finalString += "*[in PM]*\n";
            } else {
                finalString += "*[in #" + e.getChannel().getName() + "]*\n";
            }
            for (Addon addon : shownAddons) {
                finalString += addon.getShortHelp() + "\n";
            }
            
            EmbedObject.EmbedFieldObject fo = new EmbedObject.EmbedFieldObject("Available Commands", finalString, false);
            
            eo.fields = new EmbedObject.EmbedFieldObject[] {fo};
            eo.color = getColour();
            
            eo.footer = new EmbedObject.FooterObject("Page " + pageNum + "/" + pageTotal, null, null);
            
            e.getAuthor().getOrCreatePMChannel().sendMessage(eo);
            return true;
        } else {
            return false;
        }
        
    }
    
}
