package de.webtech2.components;

import de.webtech2.entities.User;
import de.webtech2.services.Authenticator;
import org.apache.tapestry5.ioc.annotations.Inject;

public class ProfileDetails
{
    @Inject
    private Authenticator authenticator;
    
    public User getUser()
    {
        return authenticator.isLoggedIn() ? authenticator.getLoggedUser() : null;
    }
    
}