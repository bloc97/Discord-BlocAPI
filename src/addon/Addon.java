/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package addon;

import container.detector.TokenDetectorContainer;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 *
 * @author bowen
 */
public interface Addon {
    
    /**
     * Returns the addon's name, used by the default Help module.
     * @return
     */
    public String getName();
    
    /**
     * Returns the addon's description, used by the default Help module.
     * @return
     */
    public String getDescription();
    
    /**
     * Returns a full help page, used by the default Help module.
     * @return
     */
    public String getFullHelp();

    /**
     * Returns a single help sentence, used by the default Help module.
     * @return
     */
    public String getShortHelp();
    
    /**
     * Returns the addon's embed colour, used by the default Help module.
     * @return
     */
    public int getColour();
    
    /**
     * Returns the addon's unique identifier, used by the ModuleLoader and the default Help module.
     * @return
     */
    public short getUid();
    
    /**
     * Determines what message should have triggered the addon, used by the help module to display the help page when calling the addon with --help.
     * @param user
     * @param channel
     * @param guild
     * @return
     */
    public TokenDetectorContainer getTriggerDetector();
    
    /**
     * Determines if the addon should be shown to the user by the Help module.
     * @param user
     * @param channel
     * @param guild
     * @return
     */
    public boolean hasPermissions(MessageReceivedEvent e);
    
    
}
