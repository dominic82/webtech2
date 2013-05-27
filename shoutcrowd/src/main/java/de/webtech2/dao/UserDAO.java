package de.webtech2.dao;

import de.webtech2.entities.User;
import java.util.List;

public interface UserDAO {

    User getById(long id);
    User getByCredentials(String username, String password);
    
    List<User> searchByUsername(String username);
    List<User> list();

    void delete(User user);

    void deleteFollowing(User user, User userFollowing);

    void sendInvite(User user, User userInviting);
    void acceptInvite(User user, User userInvited);
    void cancelInvite(User user, User userInvited);
}