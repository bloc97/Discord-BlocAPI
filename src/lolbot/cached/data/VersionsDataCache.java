/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lolbot.cached.data;

import lolbot.cached.*;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author bowen
 */
public class VersionsDataCache extends ObjectCache {
    
    private static final long LIFE = TimeUnit.DAYS.toMillis(1);
    
    public final List<String> versionsList;
    
    public VersionsDataCache(List<String> versionsList, Date currentDate) {
        super(currentDate, LIFE);
        this.versionsList = versionsList;
    }
    
}
