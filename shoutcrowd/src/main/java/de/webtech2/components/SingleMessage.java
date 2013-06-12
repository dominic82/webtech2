package de.webtech2.components;

import de.webtech2.dao.MessageDAO;
import de.webtech2.entities.Message;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

public class SingleMessage
{
    @Inject
    private MessageDAO messageDAO;
    
    @Parameter(required = true)
    private Long messageId;
    
    @Property 
    private  Message message;
    
    void setupRender() {
        this.message = messageDAO.getById(messageId);
    }
}
