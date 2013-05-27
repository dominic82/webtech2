package de.webtech2.components;

import de.webtech2.entities.User;
import de.webtech2.pages.ViewList;
import de.webtech2.services.Authenticator;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

public class ProfileListItem {
    
    @Inject
    private Session session;
    
    @Inject
    private Authenticator authenticator;
    
    @InjectPage
    private ViewList viewListPage;
    
    @Parameter(required = true)
    private Long userId;
    
    @Parameter(required = true)
    private String view;
    
    @Property
    User user;
    
    void setupRender() {
        this.user = (User) session.get(User.class, userId);
    }
    
    private User getLoggedUser() {
        return (User) session.get(User.class, authenticator.getLoggedUser().getId());
    }
    
    public boolean isViewFollowing() {
        if (this.view.equals("following")) {
            return true;
        }
        return false;
    }
    
    @CommitAfter
    public Object onActionFromDoUnfollowFollowing(Long id) {
        User tempUser = (User) session.get(User.class, id);
        User loggedUser = this.getLoggedUser();
        loggedUser.getFollowingUsers().remove(tempUser);
        session.persist(loggedUser);
        
        Link link = viewListPage.getLink("following", "");
        return link;
    }
    
    public boolean isViewFollowed() {
        if (this.view.equals("followed")) {
            return true;
        }
        return false;
    }
    
    @CommitAfter
    public void onActionFromDoUnfollowFollowed(Long id) {
        User tempUser = (User) session.get(User.class, id);
        User loggedUser = this.getLoggedUser();
        tempUser.getFollowingUsers().remove(loggedUser);
        session.persist(tempUser);
    }
    
    public boolean isViewInInvites() {
        if (this.view.equals("inInvites")) {
            return true;
        }
        return false;
    }
    
    @CommitAfter
    public Object onActionFromDoAcceptInInvite(Long id) {
        User tempUser = (User) session.get(User.class, id);
        User loggedUser = this.getLoggedUser();
        loggedUser.getInvitingUsers().remove(tempUser);
        loggedUser.getFollowingUsers().add(tempUser);
        session.persist(loggedUser);
        
        Link link = viewListPage.getLink("inInvites", "");
        return link;
    }
    
    @CommitAfter
    public void onActionFromDoCancelInInvite(Long id) {
        User tempUser = (User) session.get(User.class, id);
        User loggedUser = this.getLoggedUser();
        loggedUser.getInvitingUsers().remove(tempUser);
        session.persist(loggedUser);
    }
    
    public boolean isViewOutInvites() {
        if (this.view.equals("outInvites")) {
            return true;
        }
        return false;
    }
    
    @CommitAfter
    public void onActionFromDoCancelOutInvite(Long id) {
        User tempUser = (User) session.get(User.class, id);
        User loggedUser = this.getLoggedUser();
        tempUser.getInvitingUsers().remove(loggedUser);
        session.persist(tempUser);
    }
    
    public boolean isViewSearch() {
        if (this.view.equals("search")) {
            return true;
        }
        return false;
    }
    
    @CommitAfter
    public void onActionFromDoSendInvite(Long id) {
        User tempUser = (User) session.get(User.class, id);
        User loggedUser = this.getLoggedUser();
        tempUser.getInvitingUsers().add(loggedUser);
        session.persist(loggedUser);
    }
}
