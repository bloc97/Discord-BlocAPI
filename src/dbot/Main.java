/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbot;

import java.util.Map;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.constant.Region;
import net.rithms.riot.dto.Summoner.Summoner;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.util.DiscordException;

/**
 *
 * @author bowen
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String dApiKey = args[0];
        String rApiKey = args[1];
        System.out.println(dApiKey);
        System.out.println(rApiKey);
        
        
        IDiscordClient bot = createClient(dApiKey, true);
        
        EventDispatcher botDispatcher = bot.getDispatcher();
        botDispatcher.registerListener(new DiscordEvents());
        
        
        
    }
    
    public static IDiscordClient createClient(String token, boolean login) { // Returns a new instance of the Discord client
        ClientBuilder clientBuilder = new ClientBuilder(); // Creates the ClientBuilder instance
        clientBuilder.withToken(token); // Adds the login info to the builder
        try {
            if (login) {
                return clientBuilder.login(); // Creates the client instance and logs the client in
            } else {
                return clientBuilder.build(); // Creates the client instance but it doesn't log the client in yet, you would have to call client.login() yourself
            }
        } catch (DiscordException e) { // This is thrown if there was a problem building the client
            //e.printStackTrace();
            System.out.println(e);
            return null;
        }
    }
    
}
