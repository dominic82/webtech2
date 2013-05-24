package de.webtech2.entities;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.apache.tapestry5.beaneditor.NonVisual;

@Entity
public class Message{

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    private static final int MAX_LENGTH = 140;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @NonVisual
    private long id;
    private String content;
    @ManyToOne
    private User author;

    public Message() {
        this.author = new User();
        this.content = "";
    }

}
