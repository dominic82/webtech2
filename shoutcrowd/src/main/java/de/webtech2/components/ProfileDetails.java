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
        if (this.userId == null) {
            this.user =  userDAO.getById(authenticator.getLoggedUser().getId());
        }
        this.user =  userDAO.getById(this.userId);
    }
    
    private User getLoggedUser() {
        return userDAO.getById(authenticator.getLoggedUser().getId());
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
    
    public boolean isViewFollowing() {
        if (this.getLoggedUser().getFollowingUsers().contains(this.user)) {
            return true;
        }
        return false;
    }
    
    @CommitAfter
    public Object onActionFromDoDeleteFollowing(Long id) {
        userDAO.deleteFollowing(this.getLoggedUser(), userDAO.getById(id));
        return null;
    }
    
    public boolean isViewFollowed() {
        if (this.getLoggedUser().getFollowedUsers().contains(this.user)) {
            return true;
        }
        return false;
    }
    
    @CommitAfter
    public Object onActionFromDoDeleteFollowed(Long id) {
        userDAO.deleteFollowing(userDAO.getById(id), this.getLoggedUser());
        return null;
    }
    
    public boolean isViewInInvites() {
        if (this.getLoggedUser().getInvitedUsers().contains(this.user)) {
            return true;
        }
        return false;
    }
    
    @CommitAfter
    public Object onActionFromDoAcceptInInvite(Long id) {
        userDAO.acceptInvite(this.getLoggedUser(), userDAO.getById(id));
        return null;
    }
    
    @CommitAfter
    public Object onActionFromDoCancelInInvite(Long id) {
        userDAO.cancelInvite(this.getLoggedUser(), userDAO.getById(id));
        return null;
    }
    
    public boolean isViewOutInvites() {
        if (this.getLoggedUser().getInvitingUsers().contains(this.user)) {
            return true;
        }
        return false;
    }
    
    @CommitAfter
    public Object onActionFromDoCancelOutInvite(Long id) {
        userDAO.cancelInvite(userDAO.getById(id), this.getLoggedUser());
        return null;
    }
    
    public boolean isViewSendInvite() {
        if (!this.isViewFollowed()
                && !this.isViewOutInvites()) {
            return true;
        }
        return false;
    }
    
    @CommitAfter
    public Object onActionFromDoSendInvite(Long id) {
        userDAO.sendInvite(this.getLoggedUser(), userDAO.getById(id));
        return null;
    }
    
}