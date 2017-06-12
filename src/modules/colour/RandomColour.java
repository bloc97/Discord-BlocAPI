/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules.colour;

import addon.Addon;
import container.StringFastContainer;
import helpers.Colour;
import helpers.ParserUtils;
import helpers.Random;
import helpers.OtherUtils;
import java.util.Map;
import java.util.Map.Entry;
import modules.Colour.ColourAddon;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.Event;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

/**
 *
 * @author bowen
 */
public class RandomColour implements Addon, ColourAddon {
    
    private int randomColour = Colour.get3IntFromRGB(1f, 1f, 1f);
    
    @Override
    public String getName() {
        return "Random Colour";
    }

    @Override
    public String getDescription() {
        return "Generates random colours.";
    }

    @Override
    public String getFullHelp() {
        return "**!colour** random - *Generates random colours*";
    }

    @Override
    public String getShortHelp() {
        return "**!colour** random - *Generates random colours*";
    }

    @Override
    public int getColour() {
        return randomColour;
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
            String commandName = "colour random";
            return ParserUtils.startsWithCaseless(rawString, "!" + botName + " " + commandName) || ParserUtils.startsWithCaseless(rawString, "!" + commandName) ;
        }
        return false;
    }

    @Override
    public boolean triggerMessage(IDiscordClient client, MessageReceivedEvent e) {
        
        StringFastContainer c = new StringFastContainer(e.getMessage().getContent(), "!");
        if (c.get().equalsIgnoreCase(client.getOurUser().getName())) {
            c.next();
        }
        if (c.get().equalsIgnoreCase("colour") && c.getNext().equalsIgnoreCase("random")) {
            
            c.next();
            c.next();
            
            int number = 1;
            
            try {
                number = Integer.parseInt(c.get());
            } catch (NumberFormatException ex) {
                
            }
            number = OtherUtils.bound(number, 1, 4);
            
            if (number == 1) {
            
                Entry<Integer, String> randomEntry = getRandomColour();
                randomColour = randomEntry.getKey();

                EmbedObject eo = new EmbedObject();
                String hexString = OtherUtils.fillBegin(Integer.toHexString(randomColour), '0', 6);
                eo.footer = new EmbedObject.FooterObject("#" + hexString.toUpperCase(), null, null);
                eo.color = (randomColour == 0) ? 1 : randomColour;
                eo.image = new EmbedObject.ImageObject("http://www.colorhexa.com/" + hexString + ".png", null, 32, 32);
                eo.author = new EmbedObject.AuthorObject(randomEntry.getValue(), "http://www.colorhexa.com/" + hexString, null, null);

                e.getChannel().sendMessage(eo);
                
            } else {
                
                for (int i=0; i<number; i++) {
                    
                    Entry<Integer, String> randomEntry = getRandomColour();
                    randomColour = randomEntry.getKey();

                    EmbedObject eo = new EmbedObject();
                    String hexString = OtherUtils.fillBegin(Integer.toHexString(randomColour), '0', 6);
                    eo.footer = new EmbedObject.FooterObject("#" + hexString.toUpperCase(), "http://www.colorhexa.com/" + hexString + ".png", null);
                    eo.color = (randomColour == 0) ? 1 : randomColour;
                    eo.author = new EmbedObject.AuthorObject(randomEntry.getValue(), "http://www.colorhexa.com/" + hexString, null, null);
                    
                    e.getChannel().sendMessage(eo);
                }
                
            }
            return true;
        }
        return false;
    }
    
    public static Entry<Integer, String> getRandomColour() {
        int index = Random.randomArrayIndex(ColourDatabase.database);
        int colour = ColourDatabase.intDatabase[index];
        String name = ColourDatabase.database[index][1];
            
        final int finalColour = colour;
        final String finalName = name;
        return new Entry<Integer, String>() {
            @Override
            public Integer getKey() {
                return finalColour;
            }

            @Override
            public String getValue() {
                return finalName;
            }

            @Override
            public String setValue(String value) {
                return finalName;
            }
        };
    }
    
}
