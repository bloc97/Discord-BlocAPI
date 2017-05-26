/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules;

import addon.Addon;
import dbot.Module;
import dbot.UserCommand;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.Event;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 *
 * @author bowen
 */
public class Help extends Module {

    public Help(IDiscordClient client, Addon... addonsList) {
        super(client, addonsList);
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
    public void onEvent(Event e) {
        System.out.println(getShortName() + " Module Enabled.");
    }

    @Override
    public void onReady(ReadyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onMessage(MessageReceivedEvent e, UserCommand c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
