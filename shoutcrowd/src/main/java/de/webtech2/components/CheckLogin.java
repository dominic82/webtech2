package de.webtech2.components;

import java.io.IOException;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.SessionState;

import de.webtech2.entities.User;

/**
 * Layout component for pages of application shoutcrowd.
 */
@Import(stylesheet = "context:layout/layout.css")
public class CheckLogin {
	@SessionState
	@PageActivationContext
	User user;

	public String onActivate() throws IOException { 
		if (!isLoggedIn()) {
			return "Login";
		} else {
			return "CreateAccount";
		}
	}

	private boolean isLoggedIn() {
		// TODO: implement
		return false;
	}
}
