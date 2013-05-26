package de.webtech2.components;

import de.webtech2.entities.Message;
import de.webtech2.entities.User;
import java.util.Date;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

public class CreateMessage
{
    @Inject
    private Session session;
    @Property
    private User user;
 
    @Property
    private User userFrom;
    @Property
    private Date dateValue;
    @Property
    private String messageValue;
    @Property
    private boolean createMessage;
   
    
       void onSelectedFromCreateMessage() {
        this.createMessage = true;
    }
    
    
    private Object onSuccess(){
        if (createMessage) {
            Message newMessage = new Message();
            newMessage.setAuthor(userFrom);
            newMessage.setContent(messageValue);
            newMessage.setTimeCreated(new Date());
            session.persist(newMessage);
        } else {
            session.persist(user);
        }    
        return CreateMessage.class;    
    
    }
}
