/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lolbot;

import helpers.Levenshtein;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import lolbot.cached.ChampionMasteriesCache;
import lolbot.cached.LeaguePositionsCache;
import lolbot.cached.SummonerCache;
import lolbot.cached.data.ChampionsDataCache;
import lolbot.cached.data.ItemsDataCache;
import lolbot.cached.data.MapsDataCache;
import lolbot.cached.data.MasteriesDataCache;
import lolbot.cached.data.ProfileIconsDataCache;
import lolbot.cached.data.RealmsDataCache;
import lolbot.cached.data.RunesDataCache;
import lolbot.cached.data.SummonerSpellsDataCache;
import lolbot.cached.data.VersionsDataCache;
import net.rithms.riot.api.ApiConfig;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.champion_mastery.dto.ChampionMastery;
import net.rithms.riot.api.endpoints.league.dto.LeaguePosition;
import net.rithms.riot.api.endpoints.static_data.constant.ChampData;
import net.rithms.riot.api.endpoints.static_data.constant.ChampListData;
import net.rithms.riot.api.endpoints.static_data.constant.Locale;
import net.rithms.riot.api.endpoints.static_data.dto.Champion;
import net.rithms.riot.api.endpoints.static_data.dto.ChampionList;
import net.rithms.riot.api.endpoints.static_data.dto.ItemList;
import net.rithms.riot.api.endpoints.static_data.dto.MapData;
import net.rithms.riot.api.endpoints.static_data.dto.MasteryList;
import net.rithms.riot.api.endpoints.static_data.dto.ProfileIconData;
import net.rithms.riot.api.endpoints.static_data.dto.Realm;
import net.rithms.riot.api.endpoints.static_data.dto.RuneList;
import net.rithms.riot.api.endpoints.static_data.dto.SummonerSpellList;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Platform;

/**
 *
 * @author bowen
 */
public class RiotDatabase {
    
    private final RiotApi rApi;
    private final Platform platform;
    
    private HashMap<Long, SummonerCache> summoners = new HashMap<>(); //Maps summonerId to Summoner
    private HashMap<Long, LeaguePositionsCache> leaguePositions = new HashMap<>(); //Maps summonerId to LeaguePositions
    private HashMap<Long, ChampionMasteriesCache> championMasteries = new HashMap<>(); //Maps summonerId to LeaguePositions
    
    
    private ChampionsDataCache championsData;
    private ItemsDataCache itemsData;
    private MapsDataCache mapsData;
    private MasteriesDataCache masteriesData;
    private ProfileIconsDataCache profileIconsData;
    private RealmsDataCache realmsData;
    private RunesDataCache runesData;
    private SummonerSpellsDataCache summonerSpellsData;
    private VersionsDataCache versionsData;
    
    public RiotDatabase(Platform platform) {
        Path path = FileSystems.getDefault().getPath("rapi.key");
        String rApiKey = "";
        try {
            rApiKey = Files.readAllLines(path).get(0);
        } catch (IOException ex) {
            Logger.getLogger(LoLBotApi.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalStateException("No valid API key!");
        }
        //System.out.println("Riot API Key: " + rApiKey);
        
        ApiConfig config = new ApiConfig();
        config.setKey(rApiKey);
        config.setRespectRateLimit(true);
        config.setRequestTimeout(4000);
        
        this.rApi = new RiotApi(config);
        this.platform = platform;
    }
    
    public boolean summonerNameExists(String name) {
        Summoner summoner = getSummonerByName(name);
        return summoner != null && summoner.getName() instanceof String;
    }
    
    private Summoner updateSummonerByName(String name, Date now) {
        Summoner summoner;
        try {
            summoner = rApi.getSummonerByName(platform, name);
            summoners.put(summoner.getId(), new SummonerCache(summoner, now));
        } catch (RiotApiException ex) {
            System.out.println(ex);
            summoner = null;
        }
        return summoner;
    }
    private Summoner updateSummonerByAccountId(long accountId, Date now) {
        Summoner summoner;
        try {
            summoner = rApi.getSummonerByAccount(platform, accountId);
            summoners.put(summoner.getId(), new SummonerCache(summoner, now));
        } catch (RiotApiException ex) {
            System.out.println(ex);
            summoner = null;
        }
        return summoner;
    }
    private Summoner updateSummoner(long id, Date now) {
        Summoner summoner;
        try {
            summoner = rApi.getSummoner(platform, id);
            summoners.put(summoner.getId(), new SummonerCache(summoner, now));
        } catch (RiotApiException ex) {
            System.out.println(ex);
            summoners.remove(id);
            summoner = null;
        }
        return summoner;
    }
    
    private SummonerCache fetchSummonerByName(String name) {
        name = name.toLowerCase();
        for (HashMap.Entry<Long, SummonerCache> cacheEntry : summoners.entrySet()) { //Try finding summoner in cache
            SummonerCache cache = cacheEntry.getValue();
            if (cache.summoner.getName().toLowerCase().equals(name)) { //Lowercase since unique names are not case sensitive
                return cache;
            }
        }
        return null;
    }
    private SummonerCache fetchSummonerByAccountId(long accountId) {
        for (HashMap.Entry<Long, SummonerCache> cacheEntry : summoners.entrySet()) { //Try finding summoner in cache
            SummonerCache cache = cacheEntry.getValue();
            if (cache.summoner.getAccountId()== accountId) {
                return cache;
            }
        }
        return null;
    }
    private SummonerCache fetchSummoner(long id) {
        return summoners.get(id);
    }
    
    public Summoner getSummonerByName(String name) {
        Date now = new Date();
        
        SummonerCache cache = fetchSummonerByName(name);
        if (cache == null) { //If didn't find summoner in cache
            //System.out.println("Fetching Initial Summoner");
            return updateSummonerByName(name, now);
        }
        if (cache.liveUntil.after(now)) { //Found summoner, TTL is not yet reached
            //System.out.println("Getting Cached Summoner");
            return cache.summoner;
        } else { //If found summoner in cache, and TTL was reached, refresh the entry.
            //System.out.println("Updating Summoner");
            return getSummonerByName(updateSummonerByAccountId(cache.summoner.getAccountId(), now).getName()); //Recursive function, since name change might break the cache and search.
        }
    }
    public Summoner getSummoner(long id) {
        Date now = new Date();
        
        SummonerCache cache = fetchSummoner(id);
        if (cache == null) { //If didn't find summoner in cache
            return updateSummoner(id, now);
        }
        if (cache.liveUntil.after(now)) { //Found summoner, TTL is not yet reached
            return cache.summoner;
        } else { //If found summoner in cache, and TTL was reached, refresh the entry.
            return getSummoner(updateSummonerByAccountId(cache.summoner.getAccountId(), now).getId()); //Recursive function, since id change might break the cache and search.
        }
    }
    public Summoner getSummonerByAccountId(long accountId) {
        Date now = new Date();
        
        SummonerCache cache = fetchSummonerByAccountId(accountId);
        if (cache == null) { //If didn't find summoner in cache
            return updateSummonerByAccountId(accountId, now);
        }
        if (cache.isValid(now)) { //Found summoner, TTL is not yet reached
            return cache.summoner;
        } else { //If found summoner in cache, and TTL was reached, refresh the entry.
            return updateSummonerByAccountId(accountId, now);
        }
    }
    private Set<LeaguePosition> updateLeaguePositions(long id, Date now) {
        try {
            Set<LeaguePosition> leaguePos = rApi.getLeaguePositionsBySummonerId(platform, id);
            leaguePositions.put(id, new LeaguePositionsCache(leaguePos, now));
            return leaguePos;
        } catch (RiotApiException ex) {
            System.out.println(ex);
            return null;
        }
    }
    public Set<LeaguePosition> getLeaguePositions(long id) {
        Date now = new Date();
        
        LeaguePositionsCache cache = leaguePositions.get(id);
        if (cache == null) {
            return updateLeaguePositions(id, now);
        }
        if (cache.isValid(now)) {
            return cache.leaguePositions;
        } else {
            return updateLeaguePositions(id, now);
        }
    }
    
    private List<ChampionMastery> updateChampionMasteries(long id, Date now) {
        try {
            List<ChampionMastery> championMas = rApi.getChampionMasteriesBySummoner(platform, id);
            championMasteries.put(id, new ChampionMasteriesCache(championMas, now));
            return championMas;
        } catch (RiotApiException ex) {
            System.out.println(ex);
            return null;
        }
    }
    public List<ChampionMastery> getChampionMasteries(long id) {
        Date now = new Date();
        
        ChampionMasteriesCache cache = championMasteries.get(id);
        if (cache == null) {
            return updateChampionMasteries(id, now);
        }
        if (cache.isValid(now)) {
            return cache.championMasteries;
        } else {
            return updateChampionMasteries(id, now);
        }
    }
    
    public Champion getDataChampion(int id) {
        ChampionList cl = getDataChampionList();
        for (Map.Entry<String, Champion> championEntry : cl.getData().entrySet()) {
            if (championEntry.getValue().getId() == id) {
                return championEntry.getValue();
            }
        }
        return null;
    }
    public Champion getDataChampion(String name) {
        name = name.toLowerCase();
        ChampionList cl = getDataChampionList();
        for (Map.Entry<String, Champion> championEntry : cl.getData().entrySet()) {
            if (championEntry.getValue().getName().toLowerCase().equals(name)) {
                return championEntry.getValue();
            }
        }
        return null;
    }
    public Champion getDataChampionClosest(String name) {
        name = name.toLowerCase();
        Champion champion = null;
        int distanceScore = Integer.MAX_VALUE;
        ChampionList cl = getDataChampionList();
        for (Map.Entry<String, Champion> championEntry : cl.getData().entrySet()) {
            String championName = championEntry.getValue().getKey().toLowerCase();
            int newDistanceScore = Levenshtein.substringDistance(championName, name);
            if (newDistanceScore < distanceScore) {
                distanceScore = newDistanceScore;
                champion = championEntry.getValue();
            }
        }
        return champion;
    }
    
    
    public Champion getDataChampionFull(int id) { //Uncached
        try {
            return rApi.getDataChampion(platform, id, null, null, ChampData.ALL);
        } catch (RiotApiException ex) {
            System.out.println(ex);
            return null;
        }
    }
    public ChampionList getDataChampionList() {
        Date now = new Date();
        
        if (championsData == null) {
            return updateDataChampionList(now);
        }
        if (championsData.isValid(now)) {
            return championsData.championList;
        } else {
            return updateDataChampionList(now);
        }
    }
    
    private ChampionList updateDataChampionList(Date now) {
        try {
            ChampionList championList = rApi.getDataChampionList(platform);
            championsData = new ChampionsDataCache(championList, now);
            return championList;
        } catch (RiotApiException ex) {
            System.out.println(ex);
            return null;
        }
    }
    private ItemList updateDataItemList(Date now) {
        try {
            ItemList itemList = rApi.getDataItemList(platform);
            itemsData = new ItemsDataCache(itemList, now);
            return itemList;
        } catch (RiotApiException ex) {
            System.out.println(ex);
            return null;
        }
    }
    private MapData updateDataMaps(Date now) {
        try {
            MapData mapData = rApi.getDataMaps(platform);
            mapsData = new MapsDataCache(mapData, now);
            return mapData;
        } catch (RiotApiException ex) {
            System.out.println(ex);
            return null;
        }
    }
    private MasteryList updateDataMasteryList(Date now) {
        try {
            MasteryList masteryList = rApi.getDataMasteryList(platform);
            masteriesData = new MasteriesDataCache(masteryList, now);
            return masteryList;
        } catch (RiotApiException ex) {
            System.out.println(ex);
            return null;
        }
    }
    private ProfileIconData updateDataProfileIcons(Date now) {
        try {
            ProfileIconData profileIcons = rApi.getDataProfileIcons(platform);
            profileIconsData = new ProfileIconsDataCache(profileIcons, now);
            return profileIcons;
        } catch (RiotApiException ex) {
            System.out.println(ex);
            return null;
        }
    }
    private Realm updateDataRealms(Date now) {
        try {
            Realm realm = rApi.getDataRealm(platform);
            realmsData = new RealmsDataCache(realm, now);
            return realm;
        } catch (RiotApiException ex) {
            System.out.println(ex);
            return null;
        }
    }
    private RuneList updateDataRuneList(Date now) {
        try {
            RuneList runeList = rApi.getDataRuneList(platform);
            runesData = new RunesDataCache(runeList, now);
            return runeList;
        } catch (RiotApiException ex) {
            System.out.println(ex);
            return null;
        }
    }
    private SummonerSpellList updateDataSummonerSpellList(Date now) {
        try {
            SummonerSpellList summonerSpellList = rApi.getDataSummonerSpellList(platform);
            summonerSpellsData = new SummonerSpellsDataCache(summonerSpellList, now);
            return summonerSpellList;
        } catch (RiotApiException ex) {
            System.out.println(ex);
            return null;
        }
    }
    private List<String> updateDataVersions(Date now) {
        try {
            List<String> versions = rApi.getDataVersions(platform);
            versionsData = new VersionsDataCache(versions, now);
            return versions;
        } catch (RiotApiException ex) {
            System.out.println(ex);
            return null;
        }
    }
    
    public ItemList getDataItemList() {
        Date now = new Date();
        
        if (itemsData == null) {
            return updateDataItemList(now);
        }
        if (itemsData.isValid(now)) {
            return itemsData.itemList;
        } else {
            return updateDataItemList(now);
        }
    }
    
    public MapData getDataMaps() {
        Date now = new Date();
        
        if (mapsData == null) {
            return updateDataMaps(now);
        }
        if (mapsData.isValid(now)) {
            return mapsData.mapData;
        } else {
            return updateDataMaps(now);
        }
    }
    public MasteryList getDataMasteryList() {
        Date now = new Date();
        
        if (masteriesData == null) {
            return updateDataMasteryList(now);
        }
        if (masteriesData.isValid(now)) {
            return masteriesData.masteryList;
        } else {
            return updateDataMasteryList(now);
        }
    }
    
    public ProfileIconData getDataProfileIcons() {
        Date now = new Date();
        
        if (profileIconsData == null) {
            return updateDataProfileIcons(now);
        }
        if (profileIconsData.isValid(now)) {
            return profileIconsData.profileIconData;
        } else {
            return updateDataProfileIcons(now);
        }
    }
    public Realm getDataRealms() {
        Date now = new Date();
        
        if (realmsData == null) {
            return updateDataRealms(now);
        }
        if (realmsData.isValid(now)) {
            return realmsData.realm;
        } else {
            return updateDataRealms(now);
        }
    }
    public RuneList getDataRuneList() {
        Date now = new Date();
        
        if (runesData == null) {
            return updateDataRuneList(now);
        }
        if (runesData.isValid(now)) {
            return runesData.runeList;
        } else {
            return updateDataRuneList(now);
        }
    }
    public SummonerSpellList getDataSummonerSpellList() {
        Date now = new Date();
        
        if (summonerSpellsData == null) {
            return updateDataSummonerSpellList(now);
        }
        if (summonerSpellsData.isValid(now)) {
            return summonerSpellsData.summonerSpellList;
        } else {
            return updateDataSummonerSpellList(now);
        }
    }
    public List<String> getDataVersions() {
        Date now = new Date();
        
        if (versionsData == null) {
            return updateDataVersions(now);
        }
        if (versionsData.isValid(now)) {
            return versionsData.versionsList;
        } else {
            return updateDataVersions(now);
        }
    }
    public String getDataLatestVersion() {
        String version = getDataVersions().get(0);
        if (version == null) {
            return "0";
        }
        return version;
    }
    
    private void test() {
        try {
            rApi.getChampionMasteriesBySummoner(platform, 0);
        } catch (RiotApiException ex) {
            Logger.getLogger(RiotDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
