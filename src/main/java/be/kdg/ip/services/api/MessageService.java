package be.kdg.ip.services.api;

import be.kdg.ip.domain.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageService {

    Message addMessage(String chatroom, String message, String username, int userId);

    Message addMessage(Message message);

    List<Message> getMessages();

    List<Message> getMessagesForChatroom(String chatroom);

    List<Message> getMessageForUsername(String Username);

    List<Message> getMessageForUserId(int userId);

    Message findMessage(int id);

    void deleteMessage(int id);
}