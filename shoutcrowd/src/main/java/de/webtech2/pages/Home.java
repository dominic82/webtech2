package de.webtech2.pages;

import de.webtech2.annotations.RequiresLogin;
import de.webtech2.dao.UserDAO;
import de.webtech2.entities.Message;
import de.webtech2.entities.User;
import de.webtech2.services.Authenticator;
import de.webtech2.util.CustomMessageDateComparator;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

@RequiresLogin
public class Home {

    private static final int PAGE_AMOUNT = 10;
    @Property
    private PageItem item;
    @Property
    private PageSource source;
    @Inject
    private PageItemService pageItemService;
    private int page;
    
    @Inject
    private UserDAO userDAO;
    @Inject
    private Authenticator authenticator;
    @Property
    private List<Message> messageList;
    @Property
    private User user;
    @Property
    private Message messageEntry;

    void onActivate(int page) {
        this.page = page;
        this.user = userDAO.getById(authenticator.getLoggedUser().getId());
    }

    private void setupRender() {

        source = new PageSource(page, PAGE_AMOUNT) {
            @Override
            public Page loadPage(int start, int limit) {
                return pageItemService.getPageItems(start, limit);
            }

            @Override
            public Object[] getContext(int page) {
                return new Object[]{page};
            }
        };

        List<Message> tmpList = new LinkedList<Message>();
        tmpList.addAll(this.user.getMessages());
        for (User tmpUser : this.user.getFollowingUsers()) {
            User followingUser = userDAO.getById(tmpUser.getId());
            tmpList.addAll(followingUser.getMessages());

        }
        Collections.sort(tmpList, new CustomMessageDateComparator());
        this.messageList = tmpList;
    }

    public boolean getIsLoggedUser() {
        if (authenticator.getLoggedUser().getId() == this.user.getId()) {
            return true;
        } else {
            return false;
        }
    }
}
y