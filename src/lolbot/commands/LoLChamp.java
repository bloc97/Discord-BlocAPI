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
import java.util.Map;
import java.util.Set;
import lolbot.LoLCommand;
import lolbot.RiotDatabase;
import net.rithms.riot.api.endpoints.champion_mastery.dto.ChampionMastery;
import net.rithms.riot.api.endpoints.league.dto.LeaguePosition;
import net.rithms.riot.api.endpoints.static_data.dto.Champion;
import net.rithms.riot.api.endpoints.static_data.dto.ChampionList;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 *
 * @author bowen
 */
public class LoLChamp extends LoLCommand {
    public LoLChamp() {
        super("champ", LoLCommandType.NULL);
    }
    @Override
    public boolean trigger(MessageReceivedEvent e, UserCommand c, RiotDatabase db) {
        String nameSearch = c.get();
        Champion champion = db.getDataChampion(nameSearch);
        
        if (champion == null) {
            champion = db.getDataChampionClosest(nameSearch);
            e.getMessage().reply("Could not find champion, did you mean: " + champion.getName() + "?");
            return false;
        }
        
        /*
        ChampionList championList = db.getDataChampions();
        String finalString = "";
        for (Map.Entry<String, Champion> championEntry : championList.getData().entrySet()) {
            finalString += championEntry.getValue().getName() + " " + championEntry.getKey() + " ";
        }*/
        e.getMessage().reply(champion.getName() + " " + champion.getTitle());
        return true;
    }
    
}
