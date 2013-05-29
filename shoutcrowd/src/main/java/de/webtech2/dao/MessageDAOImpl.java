package de.webtech2.dao;

import de.webtech2.entities.Message;
import de.webtech2.entities.User;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

public class MessageDAOImpl implements MessageDAO {

    @Inject
    private Session session;

    public Message getById(long id) {
        if (session == null) {
            throw new IllegalStateException("The session is null!");
        }
        return (Message) session.get(Message.class, id);
    }

    public void delete(Message message) {
        session.delete(message);
    }

    public void create(String content, User user) {
        Message newMessage = new Message();
        newMessage.setAuthor(user);
        newMessage.setContent(content);
        session.persist(newMessage);
    }
}
