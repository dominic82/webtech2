package de.webtech2.components;

import de.webtech2.dao.MessageDAO;
import de.webtech2.dao.UserDAO;
import de.webtech2.entities.User;
import de.webtech2.services.Authenticator;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class CreateMessage {

    @Inject
    private Authenticator authenticator;
    
    @Inject
    private UserDAO userDAO;
    
    @Inject
    private MessageDAO messageDAO;
    
    @Property
    private String messageValue;
    
    @Property
    private User user;
    
    void setupRender() {
        this.user = userDAO.getById(authenticator.getLoggedUser().getId());
    }

    @CommitAfter
    private Object onSuccessFromNewMessageForm(User user) {
        messageDAO.create(messageValue, user);
        return null;
    }
}
