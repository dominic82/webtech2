package de.webtech2.components;

import de.webtech2.entities.User;
import de.webtech2.pages.ViewList;
import de.webtech2.services.Authenticator;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
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
    
    public Link onActionFromDoUnfollowFollowing() {
        User loggedUser = this.getLoggedUser();
        loggedUser.getFollowingUsers().remove(user);
        session.persist(loggedUser);
        
        Link link = viewListPage.set("following", "");
        return link;
    }
    
    public boolean isViewFollowed() {
        if (this.view.equals("followed")) {
            return true;
        }
        return false;
    }
    
    public Link onActionFromDoUnfollowFollowed() {
        User loggedUser = this.getLoggedUser();
        loggedUser.getFollowedUsers().remove(user);
        session.persist(loggedUser);
        
        Link link = viewListPage.set("followed", "");
        return link;
    }
    
    public boolean isViewInInvites() {
        if (this.view.equals("inInvites")) {
            return true;
        }
        return false;
    }
    
    public Link onActionFromDoAcceptInInvite() {
        User loggedUser = this.getLoggedUser();
        loggedUser.getInvitingUsers().remove(user);
        loggedUser.getFollowingUsers().add(user);
        session.persist(loggedUser);
        
        Link link = viewListPage.set("inInvites", "");
        return link;
    }
    
    public Link onActionFromDoCancelInInvite() {
        User loggedUser = this.getLoggedUser();
        loggedUser.getInvitingUsers().remove(user);
        session.persist(loggedUser);
        
        Link link = viewListPage.set("inInvites", "");
        return link;
    }
    
    public boolean isViewOutInvites() {
        if (this.view.equals("outInvites")) {
            return true;
        }
        return false;
    }
    
    public Link onActionFromDoCancelOutInvite() {
        User loggedUser = this.getLoggedUser();
        loggedUser.getInvitedUsers().remove(user);
        session.persist(loggedUser);
        
        Link link = viewListPage.set("outInvites", "");
        return link;
    }
    
    public boolean isViewSearch() {
        if (this.view.equals("search")) {
            return true;
        }
        return false;
    }
    
    public Link onActionFromDoSendInvite() {
        User loggedUser = this.getLoggedUser();
        loggedUser.getInvitedUsers().add(user);
        session.persist(loggedUser);
        
        Link link = viewListPage.set("search", "");
        return link;
    }
}
