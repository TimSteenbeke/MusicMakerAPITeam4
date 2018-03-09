package be.kdg.ip.Chat.interfaces;

import be.kdg.ip.Chat.DTOs.ChatChannelInitializationDTO;
import be.kdg.ip.Chat.DTOs.ChatMessageDTO;
import be.kdg.ip.services.exceptions.IsSameUserException;
import be.kdg.ip.services.exceptions.UserNotFoundException;
import org.springframework.beans.BeansException;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface IChatChannelController {
    ChatMessageDTO chatMessage(@DestinationVariable String channelId, ChatMessageDTO message)
            throws BeansException, UserNotFoundException;

    ResponseEntity<String> establishChatChannel(@RequestBody ChatChannelInitializationDTO chatChannelInitialization)
            throws IsSameUserException, UserNotFoundException;

    ResponseEntity<String> getExistingChatMessages(@PathVariable("channelUuid") String channelUuid);
}
