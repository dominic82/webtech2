package de.webtech2.components;

import de.webtech2.dao.UserDAO;
import de.webtech2.entities.User;
import de.webtech2.pages.ViewList;
import de.webtech2.services.Authenticator;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.ioc.annotations.Inject;

public class ProfileDetails
{
    @Inject
    private UserDAO userDAO;
    
    @Inject
    private Authenticator authenticator;
    
    @InjectPage
    private ViewList viewListPage;
    
    public User getUser()
    {
        return authenticator.isLoggedIn() ? authenticator.getLoggedUser() : null;
    }
    
    public Integer getShoutCount() {
        User user = userDAO.getById(this.getUser().getId());
        return user.getMessages().size();
    }
    
    public Object onActionFromSentInvites() {
        viewListPage.onActivate("outInvites", "");
        return viewListPage;
    }
    
    public Object onActionFromRecievedInvites() {
        viewListPage.onActivate("inInvites", "");
        return viewListPage;
    }
    
    public Integer getSentInviteCount() {
        User user = userDAO.getById(this.getUser().getId());
        return user.getInvitingUsers().size();
    }
        
    public Integer getRecivedInviteCount() {
        User user = userDAO.getById(this.getUser().getId());
        return user.getInvitedUsers().size();
    }
    
}