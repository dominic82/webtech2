package de.webtech2.pages;

import de.webtech2.security.AuthenticationException;
import de.webtech2.services.Authenticator;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;

public class Login
{
    @Property
    private String username;

    @Property
    private String password;

    @Inject
    private Authenticator authenticator;

    @Component
    private Form loginForm;

    /**
     * Do the cross-field validation
     */
    void onValidateFromLoginForm()
    {
    }

    /**
     * Validation passed, so we'll go to the "PostLogin" page
     */
    Object onSuccess()
    {
        try
        {
            authenticator.login(username, password);
        }
        catch (AuthenticationException ex)
        {
        }

        return Home.class;
    }
    
}
