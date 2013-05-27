package de.webtech2.components;

import de.webtech2.entities.User;
import de.webtech2.services.Authenticator;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

public class ProfileDetails
{
    @Inject
    private Session session;
    
    @Inject
    private Authenticator authenticator;
    
    public User getUser()
    {
        return authenticator.isLoggedIn() ? authenticator.getLoggedUser() : null;
    }
    
    public Integer getShoutCount() {
        User user = (User) session.get(User.class, this.getUser().getId());
        return user.getMessages().size();
    }
    
}