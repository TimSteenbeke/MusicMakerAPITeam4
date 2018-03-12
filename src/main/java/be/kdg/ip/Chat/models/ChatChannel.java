/*
package be.kdg.ip.Chat.models;


import be.kdg.ip.domain.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.UUID;

@Entity
@Table(name="chatChannel")
public class ChatChannel {

  @Id
  @NotNull
  private String uuid;

  @OneToOne
  @JoinColumn(name = "userIdOne")
  private int userIdOne;

  @OneToOne
  @JoinColumn(name = "userIdTwo")
  private int userIdTwo;

  public ChatChannel(int userIdOne, int userIdTwo) {
    this.uuid = UUID.randomUUID().toString();
    this.userIdOne = userIdOne;
    this.userIdTwo = userIdTwo;
  }

  public ChatChannel() {}

  public void setUserIdTwo(int userId) {
    this.userIdTwo = userId;
  }

  public void setUserIdOne(int userId) {
    this.userIdOne = userId;
  }

  public int getUserIdOne() {
    return this.userIdOne;
  }

  public int getUserIdTwo() {
    return this.userIdTwo;
  }

  public String getUuid() {
    return this.uuid;
  }
}*/
