/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lolbot.cached;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import net.rithms.riot.api.endpoints.league.dto.LeaguePosition;

/**
 *
 * @author bowen
 */
public class LeaguePositionsCache extends ObjectCache {
    
    private static final long LIFE = TimeUnit.HOURS.toMillis(2);
    
    public final Set<LeaguePosition> leaguePositions;
    
    public LeaguePositionsCache(Set<LeaguePosition> leaguePositions, Date currentDate) {
        super(currentDate, LIFE);
        this.leaguePositions = leaguePositions;
    }
}
