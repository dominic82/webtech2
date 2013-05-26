package de.webtech2.components;

import de.webtech2.entities.User;
import de.webtech2.pages.EditProfile;
import de.webtech2.pages.ViewList;
import de.webtech2.services.Authenticator;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

public class ProfileDetails
{
    @Inject
    private Session session;
    
    @Inject
    private Authenticator authenticator;
    
    @InjectPage
    private ViewList viewListPage;
    
    public User getUser()
    {
        return authenticator.isLoggedIn() ? authenticator.getLoggedUser() : null;
    }
    
    public Integer getShoutCount() {
        User user = (User) session.get(User.class, this.getUser().getId());
        return user.getMessages().size();
    }
    
    public Link getInInvitesPageLink() {
        Link link = viewListPage.set("inInvites", "");
        return link;
    }
    
    public Link getOutInvitesPageLink() {
        Link link = viewListPage.set("outInvites", "");
        return link;
    }
    
}