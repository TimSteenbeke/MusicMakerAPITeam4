package be.kdg.ip.services.impl;

import be.kdg.ip.domain.Message;
import be.kdg.ip.repositories.api.MessageRepository;
import be.kdg.ip.services.api.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("messageService")
@Transactional
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Message addMessage(String chatroom, String message, String username, int userId) {
        return messageRepository.save(new Message(message,chatroom,username,userId));
    }

    @Override
    public Message addMessage(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public List<Message> getMessages() {
        return messageRepository.findAll();
    }

    @Override
    public List<Message> getMessagesForChatroom(String chatroom) {
        return messageRepository.findMessagesByChatroom(chatroom);
    }

    @Override
    public List<Message> getMessageForUsername(String Username) {
        return messageRepository.findMessageByUsername(Username);

    }

    @Override
    public List<Message> getMessageForUserId(int userId) {
        return messageRepository.findMessageByUserId(userId);

    }

    @Override
    public Message findMessage(int id) {
        return messageRepository.findMessageById(id);
    }

    @Override
    public void deleteMessage(int id) {
        Message message = messageRepository.findMessageById(id);
        messageRepository.delete(message);
    }
}
