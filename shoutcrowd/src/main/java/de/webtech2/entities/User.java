package de.webtech2.entities;

import java.sql.Blob;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.apache.tapestry5.beaneditor.NonVisual;

@Entity
public class User {

	public final static Blob DEFAULT_AVATAR = null; // TODO: default value setzen
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@NonVisual
	private long id;
	
	
	private String username;
	private String email;
	
	@NonVisual
	private String password; //TODO: (MD5-Hash)
	private Blob picture;
	private Calendar timeCreated;
	private Calendar timeModified;
	private Calendar timeLastLogin;
	
	@OneToMany
	private List<Message> messages;
	
	@ManyToMany
	private List<User> followingUsers;
	
	@ManyToMany
	private List<User> followedUsers;

	@ManyToMany
	private List<User> invitedUsers;
	
	@ManyToMany
	private List<User> invitingUsers; //(ManyToMany)

	public User() {
		timeCreated = new GregorianCalendar();
		timeModified = timeCreated;
		picture = DEFAULT_AVATAR;
		
		messages = new LinkedList<Message>();
		followedUsers = new LinkedList<User>();
		followingUsers = new LinkedList<User>();
		invitedUsers = new LinkedList<User>();
		invitingUsers = new LinkedList<User>();
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

	public Blob getPicture() {
		return picture;
	}

	public void setPicture(Blob picture) {
		this.picture = picture;
	}

	public Calendar getTimeCreated() {
		return timeCreated;
	}

	public void setTimeCreated(Calendar timeCreated) {
		this.timeCreated = timeCreated;
	}

	public Calendar getTimeModified() {
		return timeModified;
	}

	public void setTimeModified(Calendar timeModified) {
		this.timeModified = timeModified;
	}

	public Calendar getTimeLastLogin() {
		return timeLastLogin;
	}

	public void setTimeLastLogin(Calendar timeLastLogin) {
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
