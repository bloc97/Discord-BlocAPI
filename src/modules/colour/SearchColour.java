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
import helpers.TextFormatter;
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
public class SearchColour implements Addon, ColourAddon {
    
    private int searchColour = Colour.get3IntFromRGB(1f, 1f, 1f);
    
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
        return searchColour;
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
            StringFastContainer c = new StringFastContainer(em.getMessage().getContent(), "!");
            if (c.get().equalsIgnoreCase(client.getOurUser().getName())) {
                c.next();
            }
            if (c.get().equalsIgnoreCase("colour") && !c.getNext().equalsIgnoreCase("random")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean triggerMessage(IDiscordClient client, MessageReceivedEvent e) {
        
        StringFastContainer c = new StringFastContainer(e.getMessage().getContent(), "!");
        if (c.get().equalsIgnoreCase(client.getOurUser().getName())) {
            c.next();
        }
        if (c.get().equalsIgnoreCase("colour") && !c.getNext().equalsIgnoreCase("random")) {
            
            c.next();
            
            int colour = 0;
            String colourHexString = c.get();
            if (colourHexString.startsWith("#")) {
                colourHexString = colourHexString.substring(1);
            }
            if (colourHexString.length() == 3) {
                colourHexString = "" + colourHexString.charAt(0) + '0' + colourHexString.charAt(1) + '0' + colourHexString.charAt(2) + '0';
            }
            if (colourHexString.length() != 6) {
                return false;
            }
            try {
                colour = Integer.parseInt(colourHexString, 16);
            } catch (NumberFormatException ex) {
                return false;
            }
            
            int closestColourIndex = ColourDatabase.getClosestColourIndex(colour);
            
            int closestColour = ColourDatabase.intDatabase[closestColourIndex];
            String closestColourName = ColourDatabase.database[closestColourIndex][1];
            
            
            EmbedObject eo = new EmbedObject();
            String hexString = TextFormatter.fillBegin(Integer.toHexString(colour), '0', 6);
            String closestHexString = TextFormatter.fillBegin(Integer.toHexString(closestColour), '0', 6);
            
            //System.out.println(hexString + " " + closestHexString);
            //EmbedObject.EmbedFieldObject fo = new EmbedObject.EmbedFieldObject("HEX: #" + hexString.toUpperCase(), "RGB: 0 0 0", true);
            //eo.fields = new EmbedObject.EmbedFieldObject[] {fo};
            eo.footer = new EmbedObject.FooterObject("#" + hexString.toUpperCase(), null, null);
            eo.color = (colour == 0) ? 1 : colour;
            eo.image = new EmbedObject.ImageObject("http://www.colorhexa.com/" + closestHexString + ".png", null, 32, 32);
            eo.author = new EmbedObject.AuthorObject(closestColourName, "http://www.colorhexa.com/" + hexString, null, null);
            eo.description = ((closestColour == colour) ? "" : "*(Closest Colour)*");
            e.getChannel().sendMessage(eo);
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
