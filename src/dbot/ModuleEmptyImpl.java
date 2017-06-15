/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbot;

import addon.Addon;
import container.ContainerSettings;
import container.TokenAdvancedContainer;
import sx.blah.discord.api.events.Event;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import token.TokenConverter;

/**
 *
 * @author bowen
 */
public abstract class ModuleEmptyImpl<T extends Addon> extends Module<T> {

    public ModuleEmptyImpl(ContainerSettings containerSettings, TokenConverter tokenConverter, BotCommandTrigger commandTrigger) {
        super(containerSettings, tokenConverter, commandTrigger);
    }

    public ModuleEmptyImpl(ContainerSettings containerSettings, TokenConverter tokenConverter, BotCommandTrigger commandTrigger, T... addonList) {
        super(containerSettings, tokenConverter, commandTrigger, addonList);
    }
    
    @Override
    public String getFullDescription() {
        return "No description yet.";
    }

    @Override
    public String getFullInfo() {
        return "No info yet.";
    }

    @Override
    public String getShortName() {
        return getFullName();
    }

    @Override
    public String getShortDescription() {
        return getFullDescription();
    }

    @Override
    public String getVersion() {
        return "0";
    }

    @Override
    public boolean onOtherEvent(Event e) {
        return false;
    }

    @Override
    public boolean onReady(ReadyEvent e) {
        return false;
    }

    
}
