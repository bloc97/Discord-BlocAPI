/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lolbot.cached.data;

import lolbot.cached.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import net.rithms.riot.api.endpoints.static_data.dto.Realm;

/**
 *
 * @author bowen
 */
public class RealmsDataCache extends ObjectCache {
    
    private static final long LIFE = TimeUnit.DAYS.toMillis(1);
    
    public final Realm realm;
    
    public RealmsDataCache(Realm realm, Date currentDate) {
        super(currentDate, LIFE);
        this.realm = realm;
    }
    
}