/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules;

import addon.Addon;
import container.ContainerSettings;
import container.StringContainer;
import container.StringFastContainer;
import container.TokenAdvancedContainer;
import container.TokenContainer;
import dbot.BotCommandDefaultTrigger;
import dbot.BotCommandTrigger;
import dbot.Module;
import dbot.ModuleLoader;
import modules.Help.HelpAddon;
import modules.help.Commands;
import modules.help.Modules;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import token.DefaultTokenConverter;
import token.TokenConverter;

/**
 *
 * @author bowenimport token.TokenConverter;

 */
public class Help extends Module<HelpAddon> {
    
    public interface HelpAddon extends Addon {
        public boolean triggerMessage(JDA client, MessageReceivedEvent e, TokenAdvancedContainer container, ModuleLoader moduleLoader);
    }
    
    public Help(ContainerSettings containerSettings, TokenConverter tokenConverter, BotCommandTrigger commandTrigger) {
        super(containerSettings, tokenConverter, commandTrigger, new modules.help.Help(), new Commands(), new Modules());
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
    public String getFullInfo() {
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
    public boolean onMessageForEachAddon(HelpAddon addon, MessageReceivedEvent e, TokenAdvancedContainer container) {
        return addon.triggerMessage(getBotClient(), e, container, getModuleLoader());
    }
    
}
