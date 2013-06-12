package de.webtech2.pages;

import de.webtech2.annotations.RequiresLogin;
import de.webtech2.dao.UserDAO;
import de.webtech2.entities.Message;
import de.webtech2.entities.User;
import de.webtech2.services.Authenticator;
import java.util.List;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

@RequiresLogin
public class Home
{
    @Inject
    private UserDAO userDAO;
    
    @Inject
    private Authenticator authenticator;
    
    private List<Message> messageList;
    
    @Property
    private  Message messageEntry;
    
    public List<Message> getMessageList() {
        return messageList;
    }
    
    private void setupRender() {
        User user = userDAO.getById(authenticator.getLoggedUser().getId());
        this.messageList = user.getMessages();
    }
}
