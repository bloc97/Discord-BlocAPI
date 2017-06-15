/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package addon;

/**
 *
 * @author bowen
 */
public abstract class AddonEmptyImpl implements Addon {
    
    @Override
    public String getDescription() {
        return "No description yet.";
    }

    @Override
    public String getFullHelp() {
        return "No help yet.";
    }

    @Override
    public String getShortHelp() {
        return getFullHelp();
    }

    @Override
    public int getColour() {
        return 0;
    }
    
}
