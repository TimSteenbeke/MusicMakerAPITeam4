package be.kdg.ip.Chat.DTOs;

public class ChatMessageDTO {
  private String contents;

  private int fromUserId;
  
  private int toUserId;

  public ChatMessageDTO(){}
  
  public ChatMessageDTO(String contents, int fromUserId, int toUserId) {
    this.contents = contents;
    this.fromUserId = fromUserId;
    this.toUserId = toUserId;
  }

  public String getContents() {
    return this.contents;
  }

  public void setToUserId(int toUserId) {
    this.toUserId = toUserId;
  }
  
  public int getToUserId() {
    return this.toUserId;
  }
  
  public void setContents(String contents) {
    this.contents = contents;
  }

  public void setFromUserId(int userId) {
    this.fromUserId = userId;
  }

  public int getFromUserId() {
    return this.fromUserId;
  }
}
