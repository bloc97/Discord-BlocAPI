/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lolbot.cached.data;

import lolbot.cached.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import net.rithms.riot.api.endpoints.static_data.dto.ItemList;

/**
 *
 * @author bowen
 */
public class ItemsDataCache extends ObjectCache {
    
    private static final long LIFE = TimeUnit.DAYS.toMillis(1);
    
    public final ItemList itemList;
    
    public ItemsDataCache(ItemList itemList, Date currentDate) {
        super(currentDate, LIFE);
        this.itemList = itemList;
    }
    
}
