/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lolbot.cached.data;

import lolbot.cached.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import net.rithms.riot.api.endpoints.static_data.dto.MasteryList;

/**
 *
 * @author bowen
 */
public class MasteriesDataCache extends ObjectCache {
    
    private static final long LIFE = TimeUnit.DAYS.toMillis(1);
    
    public final MasteryList masteryList;
    
    public MasteriesDataCache(MasteryList masteryList, Date currentDate) {
        super(currentDate, LIFE);
        this.masteryList = masteryList;
    }
    
}
