package de.webtech2.pages;

import org.hibernate.Session;
import de.webtech2.dao.UserDAO;
import de.webtech2.dao.UserDAOImpl;
import de.webtech2.security.AuthenticationException;
import de.webtech2.services.Authenticator;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

public class Login
{
    @Property
    private String username;

    @Property
    private String password;
    
    @Inject
    private Session session;
    @Inject
    private Authenticator authenticator;
    @Inject
    private Messages messages;

    @Component
    private Form loginForm;
    @InjectComponent(value = "username")
    private TextField usernameField;
    @InjectComponent(value = "password")
    private PasswordField passwordField;


    /**
     * Do the cross-field validation
     */
    void onValidateFromLoginForm()
    {
        try {
			authenticator.login(username, password);
		} catch (AuthenticationException e) {
			loginForm.recordError(messages.get("error-wrongcredentials"));
		}
    }

    /**
     * Validation passed, so we'll go to the "PostLogin" page
     */
    Object onSuccess()
    {
        return Home.class;
    }
    
}
