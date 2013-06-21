package de.webtech2.pages;

import de.webtech2.annotations.RequiresLogin;
import de.webtech2.dao.UserDAO;
import de.webtech2.entities.Message;
import de.webtech2.entities.User;
import de.webtech2.services.Authenticator;
import de.webtech2.util.CustomMessageDateComparator;
import java.util.Collections;
import java.util.List;
import org.apache.tapestry5.annotations.Persist;
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
    @Persist
    private Long userId;
    @Property
    private Message messageEntry;

    void onActivate(Long userId) {
        this.userId = userId;
    }

    private void setupRender() {
        this.user = userDAO.getById(this.userId);
        this.messageList = this.user.getMessages();
        Collections.sort(this.messageList, new CustomMessageDateComparator());
    }
    
    public boolean getIsLoggedUser() {
        if (authenticator.getLoggedUser().getId() == this.user.getId()) {
            return true;
        } else {
            return false;
        }
    }
}
