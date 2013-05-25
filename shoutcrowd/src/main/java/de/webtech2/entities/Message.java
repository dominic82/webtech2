package de.webtech2.entities;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Message{

    public static final int MAX_LENGTH = 140;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    
    private String content;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeCreated;
    
    @ManyToOne
    private User author;

    public Message() {
        this.author = new User();
        this.content = "";
        this.timeCreated = new Date();
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
