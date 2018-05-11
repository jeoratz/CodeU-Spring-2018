package codeu.model.data;

import java.time.Instant;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class NewConversationEvent extends Event {
    private String conversationName;
    private String conversationLink;
    private boolean isPrivate;
    private String user1;
    private String user2;

    /**
     * Constructs a new event that displays newly created conversations.
     *
     * @param timeStamp             the time at which this event happened
     * @param eventType             the type of the instance of event
     * @param conversationName      name of the conversation created
     * @param conversationLink      link to the conversation created
     */
    public NewConversationEvent(Instant timeStamp, String eventType, String conversationName, String conversationLink) {
        super.timeStamp = timeStamp;
        super.eventType = eventType;
        this.conversationName = conversationName;
        this.conversationLink = conversationLink;
        this.isPrivate = false;
    }

    public NewConversationEvent(Instant timeStamp, String eventType, String conversationName, String conversationLink, boolean isPrivate, String user1, String user2) {
        super.timeStamp = timeStamp;
        super.eventType = eventType;
        this.conversationName = conversationName;
        this.conversationLink = conversationLink;
        this.isPrivate = isPrivate;
        this.user1 = user1;
        this.user2 = user2;
    }

    public String getConversationName() {
        return conversationName;
    }

    public void setConversationName(String conversationName) {
        this.conversationName = conversationName;
    }

    public String getConversationLink() {
        return conversationLink;
    }

    public void setConversationLink(String conversationLink) {
        this.conversationLink = conversationLink;
    }
    public boolean isPrivate() { return isPrivate; }

    public String getUser1() { return user1; }

    public String getUser2() { return user2; }

    @Override
    public String toString() {
        LocalDateTime localdatetime = LocalDateTime.ofInstant(super.timeStamp,ZoneId.systemDefault());
        DateTimeFormatter datetimeformatter = DateTimeFormatter.ofPattern("dd/MM/yy h:mm:ss a");
        String time = localdatetime.format(datetimeformatter); 
        return time + ": " + "A new conversation " + "<a href=\"/chat/" + conversationName + "\">" + conversationName + "</a>" + " has been created.\n";
    }
}
