package be.kdg.ip.web.resources;

import java.util.List;

public class MessageGetResource {
    private List<MessageResource> messages;

    public List<MessageResource> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageResource> messages) {
        this.messages = messages;
    }
}
