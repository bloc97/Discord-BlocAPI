/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbot;

import helpers.OtherUtils;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.util.DiscordException;

/**
 *
 * @author bowen
 */
public abstract class BotBuilder {
    
    public static void buildBot(ModuleLoader moduleLoader) {
        String dApiKey = "";
        try {
            Path path = FileSystems.getDefault().getPath("dapi.key");
            dApiKey = Files.readAllLines(path).get(0);

            int showLength = Math.min(16, dApiKey.length()/2);
            System.out.println("Discord Bot API Key: " + dApiKey.substring(0, showLength) + OtherUtils.repeatString("*", dApiKey.length() - showLength));
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
        
        IDiscordClient bot = createClient(dApiKey, true);
        
        EventDispatcher botDispatcher = bot.getDispatcher();
        botDispatcher.registerListener(moduleLoader);
        
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
