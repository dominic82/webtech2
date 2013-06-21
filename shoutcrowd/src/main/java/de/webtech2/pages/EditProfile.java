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

import de.webtech2.annotations.RequiresLogin;
import de.webtech2.dao.UserDAO;
import de.webtech2.entities.User;
import de.webtech2.services.Authenticator;

@RequiresLogin
public class EditProfile {

    @Inject
    private UserDAO userDAO;
    @Inject
    Messages messages;
    @Inject
    private Authenticator authenticator;
    @Property
    private String email;
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
    boolean newPassowrd = false;

    void onValidateFromEntryForm() {
        validatePassword();
        validateEmail();
        validateUsername();
    }

    private void validatePassword() {
        if (password != null) {
            if (!password.equals(passwordRepeat)) {
                entryForm.recordError(passwordRepeatField, messages.get("error-passwordnotidentical"));
            }
            if (password.length() < 6) {
                entryForm.recordError(passwordField, messages.get("error-passwordtoshort"));
            }
            
            newPassowrd = true;
        } else {
            newPassowrd = false;
        }
    }

    private void validateUsername() {
        if (username == null) {
            entryForm.recordError(usernameField, messages.get("error-usernametoshort"));
        } else {
            if (username.length() < 4) {
                entryForm.recordError(usernameField, messages.get("error-usernametoshort"));
            }

            List<User> userList = userDAO.searchByUsername(username);
            if (!userList.isEmpty()) {
                String currentName = authenticator.getLoggedUser().getUsername();
                String newName = username;
                for (User userFound : userList) {
                    String otherName = userFound.getUsername();
                    if (otherName.equals(newName) && !currentName.equals(newName)) {
                        entryForm.recordError(usernameField, messages.get("error-doubleusername"));
                    }
                }
            }
        }
    }

    private void validateEmail() {
        if (!isValidEmailAddress(email)) {
            entryForm.recordError(emailField,
                    messages.get("error-emailinvalid"));
        } else {
            List<User> userList = userDAO.list();
            if (!(userList.isEmpty())) {
                for (User userFound : userList) {
                    if ((userFound.getEmail().equals(email)) && (userFound.getId() != authenticator.getLoggedUser().getId())) {
                        entryForm.recordError(emailField, messages.get("error-doubleemail"));
                    }
                }
            }
        }
    }

    public boolean isValidEmailAddress(final String hex) {
        Pattern pattern = Pattern.compile(User.EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(hex);
        return matcher.matches();
    }

    void setupRender() {
        long userId = authenticator.getLoggedUser().getId();
        User user = userDAO.getById(userId);
        if (username == null) {
            username = user.getUsername();
        }
        if (email == null) {
            email = user.getEmail();
        }
    }

    @CommitAfter
    private Object onSuccessFromEntryForm() {
        long userId = authenticator.getLoggedUser().getId();
        User user = userDAO.getById(userId);

        if (!newPassowrd) {
            password = user.getPassword();
        }

        userDAO.update(userId, username, email, password);
        return Home.class;
    }
}
