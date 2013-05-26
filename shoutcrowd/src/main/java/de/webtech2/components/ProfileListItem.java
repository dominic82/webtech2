package de.webtech2.components;

import de.webtech2.entities.User;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

public class ProfileListItem {
    
    @Inject
    private Session session;

    @Parameter(required = true)
    private Long userId;
    
    @Property
    User user;
    
    void setupRender() {
        this.user = (User) session.get(User.class, userId);
    }
}
