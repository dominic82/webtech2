package de.webtech2.dao;

import java.util.Date;
import java.util.List;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Query;
import org.hibernate.Session;

import de.webtech2.entities.User;

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

    public User getByCredentials(String loginname, String password) {
        if (session == null) {
            throw new IllegalStateException("The session is null!");
        }
        Query query = session.getNamedQuery("User.byCredentials")
                .setString("loginname", loginname)
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

    public void create(String loginname, String username, String email, String password) {
        User newUser = new User();
        newUser.setLoginname(loginname);
        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setPassword(password);
        session.persist(newUser);
    }

    public void update(Long id, String username, String email, String password) {
        User user = this.getById(id);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setTimeModified(new Date());
        session.update(user);
    }

    public void updateLoginData(Long id) {
        User user = this.getById(id);
        user.setTimeLastLogin(new Date());
        user.setLogincount(user.getLogincount() + 1);
        session.update(user);
    }

    public void updateImageAvatar(Long id, byte[] imageAvatar) {
        User user = this.getById(id);
        user.setImageAvatar(imageAvatar);
        session.update(user);
    }
}
