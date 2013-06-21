package de.webtech2.components;

import de.webtech2.dao.UserDAO;
import de.webtech2.entities.User;
import de.webtech2.services.Authenticator;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

public class ProfileListItem {
    
    @Inject
    private Authenticator authenticator;
    
    @Inject
    private UserDAO userDAO;
    
    @Parameter(required = true)
    private Long userId;
    
    @Property
    User user;
    
    private void setupRender() {
        this.user = userDAO.getById(userId);
    }
    
    private User getLoggedUser() {
        return userDAO.getById(authenticator.getLoggedUser().getId());
    }
}
