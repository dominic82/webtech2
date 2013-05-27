
package de.webtech2.dao;

import de.webtech2.entities.Message;
import de.webtech2.entities.User;
import java.util.List;

public interface MessageDAO {

    Message getById(long id);
    
    List<Message> searchByUser(User user);
    List<Message> list();

    void delete(Message message);
}
