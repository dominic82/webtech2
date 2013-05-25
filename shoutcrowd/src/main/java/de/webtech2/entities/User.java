package de.webtech2.entities;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="USER")
public class User {

    @Id
    @Column(name="USER_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String username;
    private String email;
    private String password; //TODO: (MD5-Hash)
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeCreated;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeModified;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeLastLogin;
    
    @OneToMany
    private List<Message> messages;
    
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name="FOLLOWING_USER",
                joinColumns={@JoinColumn(name="USER_ID")},
                inverseJoinColumns={@JoinColumn(name="FOLLOWING_USER_ID")})
    private List<User> followingUsers;
    
    @ManyToMany(mappedBy="followingUsers")
    private List<User> followedUsers;
    
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name="INVITED_USER",
                joinColumns={@JoinColumn(name="USER_ID")},
                inverseJoinColumns={@JoinColumn(name="INVITED_USER_ID")})
    private List<User> invitedUsers;
    
    @ManyToMany(mappedBy="invitedUsers")
    private List<User> invitingUsers;
    
    public User() {
        this.username = "";
        this.email = "";
        this.password = "";
        
        this.timeCreated = new Date();
        this.timeModified = new Date();
        this.timeLastLogin = new Date();

        this.messages = new LinkedList<Message>();
        
        this.followingUsers = new LinkedList<User>();
        this.followedUsers = new LinkedList<User>();
        
        this.invitedUsers = new LinkedList<User>();
        this.invitingUsers = new LinkedList<User>();
    }

    @Override
    public String toString() {
        return String.valueOf(this.getUsername());
    }

    public long getId() {
        return id;
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

    public List<User> getInvitedUsers() {
        return invitedUsers;
    }

    public void setInvitedUsers(List<User> invitedUsers) {
        this.invitedUsers = invitedUsers;
    }

    public List<User> getInvitingUsers() {
        return invitingUsers;
    }

}
