/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lolbot;

import helpers.Command;
import helpers.MapSort;
import helpers.TextFormatter;
import static helpers.TextFormatter.formatCapitalUnderscore;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.rithms.riot.api.ApiConfig;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.league.dto.LeaguePosition;
import net.rithms.riot.api.endpoints.match.dto.MatchList;
import net.rithms.riot.api.endpoints.match.dto.MatchReference;
import net.rithms.riot.api.endpoints.spectator.dto.CurrentGameInfo;
import net.rithms.riot.api.endpoints.spectator.dto.CurrentGameParticipant;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Platform;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.api.internal.json.objects.EmbedObject.EmbedFieldObject;
import sx.blah.discord.api.internal.json.objects.EmbedObject.FooterObject;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 *
 * @author bowen
 */
public class LoLBotApi {
    public static Platform platform = Platform.NA;
    
    private RiotApi rApi, rApiK;
    
    public LoLBotApi() {
        Path path = FileSystems.getDefault().getPath("rapi.key");
        String rApiKey = "";
        try {
            rApiKey = new String(Files.readAllLines(path).get(0));
        } catch (IOException ex) {
            Logger.getLogger(LoLBotApi.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Riot API Key: " + rApiKey);
        ApiConfig config = new ApiConfig();
        config.setKey(rApiKey);
        rApi = new RiotApi();
        rApiK = new RiotApi(config);
    }
    
    public void parseMessage(MessageReceivedEvent e, Command c) throws RiotApiException {
        String cs = c.get();
        String nameSearch = c.getNext();
        if (cs.isEmpty() || nameSearch.isEmpty()) {
            return;
        }
        Summoner summoner;
        try {
            summoner = rApiK.getSummonerByName(platform, nameSearch);
        } catch (RiotApiException ex) {
            String parsedName = nameSearch.substring(0, 1).toUpperCase() + nameSearch.substring(1).toLowerCase();
            e.getMessage().reply("Sorry, " + parsedName + " cound not be found.", null);
            return;
        }
        if (cs.equals("get")) {
            c.next();
            EmbedObject embed = new EmbedObject();
            //embed.title = summoner.getName() + " (" + summoner.getId() + ")";
            String profileUrl = "http://matchhistory.na.leagueoflegends.com/en/#match-history/NA1/" + summoner.getAccountId();
            String profilePicUrl = "http://ddragon.leagueoflegends.com/cdn/" + rApiK.getDataVersions(platform).get(0) + "/img/profileicon/" + summoner.getProfileIconId() + ".png";
            embed.author = new EmbedObject.AuthorObject(summoner.getName(), profileUrl, "", "");
            //embed.title = summoner.getId() + " *" + summoner.getAccountId() + "*";
            //embed.description = "Summoner Overview";
            embed.thumbnail = new EmbedObject.ThumbnailObject(profilePicUrl, "", 48, 48);
            embed.description = "*" + summoner.getId() + " | " + summoner.getAccountId() + "*";
            embed.footer = new FooterObject("Last Activity: " + new Date(summoner.getRevisionDate()).toString(), "", "");

            LinkedList<EmbedFieldObject> fieldList = new LinkedList<>();

            fieldList.add(new EmbedFieldObject("Level: ", "" + summoner.getSummonerLevel(), false));
            Set<LeaguePosition> lps = rApiK.getLeaguePositionsBySummonerId(platform, summoner.getId());
            fieldList.add(new EmbedFieldObject("Rank: ", getLeagues(lps), false));
            //fieldList.add(new EmbedFieldObject("ID/AID: ", "" + "*" + summoner.getId() + "|" + summoner.getAccountId() + "*", true));
            //fieldList.add(new EmbedFieldObject("Last Activity: ", new Date(summoner.getRevisionDate()).toString(), true));

            embed.fields = fieldList.toArray(new EmbedFieldObject[0]);
            e.getMessage().reply("", embed);
            
        } else if (cs.equals("info")) {
            c.next();
            EmbedObject embed = new EmbedObject();
            embed.title = summoner.getName();
            embed.description = "Summoner Info";
            embed.thumbnail = new EmbedObject.ThumbnailObject("http://ddragon.leagueoflegends.com/cdn/" + rApiK.getDataVersions(platform).get(0) + "/img/profileicon/" + summoner.getProfileIconId() + ".png", "", 48, 48);

            LinkedList<EmbedFieldObject> fieldList = new LinkedList<>();

            MatchList rml = rApiK.getRecentMatchListByAccountId(platform, summoner.getAccountId());
            List<MatchReference> rmr = rml.getMatches();
            List<MatchReference> mr;
            try {
                MatchList ml = rApiK.getMatchListByAccountId(platform, summoner.getAccountId());
                mr = ml.getMatches();
            } catch (RiotApiException ex) {
                System.out.println(ex);
                mr = new ArrayList<>();
            }

            fieldList.add(new EmbedFieldObject("Recent Champions: ", getMostUsedChampions(rmr, 4), true));
            fieldList.add(new EmbedFieldObject("(Ranked): ", getMostUsedChampions(mr, 4), true));
            fieldList.add(new EmbedFieldObject("Recent Roles: ", getPreferredRole(rmr, 2), true));
            fieldList.add(new EmbedFieldObject("(Ranked): ", getPreferredRole(mr, 2), true));
            fieldList.add(new EmbedFieldObject("Recent Lanes: ", getPreferredLane(rmr, 2), true));
            fieldList.add(new EmbedFieldObject("(Ranked): ", getPreferredLane(mr, 2), true));
            
            embed.fields = fieldList.toArray(new EmbedFieldObject[0]);
            e.getMessage().reply("", embed);
        } else if (cs.equals("check")) {
            c.next();
            try {
                CurrentGameInfo cgi = rApiK.getActiveGameBySummoner(platform, summoner.getId());
                EmbedObject embed = new EmbedObject();
                embed.title = formatCapitalUnderscore(cgi.getGameMode());
                embed.description = formatCapitalUnderscore(cgi.getGameType());
                
                LinkedList<EmbedFieldObject> fieldList = new LinkedList<>();
                
                fieldList.add(new EmbedFieldObject("Team 1: ", getCurrentParticipant(cgi, 100), true));
                fieldList.add(new EmbedFieldObject("Team 2: ", getCurrentParticipant(cgi, 200), true));
                
                embed.fields = fieldList.toArray(new EmbedFieldObject[0]);
                
                String timeInString = "";
                
                long runningTimeInSecs = cgi.getGameLength();
                
                long minutes = TimeUnit.SECONDS.toMinutes(runningTimeInSecs);
                
                if (minutes < 1) {
                    timeInString = (runningTimeInSecs > 1) ? runningTimeInSecs + " Seconds" : runningTimeInSecs + " Second";
                } else {
                    timeInString = (minutes > 1) ? minutes + " Minutes" : minutes + " Minute";
                }
                
                embed.footer = new FooterObject("Current Duration: " + timeInString, "", "");
                
                e.getMessage().reply(summoner.getName() + " is currently " + "**IN GAME**", embed);
            } catch (RiotApiException ex) {
                e.getMessage().reply(summoner.getName() + " is currently " + "*Not in Game*", null);
            }
        }
    }
    
    public String getCurrentParticipant(CurrentGameInfo cgi, int i) throws RiotApiException {
        String teamParticipants = "";
        for (CurrentGameParticipant cgp : cgi.getParticipants()) {
            if (cgp.getTeamId()== i) {
                teamParticipants += rApiK.getDataChampion(platform, cgp.getChampionId()).getName() + " (" + cgp.getSummonerName() + ")\n";
            }
        }
        if (teamParticipants.length() < 1) {
            return "None";
        }
        return teamParticipants;
    }
    
    public String getLeagues(Set<LeaguePosition> lps) {
        String leaguePositions = "";
        for (LeaguePosition lp : lps) {
            leaguePositions = leaguePositions + formatCapitalUnderscore(lp.getQueueType()) + ": " + lp.getTier() + " " + lp.getRank() + "\n";// + " (" + lp.getLeagueName() + ")";
        }
        if (leaguePositions.length() < 1) {
            return "None";
        }
        return leaguePositions;
    }
    public String getMostUsedChampions(List<MatchReference> mr, int n) throws RiotApiException {
        
        double mrLength = mr.size();
        Map<Long, Integer> championCount = new HashMap<>();

        for (MatchReference m : mr) {
            long champion = m.getChampion();
            if (championCount.containsKey(champion)) {
                championCount.put(champion, championCount.get(champion)+1);
            } else {
                championCount.put(champion, 1);
            }
        }
        championCount = MapSort.sortByValueDescending(championCount);
        Map.Entry<Long, Integer>[] championCountArray = championCount.entrySet().toArray(new Map.Entry[0]);

        String mostUsedChampions = "";
        int range = Math.min(championCount.size(), n);
        for (int i=0; i<range; i++) {
            int value = championCountArray[i].getValue();
            if (value < 2) {
                continue;
            }
            mostUsedChampions = mostUsedChampions + "(" + (int)(value*100/mrLength) + "%) " + rApiK.getDataChampion(platform, (championCountArray[i].getKey()).intValue()).getName() + "\n";
        }
        if (mostUsedChampions.length() < 1) {
            return "None";
        }
        
        return mostUsedChampions;
    }
    public String getPreferredLane(List<MatchReference> mr, int n) throws RiotApiException {
        
        double mrLength = mr.size();
        Map<String, Integer> laneCount = new HashMap<>();

        for (MatchReference m : mr) {
            String lane = m.getLane();
            if (laneCount.containsKey(lane)) {
                laneCount.put(lane, laneCount.get(lane)+1);
            } else {
                laneCount.put(lane, 1);
            }
        }
        laneCount = MapSort.sortByValueDescending(laneCount);
        Map.Entry<String, Integer>[] laneCountArray = laneCount.entrySet().toArray(new Map.Entry[0]);

        String mostPlayedLanes = "";
        int range = Math.min(laneCount.size(), n);
        for (int i=0; i<range; i++) {
            int value = laneCountArray[i].getValue();
            if (value < 2) {
                continue;
            }
            mostPlayedLanes = mostPlayedLanes + "("+ (int)(value*100/mrLength) + "%) " + formatCapitalUnderscore(laneCountArray[i].getKey()) + "\n";
        }
        if (mostPlayedLanes.length() < 1) {
            return "None";
        }
        
        return mostPlayedLanes;
    }
    public String getPreferredRole(List<MatchReference> mr, int n) throws RiotApiException {
        
        double mrLength = mr.size();
        Map<String, Integer> roleCount = new HashMap<>();

        for (MatchReference m : mr) {
            String role = m.getRole();
            if (roleCount.containsKey(role)) {
                roleCount.put(role, roleCount.get(role)+1);
            } else {
                roleCount.put(role, 1);
            }
        }
        roleCount = MapSort.sortByValueDescending(roleCount);
        Map.Entry<String, Integer>[] roleCountArray = roleCount.entrySet().toArray(new Map.Entry[0]);

        String mostPlayedRoles = "";
        int range = Math.min(roleCount.size(), n);
        for (int i=0; i<range; i++) {
            int value = roleCountArray[i].getValue();
            if (value < 2) {
                continue;
            }
            mostPlayedRoles = mostPlayedRoles + "(" + (int)(value*100/mrLength) + "%) " + formatCapitalUnderscore(roleCountArray[i].getKey()) + "\n";
        }
        if (mostPlayedRoles.length() < 1) {
            return "None";
        }
        
        return mostPlayedRoles;
    }
    
    public static void testRiotApi(RiotApi rApiK) throws RiotApiException {
        Summoner summoner = rApiK.getSummonerByName(platform, "Chloroplaste");
        
        System.out.println(summoner.getId());
        System.out.println(summoner.getName());
        System.out.println(summoner.getProfileIconId());
        System.out.println(summoner.getRevisionDate());
        System.out.println(summoner.getSummonerLevel());
    }
}
