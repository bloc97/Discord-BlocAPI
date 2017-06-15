/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbot;

import addon.Addon;
import container.ContainerSettings;
import container.StringAdvancedContainer;
import container.StringContainer;
import container.TokenAdvancedContainer;
import container.TokenContainer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.Event;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import token.TokenConverter;

/**
 *
 * @author bowen
 * @param <T>
 */
public abstract class Module<T extends Addon> {
    private IDiscordClient botClient = null;
    private final LinkedList<T> addons;
    
    private ModuleLoader moduleLoader = null;
    private boolean isLoaded = false;
    private int id = -1;
    
    private final ContainerSettings containerSettings;
    private final TokenConverter tokenConverter;
    private final BotCommandTrigger commandTrigger;
    
    public Module(ContainerSettings containerSettings, TokenConverter tokenConverter, BotCommandTrigger commandTrigger) {
        this.containerSettings = containerSettings;
        this.tokenConverter = tokenConverter;
        this.commandTrigger = commandTrigger;
        addons = new LinkedList<>();
    }
    public Module(ContainerSettings containerSettings, TokenConverter tokenConverter, BotCommandTrigger commandTrigger, T... addonsList) {
        this.containerSettings = containerSettings;
        this.tokenConverter = tokenConverter;
        this.commandTrigger = commandTrigger;
        addons = new LinkedList<>();
        addons.addAll(Arrays.asList(addonsList));
    }
    public final void add(T addon) {
        if (!addons.contains(addon)) {
            addons.add(addon);
        }
    }
    
    public List<T> getAddons() {
        return new ArrayList<>(addons);
    }
    
    public IDiscordClient getBotClient() {
        return botClient;
    }
    
    public int getId() {
        if (isLoaded) {
            return id;
        } else {
            throw new IllegalStateException("Cannot get ID before module is loaded.");
        }
    }
    public ModuleLoader getModuleLoader() {
        if (isLoaded && moduleLoader != null) {
            return moduleLoader;
        } else {
            throw new IllegalStateException("Cannot get ModuleLoader before module is loaded.");
        }
    }
    public void confirmLoad(ModuleLoader moduleLoader, int id) {
        if (isLoaded) {
            throw new IllegalStateException("Cannot load the same module object twice.");
        } else {
            this.moduleLoader = moduleLoader;
            this.id = id;
            isLoaded = true;
            System.out.println("Module [" + getShortName() + "] Enabled.");
        }
    }
    
    public void confirmUnload(ModuleLoader moduleLoader, int id) {
        if (!isLoaded) {
            throw new IllegalStateException("Module is already unloaded!");
        }
        if (id == this.id && moduleLoader == this.moduleLoader) {
            this.moduleLoader = null;
            this.id = -1;
            isLoaded = false;
            System.out.println("Module [" + getShortName() + "] Disabled.");
        } else {
            throw new IllegalStateException("Wrong ID or ModuleLoader!, only the original ModuleLoader can unload this module.");
        }
    }
    
    public abstract String getFullName();
    public abstract String getFullDescription();
    public abstract String getFullInfo();
    
    public abstract String getShortName();
    public abstract String getShortDescription();
    
    public abstract String getVersion();
    public abstract String getAuthor();
    public abstract long getUid();

    public ContainerSettings getContainerSettings() {
        return containerSettings;
    }
    public TokenConverter getTokenConverter() {
        return tokenConverter;
    }
    public BotCommandTrigger getCommandTrigger() {
        return commandTrigger;
    }
    
    public boolean onEvent(Event e) {
        if (e instanceof ReadyEvent) {
            ready((ReadyEvent) e);
            return false;
        } else if (e instanceof MessageReceivedEvent) {
            MessageReceivedEvent em = (MessageReceivedEvent) e;
            BotCommandTrigger commandTrigger = getCommandTrigger();
            if (commandTrigger.isMessageTrigger(botClient, em)) {
                String rawString = commandTrigger.preParse(botClient, em);
                TokenAdvancedContainer container = new TokenAdvancedContainer(botClient, em, new StringAdvancedContainer(rawString, getContainerSettings()), getTokenConverter());
                return onMessage(em, container);
            }
            return false;
        } else {
            return onOtherEvent(e);
        }
    };
    
    public void ready(ReadyEvent e) {
        botClient = e.getClient();
        System.out.println("Module [" + getShortName() + "] Ready.");
        onReady(e);
    }
    
    public abstract boolean onOtherEvent(Event e);
    public abstract boolean onReady(ReadyEvent e);
    public boolean onMessage(MessageReceivedEvent e, TokenAdvancedContainer container) {
        for (T addon : getAddons()) {
            if (addon.hasPermissions(e.getAuthor(), e.getChannel(), e.getGuild())) {
                if (onMessageForEachAddon(addon, e, container)) {
                    return true;
                }
                container.reset();
            }
        }
        return false;
    };
    public abstract boolean onMessageForEachAddon(T addon, MessageReceivedEvent e, TokenAdvancedContainer container);
    
    
}
