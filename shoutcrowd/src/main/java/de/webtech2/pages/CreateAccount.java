package de.webtech2.pages;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import de.webtech2.dao.UserDAO;
import de.webtech2.entities.User;
import de.webtech2.security.AuthenticationException;
import de.webtech2.services.Authenticator;

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
    private TextField loginnameField;
    @InjectComponent(value = "loginname")
    private TextField usernameField;
    @InjectComponent(value = "email")
    private TextField emailField;

    void onValidateFromEntryForm() {
        validatePassword();
        validateEmail();
        validateUsername();
        validateLoginname();
    }

    private void validateLoginname() {
    	if (username == null) {
            entryForm.recordError(loginnameField, messages.get("error-usernametoshort"));
        } else {
            if (username.length() < 4) {
                entryForm.recordError(loginnameField, messages.get("error-usernametoshort"));
            }

        }
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
        if (!isValidUsername(username)) {
            entryForm.recordError(usernameField, messages.get("error-usernametoshort"));
        } else {
        	List<User> usernameUsers = userDAO.searchByUsername(username);
        	if (usernameUsers != null && !usernameUsers.isEmpty()) {
        		entryForm.recordError(usernameField, messages.get("error-doubleusername"));
        	}
        }
    }

    private boolean isValidUsername(String username2) {
    	return (username != null && username.length() >= 4);
	}

	private void validateEmail() {
        if (!isValidEmailAddress(email)) {
            entryForm.recordError(emailField, messages.get("error-emailinvalid"));
        } else {
        	List<User> allUsers = userDAO.list();
        	for (User user : allUsers) {
        		if (user != null && user.getEmail().equals(email)) {
        			entryForm.recordError(usernameField, messages.get("error-doubleemail"));
        			break;
        		}
        	}
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
