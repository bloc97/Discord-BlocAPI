/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import sx.blah.discord.api.events.Event;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;

/**
 *
 * @author bowen
 */
public class ModuleLoader {
    
    private final Set<Module> modules;
    private final Set<Module> disabledModules;
    
    private int currentIdIndex = 0;
    
    public ModuleLoader(Module... modulesList) {
        modules = new HashSet<>();
        disabledModules = new HashSet<>();
        modules.addAll(Arrays.asList(modulesList));
    }
    
    public void add(Module module) {
        if (disabledModules.contains(module)) {
            disabledModules.remove(module);
        }
        modules.add(module);
        System.out.println(module.getShortName() + " Module Enabled.");
    }
    
    public void enable(Module module) {
        if (disabledModules.contains(module)) {
            disabledModules.remove(module);
            modules.add(module);
        }
    }
    public void disable(Module module) {
        if (modules.contains(module)) {
            modules.remove(module);
            disabledModules.add(module);
        }
    }
    public Module getModuleByUid(long uid) {
        for (Module module : modules) {
            if (module.getUid() == uid) {
                return module;
            }
        }
        for (Module module : disabledModules) {
            if (module.getUid() == uid) {
                return module;
            }
        }
        return null;
    }
    public Module getModuleById(long id) {
        for (Module module : modules) {
            if (module.getId()== id) {
                return module;
            }
        }
        for (Module module : disabledModules) {
            if (module.getId()== id) {
                return module;
            }
        }
        return null;
    }
    public List<Module> getEnabledModules() {
        return new ArrayList<>(modules);
    }
    public List<Module> getDisabledModules() {
        return new ArrayList<>(disabledModules);
    }
    public List<Module> getAllModules() {
        LinkedList<Module> list = new LinkedList<>();
        for (Module module : modules) {
            list.add(module);
        }
        for (Module module : disabledModules) {
            list.add(module);
        }
        return new ArrayList<>(list);
    }
    
    @EventSubscriber
    public void onEvent(Event e) {
        if (e instanceof ReadyEvent) {
            return;
        }
        if (e instanceof MessageReceivedEvent) {
            return;
        }
        
        modules.forEach((module) -> {
            module.onEvent(e);
        });
    }
    @EventSubscriber
    public void onReady(ReadyEvent e) {
        System.out.println("Bot Ready.");
        //e.getClient().online("Not Connected");
        
        modules.forEach((module) -> {
            module.onReady(e);
        });
        //bot.online("League of Legends Dev API");
    }
    @EventSubscriber
    public void onMessageReceived(MessageReceivedEvent e) {
        IMessage message = e.getMessage();
        
        UserCommand command = new UserCommand(message.getContent());
        
        modules.forEach((module) -> {
            module.onMessage(e, command);
            command.reset();
        });
        
    }
    
}
