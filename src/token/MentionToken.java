/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package token;

import java.util.List;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.IShard;
import sx.blah.discord.api.internal.ShardImpl;
import sx.blah.discord.handle.impl.obj.User;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IDiscordObject;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.StatusType;

/**
 *
 * @author bowen
 */
public class MentionToken extends Token<IDiscordObject> {
    
    private final IDiscordClient client;
    
    private IUser userMention;
    private IChannel channelMention;
    private IRole roleMention;
    
    private boolean isMentionEveryone;
    private boolean isMentionHere;
    
    private IChannel spokenChannel;
    
    public MentionToken(IDiscordClient botClient, IUser user) {
        super("<@" + user.getLongID() + ">");
        userMention = user;
        isMentionEveryone = false;
        isMentionHere = false;
        this.client = botClient;
    }
    
    public MentionToken(IDiscordClient botClient, IMessage tokenMessage, String userToken) {
        super(userToken);
        this.client = botClient;
        spokenChannel = tokenMessage.getChannel();
        if (userToken.startsWith("<@") && userToken.endsWith(">")) {
            long id = Long.parseLong(userToken.substring(2, userToken.length()-1));
            userMention = client.fetchUser(id);
        } else if (userToken.startsWith("<@&") && userToken.endsWith(">")) {
            long id = Long.parseLong(userToken.substring(3, userToken.length()-1));
            roleMention = client.getRoleByID(id);
        } else if (userToken.startsWith("<#") && userToken.endsWith(">")) {
            long id = Long.parseLong(userToken.substring(2, userToken.length()-1));
            channelMention = client.getChannelByID(id);
        } else if (userToken.equals("@everyone")) {
            isMentionEveryone = true;
        } else if (userToken.equals("@here")) {
            isMentionHere = true;
        } else {
            throw new IllegalArgumentException("String cannot be parsed into a Mention Token.");
        }
    }
    
    public boolean isBotMentionned() {
        return isMentionned(client.getOurUser());
    }
    
    public boolean isMentionned(IUser user) {
        if (isMentionEveryone) {
            if (spokenChannel.getUsersHere().contains(user)) { //Gets all users, even those offline
                return true;
            }
        } else if (isMentionHere) {
            if (spokenChannel.getUsersHere().contains(user)) {
                if (user.getPresence().getStatus() == StatusType.ONLINE) {
                    return true;
                }
            }
        } else if (userMention != null) {
            if (user.equals(userMention)) {
                return true;
            }
        } else if (roleMention != null) {
            if (spokenChannel.getGuild().getRolesForUser(user).contains(roleMention)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isMentionned(IChannel channel) {
        if (channelMention != null) {
            if (channelMention.equals(channel)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isMentionned(IRole role) {
        if (roleMention != null) {
            if (roleMention.equals(role)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public IDiscordObject getContent() {
        if (userMention != null) {
            return userMention;
        } else if (roleMention != null) {
            return roleMention;
        } else if (channelMention != null) {
            return channelMention;
        } else {
            return new IDiscordObject() {
                @Override
                public IDiscordClient getClient() {
                    return null;
                }

                @Override
                public IShard getShard() {
                    return null;
                }

                @Override
                public IDiscordObject copy() {
                    return this;
                }

                @Override
                public long getLongID() {
                    return 0;
                }
            };
        }
    }

    @Override
    public String getContentAsString() {
        if (userMention != null) {
            return "<@" + userMention.getLongID() + ">";
        } else if (roleMention != null) {
            return "<@&" + roleMention.getLongID() + ">";
        } else if (channelMention != null) {
            return "<#" + channelMention.getLongID() + ">";
        } else if (isMentionEveryone) {
            return "@everyone";
        } else if (isMentionHere) {
            return "@here";
        } else {
            return "";
        }
    }

    @Override
    public String getContentAsReadableString() {
        if (userMention != null) {
            return userMention.mention();
        } else if (roleMention != null) {
            return roleMention.mention();
        } else if (channelMention != null) {
            return channelMention.mention();
        } else if (isMentionEveryone) {
            return "@everyone";
        } else if (isMentionHere) {
            return "@here";
        } else {
            return "";
        }
    }
    
    @Override
    public String getTokenType() {
        return "Mention";
    }
    
    public static boolean isType(String string) {
        if (string.startsWith("<@") && string.endsWith(">")) {
            if (!string.substring(2, string.length() - 1).isEmpty()) {
                return true;
            }
        } else if (string.startsWith("<@&") && string.endsWith(">")) {
            if (!string.substring(3, string.length() - 1).isEmpty()) {
                return true;
            }
        } else if (string.startsWith("<#") && string.endsWith(">")) {
            if (!string.substring(2, string.length() - 1).isEmpty()) {
                return true;
            }
        } else if (string.equals("@everyone") || string.equals("@here")) {
            return true;
        }
        return false;
    }
    
}
