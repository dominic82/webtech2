package de.webtech2.pages;

import org.hibernate.Session;
import de.webtech2.security.AuthenticationException;
import de.webtech2.services.Authenticator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

public class Login {

    @Property
    private String loginname;
    @Property
    private String password;
    @Inject
    private Authenticator authenticator;
    @Inject
    private Messages messages;
    @Component
    private Form loginForm;
    @InjectComponent(value = "loginname")
    private TextField loginnameField;
    @InjectComponent(value = "password")
    private PasswordField passwordField;

    /**
     * Do the cross-field validation
     */
    void onValidateFromLoginForm() {
        try {
            authenticator.checkCredentials(loginname, password);
        } catch (AuthenticationException e) {
            loginForm.recordError(messages.get("error-wrongcredentials"));
        }
    }

    /**
     * Validation passed, so we'll go to the "PostLogin" page
     */
    @CommitAfter
    Object onSuccessFromLoginForm() {
        try {
            authenticator.login(loginname, password);
        } catch (AuthenticationException ex) {
            loginForm.recordError(messages.get("error-wrongcredentials"));
        }
        return Home.class;
    }
}
