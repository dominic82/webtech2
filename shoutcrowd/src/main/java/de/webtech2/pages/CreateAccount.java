package de.webtech2.pages;

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

/**
 * Start page of application shoutcrowd.
 */
public class CreateAccount {
	
	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	@Inject
	Messages messages;
	
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
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(hex);
		return matcher.matches();
	}

	private Object onSuccess() {
		return Index.class;
	}
}
