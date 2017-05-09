/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lolbot;

import helpers.Levenshtein;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import lolbot.cached.data.ChampionsDataCache;
import lolbot.cached.data.ItemsDataCache;
import lolbot.cached.data.MapsDataCache;
import lolbot.cached.data.MasteriesDataCache;
import lolbot.cached.data.ProfileIconsDataCache;
import lolbot.cached.data.RealmsDataCache;
import lolbot.cached.data.RunesDataCache;
import lolbot.cached.data.SummonerSpellsDataCache;
import lolbot.cached.data.VersionsDataCache;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.static_data.constant.ChampData;
import net.rithms.riot.api.endpoints.static_data.dto.Champion;
import net.rithms.riot.api.endpoints.static_data.dto.ChampionList;
import net.rithms.riot.api.endpoints.static_data.dto.Item;
import net.rithms.riot.api.endpoints.static_data.dto.ItemList;
import net.rithms.riot.api.endpoints.static_data.dto.MapData;
import net.rithms.riot.api.endpoints.static_data.dto.MasteryList;
import net.rithms.riot.api.endpoints.static_data.dto.ProfileIconData;
import net.rithms.riot.api.endpoints.static_data.dto.Realm;
import net.rithms.riot.api.endpoints.static_data.dto.RuneList;
import net.rithms.riot.api.endpoints.static_data.dto.SummonerSpellList;
import net.rithms.riot.constant.Platform;

/**
 *
 * @author bowen
 */
public class StaticDataDatabase {
    public final int version = 3;
    
    private final RiotApi rApi;
    private final Platform platform;
    
    private ChampionsDataCache championsData;
    private ItemsDataCache itemsData;
    private MapsDataCache mapsData;
    private MasteriesDataCache masteriesData;
    private ProfileIconsDataCache profileIconsData;
    private RealmsDataCache realmsData;
    private RunesDataCache runesData;
    private SummonerSpellsDataCache summonerSpellsData;
    private VersionsDataCache versionsData;
    
    public StaticDataDatabase(Platform platform, RiotApi rApi) {
        this.rApi = rApi;
        this.platform = platform;
    }
    
    //List cache updaters
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
    
    //List getters
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
    
    //Searchers (searches in cache), usually returns partial data
    public Champion getDataChampion(int id) {
        ChampionList list = getDataChampionList();
        for (Map.Entry<String, Champion> entry : list.getData().entrySet()) {
            if (entry.getValue().getId() == id) {
                return entry.getValue();
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
    public Item getDataItem(int id) {
        ItemList list = getDataItemList();
        for (Map.Entry<String, Item> entry : list.getData().entrySet()) {
            if (entry.getValue().getId() == id) {
                return entry.getValue();
            }
        }
        return null;
    }
    
    public String getDataLatestVersion() {
        String version = getDataVersions().get(0);
        if (version == null) {
            return "0";
        }
        return version;
    }
    
    //Uncached complete data
    public Champion getDataChampionFull(int id) { //Uncached
        try {
            return rApi.getDataChampion(platform, id, null, null, ChampData.ALL);
        } catch (RiotApiException ex) {
            System.out.println(ex);
            return null;
        }
    }
    
    
    
}
