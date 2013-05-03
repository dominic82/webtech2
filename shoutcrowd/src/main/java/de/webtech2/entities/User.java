package de.webtech2.entities;

import java.sql.Blob;
import java.util.Calendar;
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
	private List<User> FollowingUsers;
	
	@ManyToMany
	private List<User> FollowedUsers;

	@ManyToMany
	private List<User> InvitedUsers;
	
	@ManyToMany
	private List<User> InvitingUsers; //(ManyToMany)

	
}
