/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbot;

import helpers.NumberUtils;
import helpers.ParserUtils;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

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
            System.out.println("Discord Bot API Key: " + dApiKey.substring(0, showLength) + ParserUtils.repeatString("*", dApiKey.length() - showLength));
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
        
        
        try {
            JDA bot = new JDABuilder(AccountType.BOT)
                    .setToken(dApiKey)
                    .addEventListener(moduleLoader)
                    .buildBlocking();
        } catch (LoginException | IllegalArgumentException | InterruptedException | RateLimitedException ex) {
            Logger.getLogger(BotBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
