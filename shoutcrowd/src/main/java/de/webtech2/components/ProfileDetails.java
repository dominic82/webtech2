package de.webtech2.components;

import de.webtech2.dao.UserDAO;
import de.webtech2.entities.User;
import de.webtech2.pages.ViewList;
import de.webtech2.services.Authenticator;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
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
    
    @Property
    User user;
    
    private void setupRender() {
        this.user =  userDAO.getById(this.userId);
    }
    
    public boolean getIsLoggedUser() {
        if (authenticator.getLoggedUser().getId() == this.userId) {
            return true;
        } else {
            return false;
        }
    }
    
    public Integer getShoutCount() {
        return user.getMessages().size();
    }
    
    public Integer getSentInviteCount() {
        return user.getInvitingUsers().size();
    }
        
    public Integer getRecivedInviteCount() {
        return user.getInvitedUsers().size();
    }
    
    
    
}