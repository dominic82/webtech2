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
    
    @Property 
    @Parameter(required = true)
    private Message message;
    
    @Property
    String messageLine;
    
    @Property
    String messageLines[];
    
    boolean setupRender() {
        if (message != null) {
            this.messageLines = this.message.getContent().split("\n");
            return true;
        } 
        return false;
    }
}
