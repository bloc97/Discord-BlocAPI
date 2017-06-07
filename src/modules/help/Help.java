/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules.help;

import addon.Addon;
import container.StringPreviewContainer;
import dbot.Module;
import dbot.ModuleLoader;
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
public class Help implements Addon, LoaderAccessor {

    @Override
    public String getFullName() {
        return "Help Addon";
    }

    @Override
    public String getFullDescription() {
        return "Automatically generates help templates for loaded addons.";
    }

    @Override
    public String getFullHelp() {
        return "!help - Displays the main help page\n" +
                "!<command> --help - Displays help for specific command";
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
        if (e instanceof MessageReceivedEvent) {
            StringPreviewContainer c = new StringPreviewContainer(((MessageReceivedEvent)e).getMessage().getContent(), "!");
            if (c.get().equalsIgnoreCase(client.getOurUser().getName())) {
                c.next();
            }
            if (c.get().equalsIgnoreCase("help")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean trigger(IDiscordClient client, Event e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean triggerReady(IDiscordClient client, ReadyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean triggerMessage(IDiscordClient client, MessageReceivedEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean triggerMessage(IDiscordClient client, MessageReceivedEvent e, ModuleLoader moduleLoader) {
        
         if (e.getMessage().getContent().indexOf("--help") > 1) {
            for (Module module : moduleLoader.getEnabledModules()) {
                for (Addon addon : module.getAddons()) {
                    if (addon.isTrigger(client, e)) {
                        e.getAuthor().getOrCreatePMChannel().sendMessage("```\n" + addon.getFullHelp()+ "\n" + "```");
                        break;
                    }
                }
            }
            return true;
        }
         
        StringPreviewContainer c = new StringPreviewContainer(e.getMessage().getContent(), "!");
        if (c.get().equalsIgnoreCase(client.getOurUser().getName())) {
            c.next();
        }
        if (c.get().equalsIgnoreCase("help")) {
            String finalString = 
                    "Bot Help Commands\n" + 
                    "!<command> --help (Help for a specific command)\n" +
                    "!commands (Command list)\n" +
                    "!modules (Modules list)\n";
            e.getAuthor().getOrCreatePMChannel().sendMessage("```\n" + finalString + "```");
            return true;
        } else {
            return false;
        }
    }
    
}
