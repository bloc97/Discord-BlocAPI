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
import java.util.LinkedList;
import lolbot.LoLCommand;
import net.bloc97.riot.cache.CachedRiotApi;
import net.rithms.riot.api.endpoints.match.dto.Match;
import net.rithms.riot.api.endpoints.match.dto.MatchList;
import net.rithms.riot.api.endpoints.match.dto.MatchReference;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 *
 * @author bowen
 */
public class SummonerMatchHistory extends LoLCommand {
    
    public SummonerMatchHistory() {
        super(LoLCommand.LoLCommandType.SEARCHSUMMONERNAME, "summonermatchhistory", "summonerhistory", "smh");
    }
    @Override
    public boolean trigger(MessageReceivedEvent e, UserCommand c, CachedRiotApi api) {
        String nameSearch = c.get();
        Summoner summoner = api.Summoner.getSummonerByName(nameSearch);
        
        c.next();
        EmbedObject embed = new EmbedObject();
        String profileUrl = "http://matchhistory.na.leagueoflegends.com/en/#match-history/NA1/" + summoner.getAccountId();
        String profilePicUrl = "http://ddragon.leagueoflegends.com/cdn/" + api.StaticData.getDataLatestVersion() + "/img/profileicon/" + summoner.getProfileIconId() + ".png";
        embed.author = new EmbedObject.AuthorObject(summoner.getName(), profileUrl, "", "");
        embed.thumbnail = new EmbedObject.ThumbnailObject(profilePicUrl, "", 48, 48);
        embed.description = "Level " + summoner.getSummonerLevel();
        embed.footer = TextFormatter.getSummonerEmbedFooter(summoner.getId(), summoner.getAccountId(), summoner.getRevisionDate());

        LinkedList<EmbedObject.EmbedFieldObject> fieldList = new LinkedList<>();
        
        MatchList ml = api.Match.getRecentMatchListByAccountId(summoner.getAccountId());
        
        for (MatchReference mr : ml.getMatches()) {
            mr.getGameId()
        }
        
        fieldList.add(new EmbedObject.EmbedFieldObject("Top Champions: ", getTopChampions(api, summoner.getId(), 4), true));
        fieldList.add(new EmbedObject.EmbedFieldObject("Ranked Stats: ", getLeagues(api, summoner.getId(), 4), true));
        //fieldList.add(new EmbedObject.EmbedFieldObject("Level: ", "" + summoner.getSummonerLevel(), true));
        
        embed.fields = fieldList.toArray(new EmbedObject.EmbedFieldObject[0]);
        e.getMessage().getChannel().sendMessage("", embed);
        return true;
    }
    
}
