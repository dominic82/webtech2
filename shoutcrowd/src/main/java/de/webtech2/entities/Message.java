package de.webtech2.entities;

import java.util.Calendar;

public class Message {
	private static final int MAX_LENGTH = 140;
	long id;
	String content;
	User author;
	Calendar timeCreated;

	public Message(long id, String content, User author, Calendar created) {
		checkInput(content, author, created);
		this.author = author;
		this.content = content;
		this.id = id;
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
