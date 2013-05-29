package de.webtech2.dao;

import de.webtech2.entities.User;
import java.util.List;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Query;
import org.hibernate.Session;

public class UserDAOImpl implements UserDAO {

    @Inject
    private Session session;

    public User getById(long id) {
        if (session == null) {
            throw new IllegalStateException("The session is null!");
        }
        return (User) session.get(User.class, id);
    }

    public void delete(User user) {
        session.delete(user);
    }

    public void deleteFollowing(User user, User userFollowing) {
        user.getFollowingUsers().remove(userFollowing);
        session.persist(user);
    }

    public void sendInvite(User user, User userInviting) {
        user.getInvitingUsers().add(userInviting);
        session.persist(user);
    }

    public void acceptInvite(User user, User userInvited) {
        userInvited.getInvitingUsers().remove(user);
        session.persist(userInvited);

        user.getFollowingUsers().add(userInvited);
        session.persist(user);
    }

    public void cancelInvite(User user, User userInvited) {
        userInvited.getInvitingUsers().remove(user);
        session.persist(userInvited);
    }

    public List<User> searchByUsername(String username) {
        Query query = session.getNamedQuery("User.likeUsername")
                .setString("username", "%" + username + "%");
        return query.list();
    }

    public User getByCredentials(String username, String password) {
        if (session == null) {
            throw new IllegalStateException("The session is null!");
        }
        Query query = session.getNamedQuery("User.byCredentials")
                .setString("username", username)
                .setString("password", password);

        User user = null;
        if (query.list().size() > 0) {
            user = (User) query.list().get(0);
        }
        return user;
    }

    public List<User> list() {
        return session.createCriteria(User.class).list();
    }

    public void create(String username, String email, String password) {
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setPassword(password);
        session.persist(newUser);
    }
}
