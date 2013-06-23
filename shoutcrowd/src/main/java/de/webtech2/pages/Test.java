package de.webtech2.pages;

import de.webtech2.entities.Message;
import de.webtech2.entities.User;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.SelectModelFactory;

import org.hibernate.Session;

/**
 * Start page of application shoutcrowd.
 */
public class Test {

    /// Edit Bean Beispiel
    @Inject
    private Session session;
    
    @Property
    private User user;
    
    @Property
    private Message message;
    @Property
    private User messageAuthor; 

    /// Grid Beispiel
    @Property
    private User followingUser;
    @Property
    private User followedUser;
    @Property
    private User invitingUser;
    @Property
    private User invitedUser;
    @Property
    private Message usermessage;

    public List<User> getUsers() {
        return session.createCriteria(User.class).list();
    }
    
    public List<Message> getMessages() {
        return session.createCriteria(Message.class).list();
    }
    
    /// Das folgende ist für das Select Following/Invited Beispiel
    void setupRender() {
        List<User> users = this.getUsers();
        userSelectModel = selectModelFactory.create(users, "username");
    }
    
    @Property
    private SelectModel userSelectModel;
    @Inject
    SelectModelFactory selectModelFactory;
    
    @Property
    private User followingUserFrom;
    @Property
    private User followingUserTo;
    
    @Property
    private User invitingUserFrom;
    @Property
    private User invitingUserTo;

    @CommitAfter
    Object onSuccessFromAddFollowingUser() {
        followingUserFrom.getFollowingUsers().add(followingUserTo);
        session.persist(followingUserFrom);
        return Test.class;
    }
    
    @CommitAfter
    Object onSuccessFromAddInvitingUser() {
        invitingUserFrom.getInvitingUsers().add(invitingUserTo);
        session.persist(invitingUserFrom);
        return Test.class;
    }
    
    // Create Message Beispiel
    @Property
    private User userFrom;
    @Property
    private Date dateValue;
    @Property
    private String messageValue;
    
    @CommitAfter
    Object onSuccessFromCreateMessage() {
        Message newMessage = new Message();
        newMessage.setAuthor(userFrom);
        newMessage.setContent(messageValue);
        newMessage.setTimeCreated(new Date());
        session.persist(newMessage);
        return Test.class;
    }
    
    
    // Add Sample Users
    @CommitAfter
    Object onSuccessFromAddSampleUsers() {
        List<String> names = new ArrayList<String>();
        names.add("Dominic");
        names.add("Dominik");
        names.add("Nico");
        names.add("Dennis");
        names.add("Benny");
        names.add("Betty");
        names.add("Sylvia");
        names.add("Anna");
        names.add("Nicole");
        names.add("Sophie");
        
        int i = 0;
        for (String name : names) {
            i++;
            User newUser = new User();
            newUser.setLoginname("test" + i);
            newUser.setEmail("test" + i + "@webtech2.de");
            newUser.setUsername(name);
            newUser.setPassword("testpw");
            session.persist(newUser);
        }
        return Test.class;
    }
}
