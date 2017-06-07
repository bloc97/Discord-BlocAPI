/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules;

import addon.Addon;
import container.StringPreviewContainer;
import dbot.Module;
import dbot.ModuleLoader;
import modules.help.Commands;
import modules.help.LoaderAccessor;
import modules.help.Modules;
import sx.blah.discord.api.events.Event;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 *
 * @author bowen
 */
public class Help extends Module {
    
    public Help() {
        add(new modules.help.Help());
        add(new Commands());
        add(new Modules());
    }

    @Override
    public String getFullName() {
        return "Default Help Module";
    }

    @Override
    public String getFullDescription() {
        return "Automatically generates help and commands pages.";
    }

    @Override
    public String getFullHelp() {
        return "";
    }

    @Override
    public String getShortName() {
        return "Help";
    }

    @Override
    public String getShortDescription() {
        return "Displays Help and Commands pages.";
    }

    @Override
    public String getVersion() {
        return "0.1";
    }

    @Override
    public String getAuthor() {
        return "Bloc97";
    }

    @Override
    public long getUid() {
        return -8461062l;
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
        StringPreviewContainer c = new StringPreviewContainer(e.getMessage().getContent(), "!");
        
        //if (!(e.getChannel().isPrivate() || c.get().equalsIgnoreCase(botClient.getOurUser().getName()) || e.getMessage().getMentions().contains(botClient.getOurUser()))) {
            //return;
        //} 
        if (e.getChannel().isPrivate() || c.get().equalsIgnoreCase(botClient.getOurUser().getName()) || (e.getMessage().getMentions() != null && e.getMessage().getMentions().contains(botClient.getOurUser()))) {
            for (Addon addon : addons) {
                if (addon.hasPermissions(e.getAuthor(), e.getChannel(), e.getGuild())) {
                    //if (addon.isTrigger(botClient, e)) {

                    LoaderAccessor la = (LoaderAccessor) addon;
                    if (la.triggerMessage(botClient, e, getModuleLoader())) {
                        return true;
                    }
                    //}
                }
            }
        }
        return false;
    }

    
}
