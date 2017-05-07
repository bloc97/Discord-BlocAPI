/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lolbot;

import helpers.Command;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.constant.Region;
import net.rithms.riot.dto.Summoner.Summoner;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 *
 * @author bowen
 */
public class LoLBotApi {
    public static Region region = Region.NA;
    
    private RiotApi rApi, rApiK;
    
    public LoLBotApi(String rApiKey) {
        rApi = new RiotApi(region);
        rApiK = new RiotApi(rApiKey, region);
        /*
        try {
            testRiotApi(rApiK);
        } catch (RiotApiException e) {
            //e.printStackTrace();
            System.out.println(e);
        }*/
    }
    
    public void parseMessage(MessageReceivedEvent e, Command c) throws RiotApiException {
        switch (c.get()) {
            case "get":
                c.next();
                Summoner summoner = rApiK.getSummonerByName(c.get());
                e.getMessage().reply("Summoner: " + summoner.getName() + " ID: " + summoner.getId() + " Level: " + summoner.getSummonerLevel());
                break;
            default:
        }
    }
    
    public static void testRiotApi(RiotApi rApiK) throws RiotApiException {
        Summoner summoner = rApiK.getSummonerByName("Chloroplaste");
        
        System.out.println(summoner.getId());
        System.out.println(summoner.getName());
        System.out.println(summoner.getProfileIconId());
        System.out.println(summoner.getRevisionDate());
        System.out.println(summoner.getSummonerLevel());
    }
}
