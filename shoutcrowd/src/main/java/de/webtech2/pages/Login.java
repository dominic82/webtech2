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
public class Login
{
    private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	@Inject
	Messages messages;
	
	@Property
	private String namemail;
	@Property
	private String password;
	
	@Component
	private Form entryForm;
	
	@InjectComponent(value = "password")
	private PasswordField passwordField;
	@InjectComponent(value = "namemail")
	private TextField namemailField;

	
	void onValidateFromEntryForm() {
		validatePassword();
		validateNameMail();

	}

        private void validatePassword() {
            if (password == null) {
                entryForm.recordError(passwordField, messages.get("error-passwordtoshort"));
            } else {
                if (password.length() < 6) {
                    entryForm.recordError(passwordField, messages.get("error-passwordtoshort"));
                }
                //ToDo: password correct?
            }
        }
	
	private void validateNameMail() {
		if (namemail == null) {
			entryForm.recordError(namemailField, messages.get("error-namemailtoshort"));
		} else {
			if (namemail.length() < 4 && !isValidEmailAddress(namemail)) {
				entryForm.recordError(namemailField, messages.get("error-namemailtoshort"));
			}
		}
                //ToDo: name/mail existing?
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
