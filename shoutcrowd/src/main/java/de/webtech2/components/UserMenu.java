package de.webtech2.components;

import de.webtech2.pages.Index;
import de.webtech2.services.Authenticator;

import org.apache.tapestry5.ioc.annotations.Inject;

public class UserMenu {

    @Inject
    private Authenticator authenticator;

    public Object onActionFromLogout() {
        authenticator.logout();
        return Index.class;
    }
}
