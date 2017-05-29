/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbot;

import container.UserCommand;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import modules.Help;
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
        for (Module module : modulesList) {
            add(module);
        }
        add(new Help());
    }
    
    public final void add(Module module) {
        if (disabledModules.contains(module)) {
            disabledModules.remove(module);
        }
        modules.add(module);
        module.confirmLoad(this, currentIdIndex);
        currentIdIndex++;
    }
    
    public void enable(Module module) {
        if (disabledModules.contains(module)) {
            disabledModules.remove(module);
            module.confirmLoad(this, currentIdIndex);
            currentIdIndex++;
            modules.add(module);
        }
    }
    public void disable(Module module) {
        if (modules.contains(module)) {
            modules.remove(module);
            module.confirmUnload(this, module.getId());
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
        modules.forEach((module) -> {
            list.add(module);
        });
        disabledModules.forEach((module) -> {
            list.add(module);
        });
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
        
        LinkedList<Module> erronousModules = new LinkedList<>();
        
        modules.forEach((module) -> {
            try {
                module.onEvent(e);
            } catch (Exception ex) {
                erronousModules.add(module);
                ex.printStackTrace();
                System.out.println(module.getShortName() + " Module Error: " + ex);
                System.out.println("Disabling " + module.getShortName() + " Module.");
            }
        });
        
        erronousModules.forEach((module) -> {
            disable(module);
        });
    }
    @EventSubscriber
    public void onReady(ReadyEvent e) {
        System.out.println("Bot Ready.");
        //e.getClient().online("Not Connected");
        LinkedList<Module> erronousModules = new LinkedList<>();
        
        modules.forEach((module) -> {
            try {
                module.ready(e);
                System.out.println(module.getShortName() + " Module Ready.");
            } catch (Exception ex) {
                erronousModules.add(module);
                ex.printStackTrace();
                System.out.println(module.getShortName() + " Module Error: " + ex);
                System.out.println("Disabling " + module.getShortName() + " Module.");
            }
        });
        
        erronousModules.forEach((module) -> {
            disable(module);
        });
        
        //bot.online("League of Legends Dev API");
    }
    @EventSubscriber
    public void onMessageReceived(MessageReceivedEvent e) {
        IMessage message = e.getMessage();
        UserCommand command = new UserCommand(message.getContent());
        
        LinkedList<Module> erronousModules = new LinkedList<>();
        
        modules.forEach((module) -> {
            try {
                module.onMessage(e, command.clone());
            } catch (Exception ex) {
                erronousModules.add(module);
                ex.printStackTrace();
                System.out.println(module.getShortName() + " Module Error: " + ex);
                System.out.println("Disabling " + module.getShortName() + " Module.");
            }
        });
        
        erronousModules.forEach((module) -> {
            disable(module);
        });
        
    }
    
}
