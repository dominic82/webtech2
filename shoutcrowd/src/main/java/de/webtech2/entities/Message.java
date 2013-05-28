package de.webtech2.entities;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Message{

    public static final int MAX_LENGTH = 140;
    
    @Id
    @Column(name="MESSAGE_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    
    private String content;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeCreated;
    
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinTable(name="USER_MESSAGES",
                joinColumns={@JoinColumn(name="MESSAGE_ID")},
                inverseJoinColumns={@JoinColumn(name="USER_ID")})
    private User author;

    public Message() {
        this.author = new User();
        this.content = "";
        this.timeCreated = new Date();
    }
    
    public Message(User author, String content) {
    	this();
    	this.content = content;
    	this.author = author;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
    
}
