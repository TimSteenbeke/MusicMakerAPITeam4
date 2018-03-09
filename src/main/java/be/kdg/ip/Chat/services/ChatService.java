package be.kdg.ip.Chat.services;


import be.kdg.ip.Chat.DTOs.ChatChannelInitializationDTO;
import be.kdg.ip.Chat.DTOs.ChatMessageDTO;
import be.kdg.ip.Chat.interfaces.IChatService;
import be.kdg.ip.Chat.mappers.ChatMessageMapper;
import be.kdg.ip.Chat.models.ChatChannel;
import be.kdg.ip.Chat.models.ChatMessage;
import be.kdg.ip.Chat.repositories.ChatChannelRepository;
import be.kdg.ip.Chat.repositories.ChatMessageRepository;
import be.kdg.ip.services.api.UserService;
import be.kdg.ip.services.exceptions.IsSameUserException;
import be.kdg.ip.services.exceptions.UserNotFoundException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.google.common.collect.Lists;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class ChatService implements IChatService {
    private ChatChannelRepository chatChannelRepository;

    private ChatMessageRepository chatMessageRepository;

    private UserService userService;

    private final int MAX_PAGABLE_CHAT_MESSAGES = 100;

    @Autowired
    public ChatService(
            ChatChannelRepository chatChannelRepository,
            ChatMessageRepository chatMessageRepository,
            UserService userService) {
        this.chatChannelRepository = chatChannelRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.userService = userService;
    }

    private String getExistingChannel(ChatChannelInitializationDTO chatChannelInitializationDTO) {
        List<ChatChannel> channel = chatChannelRepository
                .findExistingChannel(
                        chatChannelInitializationDTO.getUserIdOne(),
                        chatChannelInitializationDTO.getUserIdTwo()
                );

        return (channel != null && !channel.isEmpty()) ? channel.get(0).getUuid() : null;
    }

    private String newChatSession(ChatChannelInitializationDTO chatChannelInitializationDTO)
            throws BeansException, UserNotFoundException {
        ChatChannel channel = new ChatChannel(chatChannelInitializationDTO.getUserIdOne(), chatChannelInitializationDTO.getUserIdTwo());

        chatChannelRepository.save(channel);

        return channel.getUuid();
    }

    public String establishChatSession(ChatChannelInitializationDTO chatChannelInitializationDTO)
            throws IsSameUserException, BeansException, UserNotFoundException {
        if (chatChannelInitializationDTO.getUserIdOne() == chatChannelInitializationDTO.getUserIdTwo()) {
            throw new IsSameUserException();
        }

        String uuid = getExistingChannel(chatChannelInitializationDTO);

        // If channel doesn't already exist, create a new one
        return (uuid != null) ? uuid : newChatSession(chatChannelInitializationDTO);
    }

    public void submitMessage(ChatMessageDTO chatMessageDTO)
            throws BeansException, UserNotFoundException {
        ChatMessage chatMessage = ChatMessageMapper.mapChatDTOtoMessage(chatMessageDTO);
        chatMessageRepository.save(chatMessage);

    }

    public List<ChatMessageDTO> getExistingChatMessages(String channelUuid) {
        ChatChannel channel = chatChannelRepository.getChannelDetails(channelUuid);

        List<ChatMessage> chatMessages =
                chatMessageRepository.getExistingChatMessages(
                        channel.getUserIdOne(),
                        channel.getUserIdTwo(),
                        (Pageable) new PageRequest(0, MAX_PAGABLE_CHAT_MESSAGES)
                );

        // TODO: fix this
        List<ChatMessage> messagesByLatest = Lists.reverse(chatMessages);

        return ChatMessageMapper.mapMessagesToChatDTOs(messagesByLatest);
    }
}