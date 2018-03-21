package be.kdg.ip.services.api;

import be.kdg.ip.domain.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageService {

    Message addMessage(String chatroom, String message);

    Message addMessage(Message message);

    List<Message> getMessages();

    List<Message> getMessagesForChatroom(String chatroom);

    Message findMessage(int messageId);

    void deleteMessage(int messageId);
}