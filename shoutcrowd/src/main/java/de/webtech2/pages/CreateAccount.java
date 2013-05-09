package de.webtech2.pages;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;


/**
 * Start page of application shoutcrowd.
 */
public class CreateAccount
{
	@Property
	String username;
	@Property
	String password;
	@Property
	String passwordRepeat;
	@Property
	String email;
	
	@Component
	Form entryForm;
	@InjectComponent(value="password")
	private PasswordField passwordField;

	
	void onValidateFromEntryForm() {
		entryForm.recordError("noooo!");
	}

}
