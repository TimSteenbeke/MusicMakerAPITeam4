/*
package be.kdg.ip.Chat.models;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.Date;

@Entity
@Table(name="chatMessage")
public class ChatMessage {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @OneToOne
  @JoinColumn(name = "authorUserId")
  private int authorUserId;

  @OneToOne
  @JoinColumn(name = "recipientUserId")
  private int recipientUserId;

  @NotNull
  private Date timeSent;

  @NotNull
  private String contents;

  public ChatMessage() {}

  public ChatMessage(int authorUserId, int recipientUserId, String contents) {
    this.authorUserId = authorUserId;
    this.recipientUserId = recipientUserId;
    this.contents = contents;
    this.timeSent = new Date();
  }
  
  public int getId() {
    return this.id;
  }
  
  public int getAuthorUserId() {
    return this.authorUserId;
  }
  
  public int getRecipientUserId() {
    return this.recipientUserId;
  }

  public void setAuthorUser(int userId) {
    this.recipientUserId = userId;
  }
  
  public void setRecipientUser(int userId) {
    this.authorUserId = userId;
  }

  public Date getTimeSent() {
    return this.timeSent;
  }
  
  public String getContents() {
    return this.contents;
  }
}*/
