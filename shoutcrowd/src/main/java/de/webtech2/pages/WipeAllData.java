
package de.webtech2.pages;

import de.webtech2.dao.UserDAO;
import de.webtech2.entities.User;
import java.util.List;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

public class WipeAllData {
    @Inject
    private UserDAO userDAO;
    @Inject
    private Messages messages;
    @Property
    private String resetCode;
    @Component
    private Form resetForm;
    @InjectComponent(value = "resetCode")
    private PasswordField resetCodeField;
    
    void onValidateFromResetForm() {
        if (!this.resetCode.equals("doreset")) {
            resetForm.recordError(messages.get("error-wrongresetcode"));
        }
    }

    @CommitAfter
    Object onSuccessFromResetForm() {
        List<User> userList = userDAO.list();
        for (User user : userList) {
            userDAO.delete(user);
        }
        return Index.class;
    }
}
