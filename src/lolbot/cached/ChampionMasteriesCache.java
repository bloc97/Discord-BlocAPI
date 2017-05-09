/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lolbot.cached;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import net.rithms.riot.api.endpoints.champion_mastery.dto.ChampionMastery;
import net.rithms.riot.api.endpoints.league.dto.LeaguePosition;

/**
 *
 * @author bowen
 */
public class ChampionMasteriesCache extends ObjectCache {
    
    private static final long LIFE = TimeUnit.HOURS.toMillis(2);
    
    public final List<ChampionMastery> championMasteries;
    
    public ChampionMasteriesCache(List<ChampionMastery> championMasteries, Date currentDate) {
        super(currentDate, LIFE);
        this.championMasteries = championMasteries;
    }
}
