
package de.webtech2.dao;

import de.webtech2.entities.Message;
import de.webtech2.entities.User;

public interface MessageDAO {

    void create(String content, User user);
    
    Message getById(long id);
    
    void delete(Message message);
}
