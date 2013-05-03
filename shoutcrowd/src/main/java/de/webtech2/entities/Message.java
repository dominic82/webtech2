package de.webtech2.entities;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.apache.tapestry5.beaneditor.NonVisual;

@Entity
public class Message {
	private static final int MAX_LENGTH = 140;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@NonVisual
	private long id;
	
	private String content;
	
	private Calendar timeCreated;
	
	@ManyToOne
	private User author;
	
	public Message(String content, User author, Calendar created) {
		checkInput(content, author, created);
		this.author = author;
		this.content = content;
		this.timeCreated = created;
	}

	private void checkInput(String content, User author, Calendar created) throws IllegalArgumentException {
		if (author == null) {
			// 
			throw new IllegalArgumentException("The user must not be null!");
		}
		if (content == null || content.length() == 0 || content.length() > Message.MAX_LENGTH) {
			throw new IllegalArgumentException("The message content must be at least 1 and at most "+Message.MAX_LENGTH+" characters long!");
		}
		if (created == null) {
			throw new IllegalArgumentException();
		}
		if (created.after(Calendar.getInstance())) {
			throw new IllegalArgumentException("The point of creation for a message must not exceed the current point of time!");
		}
	}

	/**
	 * @return The ID of this message, the key under which it is stored in the
	 *         database.
	 */
	public long getMessageID() {
		return id;
	}

	/**
	 * @return 
	 */
	public String getContent() {
		return content;
	}

	public User getAuthor() {
		return author;
	}
}
