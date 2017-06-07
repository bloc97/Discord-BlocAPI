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
public class Modules implements Addon, LoaderAccessor {


    @Override
    public String getFullName() {
        return "Modules Addon";
    }

    @Override
    public String getFullDescription() {
        return "Displays the module list when called.";
    }

    @Override
    public String getFullHelp() {
        return "!modules <page> - Displays the module list.";
    }

    @Override
    public String getShortName() {
        return "Modules";
    }

    @Override
    public String getShortHelp() {
        return "!modules <page>";
    }

    @Override
    public short getUid() {
        return 2;
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
            if (c.get().equalsIgnoreCase("modules")) {
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
        
        StringPreviewContainer c = new StringPreviewContainer(e.getMessage().getContent(), "!");
        if (c.get().equalsIgnoreCase(client.getOurUser().getName())) {
            c.next();
        }
        if (c.get().equalsIgnoreCase("modules")) {
            String finalString = "";
            for (Module module : moduleLoader.getEnabledModules()) {
                finalString += module.getShortName() + " v" + module.getVersion() + " (by " + module.getAuthor() + ") - " + module.getShortDescription()+ "\n";
            }
            e.getChannel().sendMessage("```\n" + finalString + "```");
            return true;
        } else {
            return false;
        }
    }

}
