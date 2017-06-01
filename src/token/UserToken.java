/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package token;

import java.util.List;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.impl.obj.User;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

/**
 *
 * @author bowen
 */
public class UserToken extends Token<IUser> {
    
    private final IUser content;
    
    public UserToken(IUser user) {
        super("<!" + user.getLongID() + ">");
        content = user;
    }
    
    public UserToken(IDiscordClient client, String userToken) {
        super(userToken = userToken.trim());
        if (userToken.startsWith("<!") && userToken.endsWith(">")) {
            long id = Long.parseLong(userToken.substring(2, userToken.length()-1));
            content = client.fetchUser(id);
        } else if (userToken.contains("@")) {
            List<IUser> users = client.getUsersByName(userToken.substring(0, userToken.indexOf("@")));
            String discriminator = userToken.substring(userToken.indexOf("@")+1);
            IUser uniqueUser = null;
            for (IUser user : users) {
                if (user.getDiscriminator().equals(discriminator)) {
                    uniqueUser = user;
                    break;
                }
            }
            if (uniqueUser == null) {
                throw new IllegalStateException("Found no user by name and discriminator!");
            }
            content = uniqueUser;
        } else {
            List<IUser> users = client.getUsersByName(userToken, true);
            if (users.size() > 1) {
                throw new IllegalStateException("Found more than 1 user, ambiguity!");
            }
            IUser user = users.get(0);
            if (user != null) {
                content = user;
            } else {
                throw new IllegalStateException("This string token cannot be parsed into a UserToken!");
            }
        }
    }

    @Override
    public IUser getContent() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getContentAsString() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getContentAsReadableString() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
