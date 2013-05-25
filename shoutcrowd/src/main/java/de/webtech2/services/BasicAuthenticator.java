/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.webtech2.services;

import de.webtech2.entities.User;
import de.webtech2.security.AuthenticationException;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Session;
import org.hibernate.Query;

/**
 * Basic Security Realm implementation
 *
 * @author karesti
 * @version 1.0
 */
public class BasicAuthenticator implements Authenticator {

    public static final String AUTH_TOKEN = "authToken";
    @Inject
    private org.hibernate.Session hibernateSession;
    @Inject
    private Request request;

    public void login(String username, String password) throws AuthenticationException {

        Query query = hibernateSession.getNamedQuery("User.byCredentials")
                .setString("username", username)
                .setString("password", password);
        
        User user = (User) query.list().get(0);
        
        if (user == null) {
            throw new AuthenticationException("The user doesn't exist");
        }
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
