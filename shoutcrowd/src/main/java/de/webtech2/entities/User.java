package de.webtech2.entities;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @NonVisual
    private long id;
    @Validate("required")
    private String username;
    @Validate("required")
    private String email;
    @NonVisual
    @Validate("required")
    private String password; //TODO: (MD5-Hash)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeCreated;
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeModified;
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeLastLogin;
    @OneToMany
    private List<Message> messages;
    @ManyToMany
    private List<User> followingUsers;
    @ManyToMany
    private List<User> followedUsers;
    @ManyToMany
    private List<User> invitedUsers;
    @ManyToMany
    private List<User> invitingUsers;

    public User() {
        timeCreated = new Date();
        timeModified = new Date();
        timeLastLogin = new Date();

        messages = new LinkedList<Message>();
        followedUsers = new LinkedList<User>();
        followingUsers = new LinkedList<User>();
        invitedUsers = new LinkedList<User>();
        invitingUsers = new LinkedList<User>();
    }

    @Override
    public String toString() {
        return String.valueOf(this.getUsername());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return The MD5 sum of the password for this user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password The MD5 hashed password for this user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Date getTimeModified() {
        return timeModified;
    }

    public void setTimeModified(Date timeModified) {
        this.timeModified = timeModified;
    }

    public Date getTimeLastLogin() {
        return timeLastLogin;
    }

    public void setTimeLastLogin(Date timeLastLogin) {
        this.timeLastLogin = timeLastLogin;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<User> getFollowingUsers() {
        return followingUsers;
    }

    public void setFollowingUsers(List<User> followingUsers) {
        this.followingUsers = followingUsers;
    }

    public List<User> getFollowedUsers() {
        return followedUsers;
    }

    public void setFollowedUsers(List<User> followedUsers) {
        this.followedUsers = followedUsers;
    }

    public List<User> getInvitedUsers() {
        return invitedUsers;
    }

    public void setInvitedUsers(List<User> invitedUsers) {
        this.invitedUsers = invitedUsers;
    }

    public List<User> getInvitingUsers() {
        return invitingUsers;
    }

    public void setInvitingUsers(List<User> invitingUsers) {
        this.invitingUsers = invitingUsers;
    }
}
