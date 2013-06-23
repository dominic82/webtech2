package de.webtech2.components;

import de.webtech2.dao.UserDAO;
import de.webtech2.entities.User;
import de.webtech2.pages.ViewUserImage;
import de.webtech2.services.Authenticator;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

public class ProfileListItem {
    
    @InjectPage
    private ViewUserImage viewUserImagePage;
    
    @Inject
    private Authenticator authenticator;
    
    @Inject
    private UserDAO userDAO;
    
    @Property
    @Parameter(required = true)
    User user;
    
    private boolean setupRender() {
        if (user != null) {
            return true;
        } 
        return false;
    }
    
    public String getUserImage() {
        return viewUserImagePage.getImageLink(this.user.getId(),true).toString();
    }
}
