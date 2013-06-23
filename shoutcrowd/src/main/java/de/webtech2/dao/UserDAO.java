package de.webtech2.dao;

import de.webtech2.entities.User;
import java.util.List;

public interface UserDAO {
	/**
	 * Fetches the {@link User} stored under this id.
     * 
	 * @param id The id of the user to get.
	 * @return Returns the {@link User} with id or {@code null} if no such user was found.
     */
    User getById(long id);
    
    /**
     * Fetches the {@link User} stored under this credentials.
     * 
     * @param username The username of the user to get.
     * @param password The password of the user to get.
     * @return Returns the {@link User} with the username and password or {@code null} if no such user was found.
     */
    User getByCredentials(String loginname, String password);
    
    void updateLoginData(Long id);
    
    void create(String loginname, String username, String email, String password);
    void update(Long id, String username, String email, String password);
    void updateImageAvatar(Long id, byte[] imageAvatar);
    
    List<User> searchByUsername(String username);
    List<User> list();

    void delete(User user);

    void deleteFollowing(User user, User userFollowing);

    void sendInvite(User user, User userInviting);
    void acceptInvite(User user, User userInvited);
    void cancelInvite(User user, User userInvited);
}