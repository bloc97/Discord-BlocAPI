/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package token;

import java.util.List;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.IMentionable;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

/**
 *
 * @author bowen
 */
public class MentionToken extends Token<IMentionable> {
    
    private final JDA client;
    
    private User userMention;
    private TextChannel channelMention;
    private Role roleMention;
    
    private boolean isMentionEveryone;
    private boolean isMentionHere;
    
    private PrivateChannel privateChannel;
    private TextChannel textChannel;
    
    public MentionToken(JDA botClient, User user) {
        super("<@" + user.getId()+ ">");
        userMention = user;
        isMentionEveryone = false;
        isMentionHere = false;
        this.client = botClient;
    }
    
    public MentionToken(JDA botClient, Message tokenMessage, String mention) {
        super(mention);
        this.client = botClient;
        privateChannel = tokenMessage.getPrivateChannel();
        textChannel = tokenMessage.getTextChannel();
        if (mention.startsWith("<@") && mention.endsWith(">")) {
            long id = Long.parseLong(mention.substring(2, mention.length()-1));
            userMention = client.getUserById(id);
        } else if (mention.startsWith("<@&") && mention.endsWith(">")) {
            long id = Long.parseLong(mention.substring(3, mention.length()-1));
            roleMention = client.getRoleById(id);
        } else if (mention.startsWith("<#") && mention.endsWith(">")) {
            long id = Long.parseLong(mention.substring(2, mention.length()-1));
            channelMention = client.getTextChannelById(id);
        } else if (mention.equals("@everyone")) {
            isMentionEveryone = true;
        } else if (mention.equals("@here")) {
            isMentionHere = true;
        } else {
            throw new IllegalArgumentException("String cannot be parsed into a Mention Token.");
        }
    }
    
    public boolean isBotMentionned() {
        return isMentionned(client.getSelfUser());
    }
    
    public boolean isMentionned(User user) {
        if (isMentionEveryone) {
            if (privateChannel != null) {
                return (privateChannel.getUser().equals(user));
            }
            if (textChannel != null) {
                for (Member member : textChannel.getMembers()) {
                    if (member.getUser().equals(user)) {
                        return true;
                    }
                }
            }
        } else if (isMentionHere) {
            if (privateChannel != null) {
                List<Guild> mutualGuilds = user.getMutualGuilds();
                return (privateChannel.getUser().equals(user) && !mutualGuilds.isEmpty() && user.getMutualGuilds().get(0).getMember(user).getOnlineStatus() == OnlineStatus.ONLINE);
            }
            if (textChannel != null) {
                for (Member member : textChannel.getMembers()) {
                    if (member.getUser().equals(user) && member.getOnlineStatus() == OnlineStatus.ONLINE) {
                        return true;
                    }
                }
            }
        } else if (userMention != null) {
            if (user.equals(userMention)) {
                return true;
            }
        } else if (roleMention != null) {
            if (textChannel != null) {
                if (textChannel.getGuild().getMember(user).getRoles().contains(roleMention)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean isMentionned(Channel channel) {
        if (channelMention != null) {
            if (channelMention.equals(channel)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isMentionned(Role role) {
        if (roleMention != null) {
            if (roleMention.equals(role)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public IMentionable getContent() {
        if (userMention != null) {
            return userMention;
        } else if (roleMention != null) {
            return roleMention;
        } else if (channelMention != null) {
            return channelMention;
        } else {
            return new IMentionable() {
                @Override
                public String getAsMention() {
                    return "Not a Mention.";
                }
            };
        }
    }

    @Override
    public String getContentAsString() {
        if (userMention != null) {
            return "<@" + userMention.getId()+ ">";
        } else if (roleMention != null) {
            return "<@&" + roleMention.getId()+ ">";
        } else if (channelMention != null) {
            return "<#" + channelMention.getId()+ ">";
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
            return userMention.getAsMention();
        } else if (roleMention != null) {
            return roleMention.getAsMention();
        } else if (channelMention != null) {
            return channelMention.getAsMention();
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
