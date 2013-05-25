package de.webtech2.pages;

import de.webtech2.entities.Message;
import de.webtech2.entities.User;
import java.io.File;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.SelectModelFactory;
import org.apache.tapestry5.upload.services.UploadedFile;

import org.hibernate.Session;

/**
 * Start page of application shoutcrowd.
 */
public class Test {

    /// Test von Dominic - Begin ///
    /// Edit Bean Beispiel
    @Inject
    private Session session;
    
    @Property
    private User user;
    @InjectComponent
    private BeanEditForm userForm;
    
    @Property
    private Message message;
    @Property
    private User messageAuthor; 

    /// Grid Beispiel
    @Property
    private User currentUser;
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
    private boolean addFollowingUser;
    
    @Property
    private User invitingUserFrom;
    @Property
    private User invitingUserTo;
    private boolean addInvitingUser;

    void onSelectedFromAddFollowingUser() {
        this.addFollowingUser = true;
    }

    void onSelectedFromAddInvitingUser() {
        this.addInvitingUser = true;
    }
    
    // Upload Picture Beispiel
    @Property
    private UploadedFile file;
    private boolean uploadPicture;

    void onSelectedFromUploadPicture() {
        this.uploadPicture = true;
    }
    
    // Create Message Beispiel

    @Property
    private boolean createMessage;
    
    @Property
    private User userFrom;
    @Property
    private Date dateValue;
    @Property
    private String messageValue;
    
    void onSelectedFromCreateMessage() {
        this.createMessage = true;
    }

    // globale After Commit Methode
    @SuppressWarnings("unchecked")
    @CommitAfter
    Object onSuccess() throws IOException {
        if (addFollowingUser) {
            followingUserFrom.getFollowingUsers().add(followingUserTo);
            session.persist(followingUserFrom);
        } else if (addInvitingUser) {
            invitingUserFrom.getInvitedUsers().add(invitingUserTo);
            session.persist(invitingUserFrom);
        } else if (uploadPicture) {
            File copied = new File("src/main/webapp/userpics/" + file.getFileName());
            file.write(copied);
        } else if (createMessage) {
            Message newMessage = new Message();
            newMessage.setAuthor(userFrom);
            newMessage.setContent(messageValue);
            newMessage.setTimeCreated(new Date());
            session.persist(newMessage);
        } else {
            session.persist(user);
        }
        return Test.class;
    }
    /// Test von Dominic - End ///
}
