package be.kdg.ip.Chat.mappers;


import be.kdg.ip.Chat.DTOs.ChatMessageDTO;
import be.kdg.ip.Chat.models.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class ChatMessageMapper {
    public static List<ChatMessageDTO> mapMessagesToChatDTOs(List<ChatMessage> chatMessages) {
        List<ChatMessageDTO> dtos = new ArrayList<ChatMessageDTO>();

        for (ChatMessage chatMessage : chatMessages) {
            dtos.add(
                    new ChatMessageDTO(
                            chatMessage.getContents(),
                            chatMessage.getAuthorUserId(),
                            chatMessage.getRecipientUserId()
                    )
            );
        }

        return dtos;
    }

    public static ChatMessage mapChatDTOtoMessage(ChatMessageDTO dto) {
        return new ChatMessage(dto.getFromUserId(), dto.getToUserId(), dto.getContents());
    }
}
