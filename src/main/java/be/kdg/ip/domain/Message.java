package be.kdg.ip.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Message {

    @Id
    @GeneratedValue
    @Column(name="messageId",nullable = false)
    private int id;

    @Column(name = "Message", nullable = true, length = 255)
    private String message;

    @Column(name = "Chatroom", nullable = true, length = 255)
    private String chatroom;

    public Message(String message, String chatroom) {
        this.message = message;
        this.chatroom = chatroom;
    }
    public Message() {
    }

    public int getMessageId() {
        return id;
    }

    public void setMessageId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getChatroom() {
        return chatroom;
    }

    public void setChatroom(String chatroom) {
        this.chatroom = chatroom;
    }
}
