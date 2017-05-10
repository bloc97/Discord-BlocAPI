/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lolbot.commands;

import dbot.UserCommand;
import lolbot.LoLCommand;
import net.bloc97.riot.cache.CachedRiotApi;
import net.rithms.riot.api.endpoints.static_data.dto.Champion;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 *
 * @author bowen
 */
public class LoLChamp extends LoLCommand {
    public LoLChamp() {
        super(LoLCommandType.NULL, "champ");
    }
    @Override
    public boolean trigger(MessageReceivedEvent e, UserCommand c, CachedRiotApi api) {
        String nameSearch = c.get();
        Champion champion = api.StaticData.searchDataChampion(nameSearch);
        
        if (champion == null) {
            champion = api.StaticData.searchDataChampionClosest(nameSearch);
            e.getMessage().reply("Could not find champion, did you mean: " + champion.getName() + "?");
            return false;
        }
        
        /*
        ChampionList championList = db.getDataChampions();
        String finalString = "";
        for (Map.Entry<String, Champion> championEntry : championList.getData().entrySet()) {
            finalString += championEntry.getValue().getName() + " " + championEntry.getKey() + " ";
        }*/
        e.getMessage().reply(champion.getName() + " " + champion.getTitle() + " " + champion.getLore());
        return true;
    }
    
}
