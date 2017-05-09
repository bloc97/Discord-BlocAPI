/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lolbot.cached;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import lolbot.RiotDatabase;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;

/**
 *
 * @author bowen
 */
public class SummonerCache extends ObjectCache {
    
    private static final long LIFE = TimeUnit.MINUTES.toMillis(10);
    
    public final Summoner summoner;
    
    public SummonerCache(Summoner summoner, Date currentDate) {
        super(currentDate, LIFE);
        this.summoner = summoner;
    }
    
}
