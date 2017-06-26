/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules.help;

import addon.Addon;
import container.StringFastContainer;
import container.TokenAdvancedContainer;
import container.TokenContainer;
import container.detector.TokenDetectorContainer;
import container.detector.TokenStringDetector;
import dbot.Module;
import dbot.ModuleLoader;
import helpers.ParserUtils;
import java.awt.Color;
import modules.Help.HelpAddon;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

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
    public boolean hasPermissions(MessageReceivedEvent e) {
        return true;
    }
    
    @Override
    public TokenDetectorContainer getTriggerDetector() {
        return new TokenDetectorContainer(
            new TokenStringDetector("help")
        );
    }
    
    public static void showHelp(MessageReceivedEvent e, Addon addon) {
        
        EmbedBuilder eb = new EmbedBuilder();
        
        String finalString = "*" + addon.getName() + "*\n\n" + addon.getFullHelp();
        
        eb.addField("Help", finalString, false);
        eb.setColor(new Color(addon.getColour()));
        
        e.getChannel().sendMessage(eb.build()).queue();
    }
    
    @Override
    public boolean triggerMessage(JDA client, MessageReceivedEvent e, TokenAdvancedContainer container, ModuleLoader moduleLoader) {
        
         if (e.getMessage().getContent().indexOf("--help") > 1) {
            for (Module<?> module : moduleLoader.getEnabledModules()) {
                for (Addon addon : module.getAddons()) {
                    if (addon.getTriggerDetector().check(container)) {
                        showHelp(e, addon);
                        break;
                    }
                }
            }
            return true;
        }
        
        if (container.getAsString().equalsIgnoreCase("help")) {
            User bot = client.getSelfUser();
            EmbedBuilder eb = new EmbedBuilder();
            
            //eo.author = new EmbedObject.AuthorObject(bot.getName(), "https://github.com/bloc97/Discord-BlocAPI", bot.getAvatarURL(), null);
            //eo.title = "Help";
            
            String finalString = "";
            for (Addon addon : moduleLoader.getModuleByUid(-8461062l).getAddons()) {
                finalString += addon.getShortHelp()+ "\n";
            }
            
            eb.addField("Help", finalString, false);
            eb.setColor(new Color(getColour()));
            
            e.getAuthor().openPrivateChannel().queue((channel) -> {
                channel.sendMessage(eb.build()).queue();
            });
            
            return true;
        } else {
            return false;
        }
    }
    
}
