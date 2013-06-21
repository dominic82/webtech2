package de.webtech2.components;

import de.webtech2.dao.MessageDAO;
import de.webtech2.dao.UserDAO;
import de.webtech2.entities.User;
import de.webtech2.services.Authenticator;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

public class CreateMessage {

    @Inject
    private Authenticator authenticator;
    
    @Inject
    private UserDAO userDAO;
    
    @Inject
    Messages messages;
    
    @Component
    private Form newMessageForm;
            
    @Inject
    private MessageDAO messageDAO;
    
    @Property
    private String messageValue;
    
    @Property
    private User user;
    
    void setupRender() {
        this.user = userDAO.getById(authenticator.getLoggedUser().getId());
    }
    
     void onValidateFromnewMessageForm() {
        validateMessage();
    }
    

    @CommitAfter
    private Object onSuccessFromNewMessageForm(User user) {
        messageDAO.create(messageValue, user);
        return null;
    }

    private void validateMessage() {
        if(messageValue.length()>140){
            newMessageForm.recordError(messages.get("error-messagetolong"));
        }
    }

    
}
