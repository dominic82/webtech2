package de.webtech2.pages;

import de.webtech2.annotations.RequiresLogin;
import de.webtech2.entities.User;
import de.webtech2.services.Authenticator;
import java.util.List;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.ActivationRequestParameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.hibernate.Query;
import org.hibernate.Session;

@RequiresLogin
public class ViewList {

    @Inject
    private Session session;
    
    @Inject
    private Authenticator authenticator;
    
    @Inject
    private PageRenderLinkSource pageRenderLinkSource;
    
    private String searchText;
    private String view;
    private List<User> userList;
    
    @Property
    User userEntry;
    
    public Link getLink(String view, String searchText) {
        Link link = pageRenderLinkSource.createPageRenderLink(this.getClass());
        link.addParameter("searchText", searchText);
        link.addParameter("view", view);
        return link;
    }

    void onActivate(String view, String searchText) {
        this.view = view;
        this.searchText = searchText;
        
        if (view.equals("following")) {
            User user = (User) session.get(User.class, authenticator.getLoggedUser().getId());
            this.userList = user.getFollowingUsers();
            return;
        }
        if (view.equals("followed")) {
            User user = (User) session.get(User.class, authenticator.getLoggedUser().getId());
            this.userList = user.getFollowedUsers();
            return;
        }
        if (view.equals("inInvites")) {
            User user = (User) session.get(User.class, authenticator.getLoggedUser().getId());
            this.userList = user.getInvitingUsers();
            return;
        }
        if (view.equals("outInvites")) {
            User user = (User) session.get(User.class, authenticator.getLoggedUser().getId());
            this.userList = user.getInvitedUsers();
            return;
        }
        if (view.equals("search")) {
            Query query = session.getNamedQuery("User.likeUsername")
                .setString("username", "%" + this.searchText + "%");
            this.userList = query.list();
        }
    }
    
    public List<User> getUserList() {
        return userList;
    }
    
    public String getView() {
        return this.view;
    }
    
    public String getSearchText() {
        return this.searchText;
    }
    
}
