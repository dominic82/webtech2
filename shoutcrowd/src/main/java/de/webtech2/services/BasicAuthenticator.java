
package de.webtech2.services;

import de.webtech2.dao.UserDAO;
import de.webtech2.entities.User;
import de.webtech2.security.AuthenticationException;
import java.util.Date;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Session;

public class BasicAuthenticator implements Authenticator {

    public static final String AUTH_TOKEN = "authToken";
    
    @Inject
    private UserDAO userDAO;
    
    @Inject
    private Request request;

    public void login(String loginname, String password) throws AuthenticationException {
        
        User user = userDAO.getByCredentials(loginname, password);
        
        if (user == null) {
            throw new AuthenticationException("The user doesn't exist");
        }
        
        userDAO.updateLoginData(user.getId());
        
        request.getSession(true).setAttribute(AUTH_TOKEN, user);
    }

    public boolean isLoggedIn() {
        Session session = request.getSession(false);
        if (session != null) {
            return session.getAttribute(AUTH_TOKEN) != null;
        }
        return false;
    }

    public void logout() {
        Session session = request.getSession(false);
        if (session != null) {
            session.setAttribute(AUTH_TOKEN, null);
            session.invalidate();
        }
    }

    public User getLoggedUser() {
        User user = null;

        if (isLoggedIn()) {
            user = (User) request.getSession(true).getAttribute(AUTH_TOKEN);
        } else {
            throw new IllegalStateException("The user is not logged ! ");
        }
        return user;
    }
}
