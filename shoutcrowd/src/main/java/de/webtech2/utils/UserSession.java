
package de.webtech2.utils;

import de.webtech2.entities.User;

/**
 *
 * @author dominic
 */
public class UserSession {

    private User user;
    
    public UserSession() {
        this.user = null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public boolean isLoggedIn() {
        if (user.equals(null)) {
            return false;
        } else {
            return true;
        }
    }
}
