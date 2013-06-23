package de.webtech2.components;

import de.webtech2.dao.MessageDAO;
import de.webtech2.entities.Message;
import de.webtech2.pages.ViewUserImage;
import java.util.Date;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

public class SingleMessage {

    @Inject
    private Messages messages;
    @Inject
    private MessageDAO messageDAO;
    @InjectPage
    private ViewUserImage viewUserImagePage;
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

    public String getUserImage() {
        return viewUserImagePage.getImageLink(this.message.getAuthor().getId(), true).toString();
    }

    public String getTimeDiff() {
        Date now = new Date();
        long ms = now.getTime() - this.message.getTimeCreated().getTime();

        long s = (ms / 1000) % 60;
        long m = (ms / (1000 * 60)) % 60;
        long h = (ms / (1000 * 60 * 60)) % 24;
        long d = (ms / (1000 * 60 * 60 * 24));

        String output = "";
        if (d > 0) {
            output = d + "d";
        } else {
            if (h > 0) {
                output = h + "h";
            } else {
                if (m > 0) {
                    output = m + "m";
                } else {
                    output = s + "s";
                }
            }
        }
        return messages.format("time_ago", output);
    }
}
