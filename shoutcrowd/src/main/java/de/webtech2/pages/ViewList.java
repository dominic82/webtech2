package de.webtech2.pages;

import de.webtech2.annotations.RequiresLogin;
import de.webtech2.dao.UserDAO;
import de.webtech2.entities.User;
import de.webtech2.services.Authenticator;
import java.util.List;
import org.apache.tapestry5.annotations.ActivationRequestParameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

@RequiresLogin
public class ViewList {
    
    @Inject
    private Messages messages;

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
    User user;
    
    @Property
    @ActivationRequestParameter("page")
    private int userPage = 1;
    
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
    
    public String getListTitle() {
        if (this.view.equals("following")) {
            return messages.format("yourFollowing", this.userList.size());
        }
        if (this.view.equals("followed")) {
            return messages.format("yourFollowed", this.userList.size());
        }
        if (this.view.equals("inInvites")) {
            return messages.format("yourInInvites", this.userList.size());
        }
        if (this.view.equals("outInvites")) {
            return messages.format("yourOutInvites", this.userList.size());
        }
        if (this.view.equals("search")) {
            return messages.format("yourSearchResults", new Object[]{this.userList.size(),this.searchText});
        }
        return "";
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
    
    public boolean getHasResults() {
        if (this.userList.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
    
    public Integer getResultCount() {
        return this.userList.size();
    }
    
}
