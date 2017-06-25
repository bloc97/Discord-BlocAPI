/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbot;

import container.ContainerSettings;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import modules.Debug;
import modules.Help;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import token.TokenConverter;

/**
 *
 * @author bowen
 */
public class ModuleLoader extends ListenerAdapter {
    
    private final Set<Module> modules;
    private final Set<Module> disabledModules;
    
    private int currentIdIndex = 0;
    
    public ModuleLoader(ContainerSettings containerSettings, TokenConverter tokenConverter, BotCommandTrigger commandTrigger, Module... modulesList) {
        modules = new LinkedHashSet<>();
        disabledModules = new HashSet<>();
        add(new Help(containerSettings, tokenConverter, commandTrigger));
        
        for (Module module : modulesList) {
            add(module);
        }
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
    public Module<?> getModuleByUid(long uid) {
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
    public Module<?> getModuleById(long id) {
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
    
    @Override
    public void onGenericEvent(Event e) {
        
        LinkedList<Module> erronousModules = null;
        
        for (Module module : modules) {
            try {
                if (module.onEvent(e)) {
                    break;
                }
            } catch (Exception ex) {
                if (erronousModules == null) {
                    erronousModules = new LinkedList<>();
                }
                erronousModules.add(module);
                ex.printStackTrace();
                System.out.println(module.getShortName() + " Module Error: " + ex);
            }
        }
        
        if (erronousModules != null) {
            erronousModules.forEach((module) -> {
                disable(module);
            });
        }

        if (e instanceof ReadyEvent) {
            System.out.println("Bot Ready.");
        }

    }
}
