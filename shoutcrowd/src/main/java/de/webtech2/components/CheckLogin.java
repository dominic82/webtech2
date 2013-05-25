package de.webtech2.components;

import java.io.IOException;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.SessionState;

import de.webtech2.utils.UserSession;

/**
 * Layout component for pages of application shoutcrowd.
 */
@Import(stylesheet = "context:layout/layout.css")
public class CheckLogin {
	@SessionState
	@PageActivationContext
	       UserSession userSession;

	public String onActivate() throws IOException { 
		if (!userSession.isLoggedIn()) {
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
