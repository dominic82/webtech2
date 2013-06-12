package de.webtech2.components;

import de.webtech2.dao.UserDAO;
import de.webtech2.entities.User;
import de.webtech2.pages.Index;
import de.webtech2.pages.ViewList;
import de.webtech2.services.Authenticator;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;

import org.apache.tapestry5.ioc.annotations.Inject;

public class UserMenu {
    
    @Inject
    private Authenticator authenticator;
    @Inject
    private UserDAO userDAO;
    @Property
    private String searchText;
    @Component
    private Form searchForm;
    @InjectPage
    private ViewList viewListPage;
    
    private User getLoggedUser() {
        return userDAO.getById(authenticator.getLoggedUser().getId());
    }
    
    public Object onActionFromViewFollowing() {
        viewListPage.onActivate("following", "");
        return viewListPage;
    }
    
    public Object onActionFromViewFollowed() {
        viewListPage.onActivate("followed", "");
        return viewListPage;
    }
    
    public Object onActionFromLogout() {
        authenticator.logout();
        return Index.class;
    }
    
    Object onSuccessFromsearchForm() {
        viewListPage.onActivate("search", searchText);
        return viewListPage;
    }
}
