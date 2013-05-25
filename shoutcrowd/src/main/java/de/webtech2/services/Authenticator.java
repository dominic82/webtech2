
package de.webtech2.services;

import de.webtech2.entities.User;
import de.webtech2.security.AuthenticationException;

/**
 * Basic security interface
 * 
 * @author karesti
 */
public interface Authenticator
{

    /**
     * Gets the logged user
     * 
     * @return User, the logged User
     */
    User getLoggedUser();

    /**
     * Checks if the current user is logged in
     * 
     * @return true if the user is logged in
     */
    boolean isLoggedIn();

    /**
     * Logs the user.
     * 
     * @param username
     * @param password
     * @throws AuthenticationException
     *             throw if an error occurs
     */
    void login(String username, String password) throws AuthenticationException;

    /**
     * Logs out the user
     */
    void logout();
}