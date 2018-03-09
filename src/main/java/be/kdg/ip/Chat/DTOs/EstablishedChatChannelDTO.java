package be.kdg.ip.Chat.DTOs;

public class EstablishedChatChannelDTO {
  private String channelUuid;
  
  private String userOneUsername;
  
  private String userTwoUsername;

  public EstablishedChatChannelDTO() {}
  
  public EstablishedChatChannelDTO(String channelUuid, String userOneUsername, String userTwoUsername) {
    this.channelUuid = channelUuid;
    this.userOneUsername = userOneUsername;
    this.userTwoUsername = userTwoUsername;
  }
  
  public void setChannelUuid(String channelUuid) {
    this.channelUuid = channelUuid;
  }
  
  public String getChannelUuid() {
    return this.channelUuid;
  }
  
  public void setUserOneUsername(String userOneUsername) {
    this.userOneUsername = userOneUsername;
  }
  
  public String getUserOneUsername() {
    return this.userOneUsername;
  }

  public void setUserTwoUsername(String userTwoUsername) {
    this.userTwoUsername = userTwoUsername;
  }
  
  public String getUserTwoUsername() {
    return this.userTwoUsername;
  }
}