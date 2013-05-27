package de.webtech2.pages;

import de.webtech2.annotations.RequiresLogin;
import de.webtech2.dao.UserDAO;
import de.webtech2.entities.User;
import de.webtech2.services.Authenticator;
import java.util.List;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

@RequiresLogin
public class ViewList {

    @Inject
    private UserDAO userDAO;
    
    @Inject
    private Authenticator authenticator;
    
    @Inject
    private PageRenderLinkSource pageRenderLinkSource;
    
    @Persist
    private String searchText;
    
    @Persist
    private String view;
    
    private List<User> userList;
    
    @Property
    User userEntry;
    
    public void onActivate(String view, String searchText) {
        this.view = view;
        this.searchText = searchText;
    }
        
    private void setupRender() {
        User user = userDAO.getById(authenticator.getLoggedUser().getId());
        
        if (this.view.equals("following")) {
            this.userList = user.getFollowingUsers();
            return;
        }
        if (this.view.equals("followed")) {
            this.userList = user.getFollowedUsers();
            return;
        }
        if (this.view.equals("inInvites")) {
            this.userList = user.getInvitedUsers();
            return;
        }
        if (this.view.equals("outInvites")) {
            this.userList = user.getInvitingUsers();
            return;
        }
        if (this.view.equals("search")) {
            this.userList = userDAO.searchByUsername(this.searchText);
        }
        
        this.userList.remove(userDAO.getById(this.authenticator.getLoggedUser().getId()));
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
