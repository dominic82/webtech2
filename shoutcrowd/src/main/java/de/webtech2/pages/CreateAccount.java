package de.webtech2.pages;

import de.webtech2.dao.UserDAO;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import de.webtech2.entities.User;
import de.webtech2.security.AuthenticationException;
import de.webtech2.services.Authenticator;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;

/**
 * Start page of application shoutcrowd.
 */
public class CreateAccount {

    @Inject
    private UserDAO userDAO;
    @Inject
    Messages messages;
    @Inject
    private Authenticator authenticator;
    @Property
    private String email;
    @Property
    private String loginname;
    @Property
    private String username;
    @Property
    private String password;
    @Property
    private String passwordRepeat;
    @Component
    private Form entryForm;
    @InjectComponent(value = "password")
    private PasswordField passwordField;
    @InjectComponent(value = "passwordRepeat")
    private PasswordField passwordRepeatField;
    @InjectComponent(value = "username")
    private TextField usernameField;
    @InjectComponent(value = "email")
    private TextField emailField;

    void onValidateFromEntryForm() {
        validatePassword();
        validateEmail();
        validateUsername();
    }

    private void validatePassword() {
        if (password == null) {
            entryForm.recordError(passwordField, messages.get("error-passwordtoshort"));
        } else if (passwordRepeat == null) {
            entryForm.recordError(passwordRepeatField, messages.get("error-passwordnotidentical"));
        } else {
            if (!password.equals(passwordRepeat)) {
                entryForm.recordError(passwordRepeatField, messages.get("error-passwordnotidentical"));
            }
            if (password.length() < 6) {
                entryForm.recordError(passwordField, messages.get("error-passwordtoshort"));
            }
        }
    }

    private void validateUsername() {
        if (username == null) {
            entryForm.recordError(usernameField, messages.get("error-usernametoshort"));
        } else {
            if (username.length() < 4) {
                entryForm.recordError(usernameField, messages.get("error-usernametoshort"));
            }
            // TODO: username already exists?
        }
    }

    private void validateEmail() {
        if (!isValidEmailAddress(email)) {
            entryForm.recordError(emailField, messages.get("error-emailinvalid"));
        } else {
            // TODO: email already in use?
        }
    }

    public boolean isValidEmailAddress(final String hex) {
        Pattern pattern = Pattern.compile(User.EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(hex);
        return matcher.matches();
    }

    @CommitAfter
	private Object onSuccessFromEntryForm() {
		try {
			userDAO.create(loginname, username, email, password);
			authenticator.login(loginname, password);
			return Home.class;
		} catch (AuthenticationException ex) {
			return Login.class;
		}
	}
}
