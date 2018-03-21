package be.kdg.ip.domain;

import javax.persistence.*;

@Entity
@Table(name ="Message")
public class Message {

    @Id
    @GeneratedValue
    @Column(name="Id",nullable = false)
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
