package de.webtech2.components;

import de.webtech2.dao.UserDAO;
import de.webtech2.entities.User;
import de.webtech2.pages.ViewList;
import de.webtech2.services.Authenticator;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class ProfileDetails
{
    @Inject
    private UserDAO userDAO;
    
    @Inject
    private Authenticator authenticator;
    
    @InjectPage
    private ViewList viewListPage;
    
    @Parameter
    private Long userId;
    
    public User getUser()
    {
        if (this.userId == null) {
            return userDAO.getById(authenticator.getLoggedUser().getId());
        }
        return userDAO.getById(this.userId);
    }
    
    public boolean getIsLoggedUser() {
        if (authenticator.getLoggedUser().getId() == this.userId) {
            return true;
        } else {
            return false;
        }
    }
    
    public Integer getShoutCount() {
        User user = userDAO.getById(this.getUser().getId());
        return user.getMessages().size();
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