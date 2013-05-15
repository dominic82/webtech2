package de.webtech2.pages;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

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

	private boolean isValidEmailAddress(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}

	private Object onSuccess() {
		return Index.class;
	}
}
