/*
package be.kdg.ip.Chat.interfaces;

import java.util.List;

import be.kdg.ip.Chat.DTOs.ChatChannelInitializationDTO;
import be.kdg.ip.Chat.DTOs.ChatMessageDTO;
import be.kdg.ip.services.exceptions.IsSameUserException;
import be.kdg.ip.services.exceptions.UserNotFoundException;
import org.springframework.beans.BeansException;

public interface IChatService {
  String establishChatSession(ChatChannelInitializationDTO chatChannelInitializationDTO)
      throws IsSameUserException, BeansException, UserNotFoundException;

  void submitMessage(ChatMessageDTO chatMessageDTO)
      throws BeansException, UserNotFoundException;
  
  List<ChatMessageDTO> getExistingChatMessages(String channelUuid);
}*/
