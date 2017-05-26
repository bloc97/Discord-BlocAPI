/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbot;

import addon.Addon;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.LinkedHashSet;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.Event;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 *
 * @author bowen
 */
public abstract class Module {
    protected IDiscordClient botClient;
    protected final LinkedList<Addon> addons;
    
    private ModuleLoader moduleLoader = null;
    private boolean isLoaded = false;
    private int id = -1;
    
    public Module(IDiscordClient client, Addon... addonsList) {
        addons = new LinkedList<>();
        botClient = client;
        addons.addAll(Arrays.asList(addonsList));
    }
    public final void add(Addon addon) {
        if (!addons.contains(addon)) {
            addons.add(addon);
        }
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
        }
    }
    
    public void confirmUnload(ModuleLoader moduleLoader, int id) {
        if (id == this.id && moduleLoader == this.moduleLoader) {
            this.moduleLoader = null;
            this.id = -1;
            isLoaded = false;
        } else {
            throw new IllegalStateException("Wrong ID or ModuleLoader!, only the original ModuleLoader can unload this module.");
        }
    }
    
    public abstract String getFullName();
    public abstract String getFullDescription();
    public abstract String getFullHelp();
    
    public abstract String getShortName();
    public abstract String getShortDescription();
    
    public abstract String getVersion();
    public abstract String getAuthor();
    public abstract long getUid();
    
    public abstract void onEvent(Event e);
    public abstract void onReady(ReadyEvent e);
    public abstract void onMessage(MessageReceivedEvent e, UserCommand c);
    
    
}
