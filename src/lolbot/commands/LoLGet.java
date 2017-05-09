/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lolbot.commands;

import dbot.UserCommand;
import static helpers.TextFormatter.formatCapitalUnderscore;
import static helpers.TextFormatter.formatNounOutput;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import lolbot.LoLCommand;
import lolbot.RiotDatabase;
import net.rithms.riot.api.endpoints.champion_mastery.dto.ChampionMastery;
import net.rithms.riot.api.endpoints.league.dto.LeaguePosition;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 *
 * @author bowen
 */
public class LoLGet extends LoLCommand {
    public LoLGet() {
        super("get", LoLCommandType.SEARCHSUMMONERNAME);
    }
    @Override
    public boolean trigger(MessageReceivedEvent e, UserCommand c, RiotDatabase db) {
        String nameSearch = c.get();
        Summoner summoner = db.getSummonerByName(nameSearch);
        if (summoner == null) {
            e.getMessage().reply("Sorry, " + formatNounOutput(nameSearch) + " cound not be found.", null);
            return false;
        }
        c.next();
        EmbedObject embed = new EmbedObject();
        String profileUrl = "http://matchhistory.na.leagueoflegends.com/en/#match-history/NA1/" + summoner.getAccountId();
        String profilePicUrl = "http://ddragon.leagueoflegends.com/cdn/" + db.getDataLatestVersion() + "/img/profileicon/" + summoner.getProfileIconId() + ".png";
        embed.author = new EmbedObject.AuthorObject(summoner.getName(), profileUrl, "", "");
        embed.thumbnail = new EmbedObject.ThumbnailObject(profilePicUrl, "", 48, 48);
        embed.description = "*" + summoner.getId() + " | " + summoner.getAccountId() + "*";
        embed.footer = new EmbedObject.FooterObject("Last Activity: " + new Date(summoner.getRevisionDate()).toString(), "", "");

        LinkedList<EmbedObject.EmbedFieldObject> fieldList = new LinkedList<>();

        fieldList.add(new EmbedObject.EmbedFieldObject("Level: ", "" + summoner.getSummonerLevel(), true));
        
        fieldList.add(new EmbedObject.EmbedFieldObject("Ranked Stats: ", getLeagues(db, summoner.getId(), 3), true));
        fieldList.add(new EmbedObject.EmbedFieldObject("Top Champions: ", getTopChampions(db, summoner.getId(), 3), true));
        
        embed.fields = fieldList.toArray(new EmbedObject.EmbedFieldObject[0]);
        e.getMessage().getChannel().sendMessage("", embed);
        return true;
    }
    
    public static String getTopChampions(RiotDatabase db, long id, int n) {
        List<ChampionMastery> cms = db.getChampionMasteries(id);
        if (cms == null || cms.size() < 1) {
            return "None";
        }
        String topChampions = "";
        int i = 0;
        for (ChampionMastery cm : cms) {
            topChampions = topChampions + "**[" + cm.getChampionLevel() + "]** " + db.getDataChampion(cm.getChampionId()).getName() + ": " + cm.getChampionPoints() + "\n";
            i++;
            if (i >= n) {
                break;
            }
        }
        return topChampions;
    }
    public static String getLeagues(RiotDatabase db, long id, int n) {
        Set<LeaguePosition> lps = db.getLeaguePositions(id);
        if (lps == null || lps.size() < 1) {
            return "None";
        }
        String leaguePositions = "";
        int i = 0;
        for (LeaguePosition lp : lps) {
            leaguePositions = leaguePositions + formatCapitalUnderscore(lp.getQueueType()) + ": **" + lp.getTier() + " " + lp.getRank() + "**\n";// + " (" + lp.getLeagueName() + ")";
            i++;
            if (i >= n) {
                break;
            }
        }
        return leaguePositions;
    }
}
