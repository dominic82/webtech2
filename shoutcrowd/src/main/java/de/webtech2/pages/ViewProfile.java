package de.webtech2.pages;

import de.webtech2.annotations.RequiresLogin;
import de.webtech2.dao.UserDAO;
import de.webtech2.entities.Message;
import de.webtech2.entities.User;
import de.webtech2.services.Authenticator;
import java.util.List;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

@RequiresLogin
public class ViewProfile {

    @Inject
    private UserDAO userDAO;
    @Inject
    private Authenticator authenticator;
    @Property
    private List<Message> messageList;
    @Property
    private User user;
    @Property
    private Message messageEntry;

    void onActivate(Long userId) {
        this.user = userDAO.getById(userId);
    }

    private void setupRender() {
        this.messageList = this.user.getMessages();
//        for (User followingUser : user.getFollowingUsers()) {
//            this.messageList.addAll(followingUser.getMessages());
//        }
    }
    
    public boolean getIsLoggedUser() {
        if (authenticator.getLoggedUser().getId() == this.user.getId()) {
            return true;
        } else {
            return false;
        }
    }
}
