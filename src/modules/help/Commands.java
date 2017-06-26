/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules.help;

import addon.Addon;
import container.TokenAdvancedContainer;
import container.detector.TokenDetectorContainer;
import container.detector.TokenStringDetector;
import dbot.Module;
import dbot.ModuleLoader;
import helpers.NumberUtils;
import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import modules.Help.HelpAddon;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 *
 * @author bowen
 */
public class Commands implements Addon, HelpAddon {

    @Override
    public String getName() {
        return "Commands";
    }

    @Override
    public String getDescription() {
        return "Displays the command list when called.";
    }

    @Override
    public String getFullHelp() {
        return "**!commands** <page> - *Shows the command list*";
    }

    @Override
    public String getShortHelp() {
        return "**!commands** <page> - *Shows the command list*";
    }
    
    @Override
    public int getColour() {
        return 5563639;
    }

    @Override
    public short getUid() {
        return 1;
    }

    @Override
    public boolean hasPermissions(MessageReceivedEvent e) {
        return true;
    }

    @Override
    public TokenDetectorContainer getTriggerDetector() {
        return new TokenDetectorContainer(
            new TokenStringDetector("commands")
        );
    }
    
    @Override
    public boolean triggerMessage(JDA client, MessageReceivedEvent e, TokenAdvancedContainer container, ModuleLoader moduleLoader) {
        
        if (container.getAsString().equalsIgnoreCase("commands")) {
            container.next();
            
            EmbedBuilder eb = new EmbedBuilder();
            
            List<Addon> addons = new LinkedList();
            
            for (Module<?> module : moduleLoader.getEnabledModules()) {
                for (Addon addon : module.getAddons()) {
                    if (addon.hasPermissions(e)) {
                        addons.add(addon);
                    }
                }
            }
            
            int pageNum = container.getAsNumber().intValue();
            int commandsPerPage = 8;
            int pageTotal = (int)Math.ceil((double)addons.size()/commandsPerPage);
            
            pageNum = NumberUtils.bound(pageNum, 1, pageTotal);
            
            int maxAddonIndex = Math.min(pageNum*8, addons.size());
            List<Addon> shownAddons = addons.subList((pageNum-1)*8, maxAddonIndex);
            
            
            String finalString = "";
            if (e.getChannelType() == ChannelType.PRIVATE) {
                finalString += "*[in PM]*\n";
            } else {
                finalString += "*[in #" + e.getChannel().getName() + "]*\n";
            }
            for (Addon addon : shownAddons) {
                finalString += addon.getShortHelp() + "\n";
            }
            
            eb.addField("Available Commands", finalString, false);
            eb.setColor(new Color(getColour()));
            eb.setFooter("Page " + pageNum + "/" + pageTotal, null);
            
            e.getAuthor().openPrivateChannel().queue((channel) -> {
                channel.sendMessage(eb.build()).queue();
            });
            return true;
        } else {
            return false;
        }
        
    }
    
}
