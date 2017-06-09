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
 */
public abstract class Module {
    private IDiscordClient botClient = null;
    private final LinkedList<Addon> addons;
    
    private ModuleLoader moduleLoader = null;
    private boolean isLoaded = false;
    private int id = -1;
    
    public Module() {
        addons = new LinkedList<>();
    }
    public Module(Addon... addonsList) {
        addons = new LinkedList<>();
        addons.addAll(Arrays.asList(addonsList));
    }
    public final void add(Addon addon) {
        if (!addons.contains(addon)) {
            addons.add(addon);
        }
    }
    
    public List<Addon> getAddons() {
        return new ArrayList(addons);
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
    
    public abstract ContainerSettings getContainerSettings();
    public abstract TokenConverter getTokenConverter();
    public abstract BotCommandTrigger getCommandTrigger();
    
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
    public abstract boolean onMessage(MessageReceivedEvent e, TokenAdvancedContainer container);
    
    
}
