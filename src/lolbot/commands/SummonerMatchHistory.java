/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lolbot.commands;

import dbot.UserCommand;
import helpers.TextFormatter;
import static helpers.TextFormatter.formatNounOutput;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import lolbot.LoLCommand;
import lolbot.helpers.QueueConstants;
import net.bloc97.riot.cache.CachedRiotApi;
import net.rithms.riot.api.endpoints.match.dto.Match;
import net.rithms.riot.api.endpoints.match.dto.MatchList;
import net.rithms.riot.api.endpoints.match.dto.MatchReference;
import net.rithms.riot.api.endpoints.match.dto.Participant;
import net.rithms.riot.api.endpoints.match.dto.ParticipantStats;
import net.rithms.riot.api.endpoints.match.dto.TeamStats;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 *
 * @author bowen
 */
public class SummonerMatchHistory extends LoLCommand {
    
    private QueueConstants qConstants = new QueueConstants();
    
    public SummonerMatchHistory() {
        super(LoLCommand.LoLCommandType.SEARCHSUMMONERNAME, "summonermatchhistory", "matchhistory", "history", "smh", "mh");
    }
    @Override
    public boolean trigger(MessageReceivedEvent e, UserCommand c, CachedRiotApi api) {
        String nameSearch = c.get();
        Summoner summoner = api.Summoner.getSummonerByName(nameSearch);
        
        c.next();
        int page = 1;
        try {
            page = Integer.parseInt(c.get());
        } catch (NumberFormatException ex) {
        }
        
        EmbedObject embed = new EmbedObject();
        embed.color = 6732543;
        String profileUrl = "http://matchhistory.na.leagueoflegends.com/en/#match-history/NA1/" + summoner.getAccountId();
        String profilePicUrl = "http://ddragon.leagueoflegends.com/cdn/" + api.StaticData.getDataLatestVersion() + "/img/profileicon/" + summoner.getProfileIconId() + ".png";
        embed.author = new EmbedObject.AuthorObject(summoner.getName(), profileUrl, profilePicUrl, null);
        embed.description = "Level " + summoner.getSummonerLevel();
        embed.footer = new EmbedObject.FooterObject("Page " + page + "/4", null, null);
        
        LinkedList<EmbedObject.EmbedFieldObject> fieldList = new LinkedList<>();
        
        MatchList ml = api.Match.getRecentMatchListByAccountId(summoner.getAccountId());
        
        //fieldList.add(new EmbedObject.EmbedFieldObject("Last " + n + " Games", getMatchesTitle(ml, n), true));
        //fieldList.add(new EmbedObject.EmbedFieldObject("\u3000\uFEFF", getMatchesChampions(ml, api, n), true));
        //fieldList.add(new EmbedObject.EmbedFieldObject("\uFEFF", getMatchesStats(ml, api, summoner.getId(), n), true));
        
        fieldList.add(new EmbedObject.EmbedFieldObject("\uFEFF", getMatchAll(ml, api, summoner.getId(), page), true));
        
        embed.fields = fieldList.toArray(new EmbedObject.EmbedFieldObject[0]);
        e.getMessage().getChannel().sendMessage("", embed);
        return true;
    }
    
    
    private String getMatchAll(MatchList matches, CachedRiotApi api, long summonerId, int page) {
        if (page > 4) {
            page = 4;
        } else if (page < 1) {
            page = 1;
        }
        int begin = (page-1)*5;
        int end = begin+5;
        
        String finalString = "";
        int i = 0;
        for (MatchReference match : matches.getMatches()) {
            if (i < begin) {
                i++;
                continue;
            }
            if (i >= end) break;
            String description = qConstants.descDictionary.get(match.getQueue());
            if (!description.isEmpty()) {
                description = "*(" + description + ")*";
            }
            finalString += "**" + qConstants.titleDictionary.get(match.getQueue()) + "** " + description + "\n";
            
            String role = TextFormatter.formatCapitalUnderscore(match.getRole());
            String lane = TextFormatter.formatCapitalUnderscore(match.getLane());
            if (role.equals("None")) {
                role = "";
            } else {
                role += " ";
            }
            finalString += api.StaticData.getDataChampionPartial(match.getChampion()).getName() + " (" + role + lane + ")\n";
            
            Match fullMatch = api.Match.getMatch(match.getGameId());
            Participant participant = api.Match.tryGetParticipant(match, fullMatch, summonerId, false, api);
            
            if (participant != null) {
                ParticipantStats stats = participant.getStats();
                finalString += (stats.isWin() ? "**Victory**" : "*Defeat*") + "\n" + stats.getKills() + "/"  + stats.getDeaths() + "/" + stats.getAssists() + "\n\n";
            } else {
                finalString += "*Hidden*" +  "\n\n";
            }
            
            i++;
        }
        return finalString;
        
    }
    
    
    
}