/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules.help;

import addon.Addon;
import container.StringFastContainer;
import dbot.Module;
import dbot.ModuleLoader;
import helpers.ParserUtils;
import modules.Help.HelpAddon;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.Event;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.api.internal.json.objects.EmbedObject.EmbedFieldObject;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

/**
 *
 * @author bowen
 */
public class Help implements Addon, HelpAddon {

    @Override
    public String getName() {
        return "Help";
    }

    @Override
    public String getDescription() {
        return "Automatically generates help templates for loaded addons.";
    }

    @Override
    public String getFullHelp() {
        return "**!help** - *Shows the general help page*\n" +
                "!<command> **--help** - *Shows a specific command's help page*";
    }
    
    @Override
    public String getShortHelp() {
        return "**!help** - *Shows the general help page*\n" +
                "!<command> **--help** - *Shows a specific command's help page*";
    }
    
    @Override
    public int getColour() {
        return 5563639;
    }

    @Override
    public short getUid() {
        return 0;
    }

    @Override
    public boolean hasPermissions(IUser user, IChannel channel, IGuild guild) {
        return true;
    }

    @Override
    public boolean isTrigger(IDiscordClient client, Event e) {
        if (e instanceof MessageReceivedEvent) {
            MessageReceivedEvent em = (MessageReceivedEvent) e;
            String rawString = em.getMessage().getContent();
            String botName = client.getOurUser().getName();
            String commandName = "help";
            return ParserUtils.startsWithCaseless(rawString, "!" + botName + " " + commandName) || ParserUtils.startsWithCaseless(rawString, "!" + commandName) ;
        }
        return false;
    }
    @Override
    public boolean triggerMessage(IDiscordClient client, MessageReceivedEvent e, ModuleLoader moduleLoader) {
        
         if (e.getMessage().getContent().indexOf("--help") > 1) {
            for (Module module : moduleLoader.getEnabledModules()) {
                for (Addon addon : module.getAddons()) {
                    if (addon.isTrigger(client, e)) {
                        
                        EmbedObject eo = new EmbedObject();
                        String finalString = "*" + addon.getName() + "*\n" + addon.getFullHelp();
                        EmbedFieldObject fo = new EmbedFieldObject("Help", finalString, false);
                        eo.fields = new EmbedFieldObject[] {fo};
                        eo.color = addon.getColour();
                        
                        e.getAuthor().getOrCreatePMChannel().sendMessage(eo);
                        
                        break;
                    }
                }
            }
            return true;
        }
         
        StringFastContainer c = new StringFastContainer(e.getMessage().getContent(), "!");
        if (c.get().equalsIgnoreCase(client.getOurUser().getName())) {
            c.next();
        }
        if (c.get().equalsIgnoreCase("help")) {
            IUser bot = client.getOurUser();
            EmbedObject eo = new EmbedObject();
            //eo.author = new EmbedObject.AuthorObject(bot.getName(), "https://github.com/bloc97/Discord-BlocAPI", bot.getAvatarURL(), null);
            //eo.title = "Help";
            
            String finalString = "";
            for (Addon addon : moduleLoader.getModuleByUid(-8461062l).getAddons()) {
                finalString += addon.getShortHelp()+ "\n";
            }
            
            EmbedFieldObject fo = new EmbedFieldObject("Help", finalString, false);
            
            eo.fields = new EmbedFieldObject[] {fo};
            eo.color = getColour();
            
            e.getAuthor().getOrCreatePMChannel().sendMessage(eo);
            return true;
        } else {
            return false;
        }
    }
    
}
