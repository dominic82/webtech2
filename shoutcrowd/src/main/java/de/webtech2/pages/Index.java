package de.webtech2.pages;

import de.webtech2.entities.Message;
import de.webtech2.entities.User;
import java.io.File;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.SelectModelFactory;
import org.apache.tapestry5.upload.services.UploadedFile;

import org.hibernate.Session;

/**
 * Start page of application shoutcrowd.
 */
public class Index {

    @Property
    @Inject
    @Symbol(SymbolConstants.TAPESTRY_VERSION)
    private String tapestryVersion;
    @InjectComponent
    private Zone zone;
    @Persist
    @Property
    private int clickCount;
    @Inject
    private AlertManager alertManager;

    public Date getCurrentTime() {
        return new Date();
    }

    void onActionFromIncrement() {
        alertManager.info("Increment clicked");

        clickCount++;
    }

    Object onActionFromIncrementAjax() {
        clickCount++;

        alertManager.info("Increment (via Ajax) clicked");

        return zone;
    }
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
    @InjectComponent
    private BeanEditForm messageForm;
    
    /// Grid Beispiel
    @Property
    private User currentUser;
    @Property
    private User followingUser;

    public List<User> getUsers() {
        return session.createCriteria(User.class).list();
    }
    
    public List<Message> getMessages() {
        return session.createCriteria(Message.class).list();
    }
    
    /// Das folgende ist für das Select Following Beispiel
    @Property
    private SelectModel userSelectModel;
    @Inject
    SelectModelFactory selectModelFactory;
    @Property
    private User followingUserFrom;
    @Property
    private User followingUserTo;
    private boolean addFollowingUser;

    void setupRender() {
        List<User> users = this.getUsers();
        userSelectModel = selectModelFactory.create(users, "username");
    }

    void onSelectedFromAddFollowingUser() {
        this.addFollowingUser = true;
    }
    // Upload Picture Beispiel
    @Property
    private UploadedFile file;
    private boolean uploadPicture;

    void onSelectedFromUploadPicture() {
        this.uploadPicture = true;
    }

    // globale After Commit Methode
    @SuppressWarnings("unchecked")
    @CommitAfter
    Object onSuccess() throws IOException {
        if (addFollowingUser) {
            for (User userToChange : this.getUsers()) {
                //if (userToChange.getUsername().equals(followingUserFrom.getUsername())) {
                    userToChange.getFollowingUsers().add(followingUserTo);
                    session.persist(userToChange);
                //}
            }
        } else if (uploadPicture) {
            File copied = new File("src/main/webapp/userpics/" + file.getFileName());
            file.write(copied);
        } else {
            session.persist(user);
        }
        return Index.class;
    }
    /// Test von Dominic - End ///
}
