package de.webtech2.util;

import de.webtech2.entities.Message;
import java.util.Comparator;

public class CustomMessageDateComparator implements Comparator<Message> {
    @Override
    public int compare(Message o1, Message o2) {
        return o2.getTimeCreated().compareTo(o1.getTimeCreated());
    }
}
