/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules;

import addon.Addon;
import dbot.Module;
import helpers.Colour;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.Event;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

/**
 *
 * @author bowen
 */
public class Debug extends Module {
    
    public Debug() {
        for (int n=0; n<50; n++) {
            final int i = n;
            add(new Addon() {
                @Override
                public String getName() {
                    return "Debug Addon #" + i;
                }

                @Override
                public String getDescription() {
                    return "Debug Addon #" + i;
                }

                @Override
                public String getFullHelp() {
                    return "**!debug" + i + "** - *Debug Addon Full Help #" + i + "*";
                }

                @Override
                public String getShortHelp() {
                    return "**!debug" + i + "** - *Debug Addon Short Help #" + i + "*";
                }

                @Override
                public int getColour() {
                    return Colour.get3IntFromRGB(Math.random(), Math.random(), Math.random());
                }

                @Override
                public short getUid() {
                    return (short)i;
                }

                @Override
                public boolean hasPermissions(IUser user, IChannel channel, IGuild guild) {
                    return true;
                }

                @Override
                public boolean isTrigger(IDiscordClient client, Event e) {
                    if (e instanceof MessageReceivedEvent) {
                        MessageReceivedEvent em = (MessageReceivedEvent) e;
                        if (em.getMessage().getContent().startsWith("!debug" + i)) {
                            return true;
                        }
                    }
                    return false;
                }
            });
        }
    }
    
    @Override
    public String getFullName() {
        return "Debug Module";
    }

    @Override
    public String getFullDescription() {
        return "";
    }

    @Override
    public String getFullInfo() {
        return "";
    }

    @Override
    public String getShortName() {
        return "Debug";
    }

    @Override
    public String getShortDescription() {
        return "";
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
        return -24361349l;
    }

    @Override
    public boolean onOtherEvent(Event e) {
        return false;
    }

    @Override
    public boolean onReady(ReadyEvent e) {
        return false;
    }

    @Override
    public boolean onMessage(MessageReceivedEvent e) {
        return false;
    }

    
}
