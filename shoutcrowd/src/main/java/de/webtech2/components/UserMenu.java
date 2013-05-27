package de.webtech2.components;

import de.webtech2.pages.Index;
import de.webtech2.pages.ViewList;
import de.webtech2.services.Authenticator;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;

import org.apache.tapestry5.ioc.annotations.Inject;

public class UserMenu {
    
    @Inject
    private Authenticator authenticator;
    @Property
    private String searchText;
    @Component
    private Form searchForm;
    @InjectPage
    private ViewList viewListPage;
    
    public Object onActionFromLogout() {
        authenticator.logout();
        return Index.class;
    }
    
    Object onSuccessFromsearchForm() {
        Link link = viewListPage.getLink("search", searchText);
        return link;
    }
}
