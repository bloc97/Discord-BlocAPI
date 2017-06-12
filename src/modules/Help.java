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
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.Event;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import token.DefaultTokenConverter;
import token.TokenConverter;

/**
 *
 * @author bowenimport token.TokenConverter;

 */
public class Help extends Module {

    public interface HelpAddon extends Addon {
        public boolean triggerMessage(IDiscordClient client, MessageReceivedEvent e, TokenAdvancedContainer container, ModuleLoader moduleLoader);
    }
    
    public Help() {
        super(new modules.help.Help(), new Commands(), new Modules());
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
    
    private static final ContainerSettings settings = ContainerSettings.buildSettings("!");
    @Override
    public ContainerSettings getContainerSettings() {
        return settings;
    }

    private static final TokenConverter converter = TokenConverter.getDefault();
    @Override
    public TokenConverter getTokenConverter() {
        return converter;
    }
    
    private static final BotCommandTrigger trigger = BotCommandTrigger.getDefault(settings);
    @Override
    public BotCommandTrigger getCommandTrigger() {
        return trigger;
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
    public boolean onMessage(MessageReceivedEvent e, TokenAdvancedContainer container) {
        
        for (Addon addon : getAddons()) {
            if (addon.hasPermissions(e.getAuthor(), e.getChannel(), e.getGuild())) {
                
                HelpAddon ha = (HelpAddon) addon;
                if (ha.triggerMessage(getBotClient(), e, container, getModuleLoader())) {
                    return true;
                }
                container.reset();
            }
        }
        return false;
    }
    
}
